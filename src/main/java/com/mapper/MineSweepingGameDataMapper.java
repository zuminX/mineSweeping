package com.mapper;

import com.domain.MineSweepingGameData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对数据库中扫雷数据进行操作的类
 * 建立实体与表的映射关系
 */
@Mapper
public interface MineSweepingGameDataMapper {
    /**
     * 向数据库插入一条扫雷数据
     *
     * @param mineSweepingGameData 本局扫雷数据
     *
     * @return 插入行数
     */
    int insert(MineSweepingGameData mineSweepingGameData);

    /**
     * 查找指定玩家的扫雷数据
     *
     * @param playerName 玩家名称
     *
     * @return 该玩家的扫雷数据集合
     */
    List<MineSweepingGameData> findByPlayerName(@Param("playerName") String playerName);

    /**
     * 查找指定玩家指定模式的扫雷数据
     *
     * @param playerName 玩家名称
     * @param modelName  扫雷模式名称
     *
     * @return 该玩家某个模式的扫雷数据集合
     */
    List<MineSweepingGameData> findByPlayerNameAndModelName(@Param("playerName") String playerName, @Param("modelName") String modelName);

    /**
     * 查找指定模式最短用时的扫雷数据
     *
     * @param modelName 扫雷模式名称
     *
     * @return 扫雷数据
     */
    MineSweepingGameData findByModelNameOrderByTime(@Param("modelName") String modelName);

}