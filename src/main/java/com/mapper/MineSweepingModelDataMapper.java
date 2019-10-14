package com.mapper;

import com.domain.MineSweepingModelData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MineSweepingModelDataMapper {
    int insert(MineSweepingModelData record);

    int updateByModelId(@Param("updated")MineSweepingModelData updated,@Param("modelId")Integer modelId);

    MineSweepingModelData findOneByModelName(@Param("modelName")String modelName);

}