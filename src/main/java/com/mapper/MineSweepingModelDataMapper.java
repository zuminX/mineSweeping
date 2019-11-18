package com.mapper;

import com.pojo.MineSweepingModelData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 对数据库中扫雷模式数据进行操作的类
 * 建立实体与表的映射关系
 */
@Mapper
public interface MineSweepingModelDataMapper {
    /**
     * 向数据库插入一条扫雷模式数据
     *
     * @param mineSweepingModelData 扫雷模式数据
     * @return 插入行数
     */
    int insert(MineSweepingModelData mineSweepingModelData);

    /**
     * 通过id更新扫雷模式数据
     *
     * @param mineSweepingModelData 扫雷模式数据
     * @param modelId               扫雷模式的id
     * @return 更新行数
     */
    int updateByModelId(@Param("mineSweepingModelData") MineSweepingModelData mineSweepingModelData, @Param("modelId") Integer modelId);

    /**
     * 根据名称查找扫雷模式数据
     *
     * @param modelName 模式名称
     * @return 扫雷模式数据
     */
    MineSweepingModelData findOneByModelName(@Param("modelName") String modelName);

}