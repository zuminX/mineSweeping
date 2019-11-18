package com.service.impl;

import com.dao.GamePropertiesDao;
import com.domain.*;
import com.service.GameDataService;
import com.utils.Information;
import com.utils.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 扫雷业务层
 * 接收控制层的数据
 * 调用dao层获得扫雷数据
 * 返回数据给控制层
 */
@Service("gameDataService")
public class GameDataServiceImpl implements GameDataService {
    /**
     * dao层对象
     */
    private final GamePropertiesDao mineDao;
    /**
     * 视图组件
     */
    private final ViewComponent viewComponent;
    /**
     * 游戏当前数据
     */
    private final GameNowData gameNowData;
    /**
     * 扫雷游戏的所有模式
     */
    private MineAllModel mineAllModel = new MineAllModel();
    /**
     * 是否改变数据
     */
    private boolean changeData;
    /**
     * 地雷预加载数据
     */
    private Queue<MineData> minePreloadData = new ConcurrentLinkedQueue<>();
    /**
     * 产生随机数的对象
     */
    private Random random;

    /**
     * 注入成员变量
     * 初始化随机对象，预加载数据
     */
    @Autowired
    public GameDataServiceImpl(GamePropertiesDao mineDao, ViewComponent viewComponent, GameNowData gameNowData) {
        this.mineDao = mineDao;
        this.viewComponent = viewComponent;
        this.gameNowData = gameNowData;

        random = new Random();
        preLoadData();
    }

    /**
     * 根据名称改变当前扫雷模式
     *
     * @param modelName 模式名称
     * @return false 没有发生异常；null 发生异常
     */
    @Override
    public Boolean changeModel(String modelName) {
        MineModel model = null;
        switch (modelName) {
            case "简单":
                model = mineAllModel.getEasyModel();
                break;
            case "普通":
                model = mineAllModel.getOrdinaryModel();
                break;
            case "困难":
                model = mineAllModel.getHardModel();
                break;
            case "自定义":
                model = mineAllModel.getCustomizeModel();
                break;
        }
        //未找到指定模式，抛出加载游戏模式失败的异常
        if (model == null) {
            throw new RuntimeException(Information.loadGameModelError);
        }
        //设置为当前模式
        mineAllModel.setNowMineModel(model);
        return false;
    }

    /**
     * 获取当前扫雷模式
     *
     * @return 当前模式
     */
    @Override
    public MineModel getNowMineModel() {
        return mineAllModel.getNowMineModel();
    }

    /**
     * 创建一个扫雷视图按钮组
     */
    @Override
    public void newMineViewButtons() {
        //获取所需数据
        final MineModel nowMineModel = getNowMineModel();
        final int row = nowMineModel.getRow();
        final int column = nowMineModel.getColumn();

        //创建对应大小的按钮组
        MineJButton[][] mineJButtons = new MineJButton[row][];
        for (int i = 0; i < row; i++) {
            mineJButtons[i] = new MineJButton[column];
            for (int j = 0; j < column; j++) {
                mineJButtons[i][j] = new MineJButton();
                //设置按钮的位置
                mineJButtons[i][j].setPoint(new Point(i, j));
            }
        }

        //设置当前游戏数据
        gameNowData.setButtons(mineJButtons);
        gameNowData.setMineNumber(nowMineModel.getMineNumber());
        gameNowData.setSpace(row * column);
    }

    /**
     * 向扫雷地图添加地雷数据
     *
     * @param point 点
     */
    @Override
    public void fillMineData(Point point) {
        MineData mineData;
        //重复获取地雷数据，直到地雷数据中无当前点
        do {
            //若预加载数据为空，则创建新的扫雷数据
            if (minePreloadData.isEmpty()) {
                mineData = newMineData(point);
                //否则从预加载数据中取出数据
            } else {
                mineData = minePreloadData.poll();
            }
        } while (mineData.getMinePoint().contains(point));

        //为按钮填充地雷数据
        MineJButton[][] mineJButtons = gameNowData.getButtons();
        int[][] data = mineData.getData();
        for (int i = 0; i < mineJButtons.length; i++) {
            for (int j = 0; j < mineJButtons[i].length; j++) {
                //若数据为-1则为地雷，否则为周围的地雷数
                mineJButtons[i][j].setStatus(data[i][j] == -1 ? MineJButton.MINE : MineJButton.HIDE_SPACE);
                mineJButtons[i][j].setData(data[i][j]);
            }
        }
    }

    /**
     * 保存设置的数据
     *
     * @return false 没有发生异常；null 发生异常
     */
    @Override
    public Boolean saveSettingData() {
        //若当前模式为自定义模式
        if (getNowMineModel().getName().equals("自定义")) {
            MineModel customizeModelByData;
            //创建自定义扫雷模式
            try {
                customizeModelByData = mineDao.findCustomizeModelByData(viewComponent.getRowTextField().getText(),
                        viewComponent.getColumnTextField().getText(), viewComponent.getMineNumberTextField().getText());
            } catch (IOException e) {
                //有异常则抛出加载游戏基础设置异常
                throw new RuntimeException(Information.loadGameBasicSettingError);
            }

            //自定义模式为空则抛出改变自定义模式数据异常
            if (customizeModelByData == null) {
                throw new RuntimeException(Information.changeCustomizeDataError);
            }

            //设置自定义模式
            mineAllModel.setCustomizeModel(customizeModelByData);

            //向配置文件中更新自定义模式数据
            try {
                mineDao.updateCustomizeModelInProperties(customizeModelByData);
            } catch (IOException e) {
                //有异常则抛出加载游戏基础设置异常
                throw new RuntimeException(Information.loadGameBasicSettingError);
            }
        }

        //获取标签上的名字
        final String name = viewComponent.getGameNameField().getText();
        //若名字为空则抛出玩家名为空的异常
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException(Information.playerNameNotNull);
        }

        //向配置文件中改变玩家的名称
        try {
            mineDao.changeGameName(name);
        } catch (IOException e) {
            //有异常则抛出加载玩家数据基础设置异常
            throw new RuntimeException(Information.loadPlayerDataSettingError);
        }

        //向配置文件中改变开启记录的状态
        try {
            mineDao.changeOpenRecordStatus(viewComponent.getOpenRecordCheckBox().isSelected());
        } catch (IOException e) {
            //有异常则抛出加载玩家数据基础设置异常
            throw new RuntimeException(Information.loadPlayerDataSettingError);
        }

        //改变了数据
        changeData = true;

        return false;
    }

    /**
     * 创建地雷数据
     *
     * @param ignorePoint 忽略的点
     * @return 地雷点的集合
     */
    private HashSet<Point> createMineData(Point ignorePoint) {
        //获取当前模式的数据
        final MineModel nowMineModel = getNowMineModel();
        final int row = nowMineModel.getRow();
        final int column = nowMineModel.getColumn();
        final int mineNumber = nowMineModel.getMineNumber();

        //向集合中添加地雷的点
        HashSet<Point> points = new HashSet<>(mineNumber);
        while (points.size() < mineNumber) {
            final Point point = new Point(random.nextInt(row), random.nextInt(column));
            //若有忽略的点，则避免生成对应位置的地雷
            if (!point.equals(ignorePoint)) {
                points.add(point);
            }
        }
        return points;
    }

    /**
     * 根据地雷数据填充每个点的数据
     *
     * @param mineModel 扫雷模式
     * @param mineData  地雷数据
     * @return 每个点的数据
     */
    private int[][] findAllSpaceAroundMineNumber(MineModel mineModel, Set<Point> mineData) {
        //获取当前模式的数据
        final int row = mineModel.getRow();
        final int column = mineModel.getColumn();
        int[][] aroundMineNumberData = new int[row][column];

        //查找每个点周围的地雷数
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                aroundMineNumberData[i][j] = findAroundMineNumber(i, j, mineData);
            }
        }
        return aroundMineNumberData;
    }

    /**
     * 寻找指定行列周围9x9区域的地雷数
     *
     * @param row      行数
     * @param column   列数
     * @param mineData 地雷数据
     * @return 地雷数
     */
    private int findAroundMineNumber(int row, int column, Set<Point> mineData) {
        int aroundMineNumber = 0;

        //遍历所有地雷，找到该点周围的地雷数
        for (Point point : mineData) {
            final int i = point.getI();
            final int j = point.getJ();
            //若该地为地雷，则直接返回-1
            if (row == i && column == j) {
                return -1;
            }
            //该地雷在该地的范围内
            if (row >= i - 1 && row <= i + 1 && column >= j - 1 && column <= j + 1) {
                aroundMineNumber++;
            }
        }
        return aroundMineNumber;
    }

    /**
     * 进行预加载数据
     */
    @Override
    public void preLoadData() {
        new Thread(new Preload()).start();
    }

    /**
     * 创建新的地雷数据
     *
     * @param ignorePoint 忽略的点
     * @return 地雷数据
     */
    private MineData newMineData(Point ignorePoint) {
        //创建地雷的集合
        final HashSet<Point> mineData = createMineData(ignorePoint);
        //查找所有点周围的地雷数
        final int[][] data = findAllSpaceAroundMineNumber(getNowMineModel(), mineData);
        return new MineData(mineData, data);
    }

    /**
     * 进行定时预加载数据的类
     */
    private class Preload implements Runnable {
        /**
         * 预加载数据
         */
        @Override
        public void run() {
            try {
                final MineModel nowMineModel = getNowMineModel();
                //若改变数据，则清空预加载数据
                if (changeData) {
                    minePreloadData.clear();
                    changeData = false;
                }
                //设置预加载的大小为：最大(地雷密度*10)^3，最小8
                final int size = Math.max((int) (Math.pow(nowMineModel.getMineDensity() * 10, 4)), 16);
                //预加载容器小于设定大小时，向其中创建地雷数据
                while (minePreloadData.size() < size) {
                    minePreloadData.offer(newMineData(null));
                }
            } catch (Exception e) {
                //有异常则抛出加载游戏数据异常
                throw new RuntimeException(Information.loadGameDataError);
            }
        }
    }
}
