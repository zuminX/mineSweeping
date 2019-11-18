package com.service;

import com.domain.GameNowData;
import com.domain.GameOverDialogData;
import com.domain.MineModel;
import com.pojo.MineSweepingGameData;

import java.util.List;

/**
 * 操纵扫雷数据库类的接口
 * 接收控制层的数据
 * 查询、修改数据库数据
 * 返回数据给控制层
 */
public interface MineSweepingGameDataService {
    /**
     * 根据扫雷游戏数据创建对话框数据
     *
     * @param mineSweepingGameData 扫雷游戏数据
     * @return 游戏结束对话框数据
     */
    GameOverDialogData findGameOverData(MineSweepingGameData mineSweepingGameData);

    /**
     * 查找所有模式用时最短的扫雷游戏数据
     *
     * @return 扫雷游戏数据集合
     */
    MineSweepingGameData[] findAllModelBestGameData();

    /**
     * 根据参数向数据库中插入一条扫雷游戏数据
     *
     * @param gameNowData  游戏当前数据
     * @param nowMineModel 当前扫雷模式
     * @return 扫雷游戏数据
     */
    MineSweepingGameData insert(GameNowData gameNowData, MineModel nowMineModel);

    /**
     * 查找该玩家该模式的所有扫雷游戏数据
     *
     * @param playerName 玩家名称
     * @param modelName  扫雷模式名称
     * @return 扫雷游戏数据集合
     */
    List<MineSweepingGameData> findByPlayerNameAndModelName(String playerName, String modelName);
}
