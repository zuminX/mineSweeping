package com.service.impl;

import com.dao.MineDao;
import com.domain.GameNowData;
import com.domain.GameNowStatus;
import com.domain.MineModel;
import com.domain.MineSweepingGameData;
import com.mapper.MineSweepingGameDataMapper;
import com.service.MineSweepingGameDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service("mineSweepingGameData")
public class MineSweepingGameDataServiceImpl implements MineSweepingGameDataService {

    @Resource
    private MineSweepingGameDataMapper mineSweepingGameDataMapper;

    @Autowired
    private MineDao mineDao;

    @Override
    public int insert(GameNowData gameNowData, MineModel nowMineModel) {
        MineSweepingGameData gameData = new MineSweepingGameData();
        GameNowStatus status = gameNowData.getNowStatus();
        gameData.setIsWin((byte) (status.isWin()?1:0));
        try {
            gameData.setPlayerName(mineDao.findNowGameName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameData.setTime(status.getEndTime()-status.getStartTime());
        gameData.setRow(nowMineModel.getRow());
        gameData.setColumn(nowMineModel.getColumn());
        gameData.setMineNumber(nowMineModel.getMineNumber());
        gameData.setModelName(nowMineModel.getName());
        return mineSweepingGameDataMapper.insert(gameData);
    }

    @Override
    public List<MineSweepingGameData> findByPlayerName(String playerName) {
        return mineSweepingGameDataMapper.findByPlayerName(playerName);
    }

}
