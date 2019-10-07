package com.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.domain.MineSweepingGameData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Mapper
public interface MineSweepingGameDataMapper {
    int insert(MineSweepingGameData record);

    List<MineSweepingGameData> findByPlayerName(@Param("playerName")String playerName);

}