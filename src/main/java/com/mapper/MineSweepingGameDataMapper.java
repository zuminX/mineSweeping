package com.mapper;

import com.domain.MineSweepingGameData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MineSweepingGameDataMapper {

    int insert(MineSweepingGameData record);

    List<MineSweepingGameData> findByPlayerName(@Param("playerName") String playerName);

    List<MineSweepingGameData> findByPlayerNameAndModelName(@Param("playerName") String playerName, @Param("modelName") String modelName);

    MineSweepingGameData findByModelNameOrderByTime(@Param("modelName")String modelName);

}