package com.service.impl;

import com.dao.MineDao;
import com.domain.*;
import com.mapper.MineSweepingGameDataMapper;
import com.mapper.MineSweepingModelDataMapper;
import com.service.MineSweepingGameDataService;
import com.utils.BaseHolder;
import com.utils.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.LongSummaryStatistics;

/**
 * 操纵扫雷数据库的类
 * 接收控制层的数据
 * 查询、修改数据库数据
 * 返回数据给控制层
 */
@Service("mineSweepingGameData")
@SuppressWarnings("all")
public class MineSweepingGameDataServiceImpl implements MineSweepingGameDataService {
    /**
     * mybatis控制扫雷游戏数据的持久层对象
     */
    @Resource
    private MineSweepingGameDataMapper mineSweepingGameDataMapper;
    /**
     * mybatis控制扫雷游戏模式的持久层对象
     */
    @Resource
    private MineSweepingModelDataMapper mineSweepingModelDataMapper;
    /**
     * dao层对象
     */
    @Autowired
    private MineDao mineDao;

    /**
     * 查找所有模式用时最短的扫雷游戏数据
     *
     * @return 扫雷游戏数据集合
     */
    @Override
    public MineSweepingGameData[] findAllModelBestGameData() {
        //查找四个模式
        final MineSweepingGameData easyModelWinGameData = mineSweepingGameDataMapper.findByModelNameOrderByTime("简单");
        final MineSweepingGameData ordinaryModelWinGameData = mineSweepingGameDataMapper.findByModelNameOrderByTime("普通");
        final MineSweepingGameData hardModelWinGameData = mineSweepingGameDataMapper.findByModelNameOrderByTime("困难");
        final MineSweepingGameData customizeModelWinGameData = mineSweepingGameDataMapper.findByModelNameOrderByTime("自定义");
        //将模式返回为数组形式
        return new MineSweepingGameData[]{easyModelWinGameData, ordinaryModelWinGameData, hardModelWinGameData, customizeModelWinGameData};
    }

    /**
     * 根据参数向数据库中插入一条扫雷游戏数据
     *
     * @param gameNowData  游戏当前数据
     * @param nowMineModel 当前扫雷模式
     *
     * @return 扫雷游戏数据
     */
    @Override
    public MineSweepingGameData insert(GameNowData gameNowData, MineModel nowMineModel) {
        MineSweepingGameData gameData = new MineSweepingGameData();

        //设置游戏数据
        gameData.setIsWin((byte) (gameNowData.isWin() ? 1 : 0));
        try {
            gameData.setPlayerName(mineDao.findNowGameName());
        } catch (IOException e) {
            //有异常则抛出加载玩家数据异常
            throw new RuntimeException(Information.playerDataError);
        }
        gameData.setTime(gameNowData.getEndTime() - gameNowData.getStartTime());

        //设置模式数据
        MineSweepingModelData modelData = new MineSweepingModelData();
        modelData.setColumn(nowMineModel.getColumn());
        modelData.setRow(nowMineModel.getRow());
        modelData.setMineNumber(nowMineModel.getMineNumber());
        modelData.setModelName(nowMineModel.getName());

        //当前数据库对应名字的模式数据
        MineSweepingModelData oldModelData = mineSweepingModelDataMapper.findOneByModelName(nowMineModel.getName());

        //若数据库中无对应的模式数据，则插入该数据
        if (oldModelData == null) {
            mineSweepingModelDataMapper.insert(modelData);
        } else {
            //若数据不一致，则更新该数据
            if (!oldModelData.equals(modelData)) {
                mineSweepingModelDataMapper.updateByModelId(modelData, oldModelData.getModelId());
            }
            modelData.setModelId(oldModelData.getModelId());
        }

        //为扫雷数据添加对应的模式数据
        gameData.setMineSweepingModelData(modelData);

        //向数据库插入数据
        mineSweepingGameDataMapper.insert(gameData);

        return gameData;
    }

    /**
     * 根据扫雷游戏数据创建对话框数据
     *
     * @param mineSweepingGameData 扫雷游戏数据
     *
     * @return 游戏结束对话框数据
     */
    @Override
    public GameOverDialogData findGameOverData(MineSweepingGameData mineSweepingGameData) {
        //获取玩家名称
        String name;
        try {
            name = mineDao.findNowGameName();
        } catch (IOException e) {
            throw new RuntimeException(Information.playerDataError);
        }

        //创建扫雷结束后的对话框数据对象
        GameOverDialogData data = new GameOverDialogData();

        //获取该玩家该模式的所有扫雷游戏数据
        List<MineSweepingGameData> mineSweepingGameDataList = findByPlayerNameAndModelName(name,
                mineSweepingGameData.getMineSweepingModelData().getModelName());

        //对数据进行统计
        final LongSummaryStatistics statistics = mineSweepingGameDataList.stream()
                .filter(gameData -> gameData.getIsWin() == 1)
                .mapToLong(MineSweepingGameData::getTime)
                .summaryStatistics();

        //游戏次数
        int gameNumber = mineSweepingGameDataList.size();
        //胜利次数
        int winsNumber = (int) statistics.getCount();
        //最短用时
        long shortestTime = statistics.getMin();
        //当前游戏的用时
        long time = mineSweepingGameData.getTime();

        //获得低精度的浮点数格式化对象
        DecimalFormat format = BaseHolder.getBean("lowDecimalFormat", DecimalFormat.class);

        //设置数据
        data.setNowGameTime(time + "ms");
        data.setWinsNumber(winsNumber + "");
        data.setAverageWinPercentage(gameNumber == 0 ? "-" : (format.format((double) winsNumber * 100 / gameNumber)) + "%");
        data.setFailNumber((gameNumber - winsNumber) + "");
        data.setAverageTime(winsNumber == 0 ? "-" : (format.format((double) statistics.getSum() / winsNumber)) + "ms");
        data.setAllGameNumber(gameNumber + "");
        data.setShortestTime(shortestTime == Long.MAX_VALUE ? "-" : shortestTime + "ms");
        data.setWin(mineSweepingGameData.getIsWin() == 1);
        data.setBreakRecord(time == shortestTime);

        return data;
    }

    /**
     * 查找该玩家该模式的所有扫雷游戏数据
     *
     * @param playerName 玩家名称
     * @param modelName  扫雷模式名称
     *
     * @return 扫雷游戏数据集合
     */
    @Override
    public List<MineSweepingGameData> findByPlayerNameAndModelName(String playerName, String modelName) {
        return mineSweepingGameDataMapper.findByPlayerNameAndModelName(playerName, modelName);
    }

}
