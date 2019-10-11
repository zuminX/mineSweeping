package com.service.impl;

import com.dao.MineDao;
import com.domain.*;
import com.mapper.MineSweepingGameDataMapper;
import com.service.MineSweepingGameDataService;
import com.utils.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service("mineSweepingGameData")
public class MineSweepingGameDataServiceImpl implements MineSweepingGameDataService {

    @Resource
    private MineSweepingGameDataMapper mineSweepingGameDataMapper;

    @Autowired
    private MineDao mineDao;

    @Override
    public MineSweepingGameData insert(GameNowData gameNowData, MineModel nowMineModel) {
        MineSweepingGameData gameData = new MineSweepingGameData();
        GameNowStatus status = gameNowData.getNowStatus();

        gameData.setIsWin((byte) (status.isWin() ? 1 : 0));
        try {
            gameData.setPlayerName(mineDao.findNowGameName());
        } catch (IOException e) {
            throw new RuntimeException(Information.playerDataError);
        }
        gameData.setTime(status.getEndTime() - status.getStartTime());
        gameData.setRow(nowMineModel.getRow());
        gameData.setColumn(nowMineModel.getColumn());
        gameData.setMineNumber(nowMineModel.getMineNumber());
        gameData.setModelName(nowMineModel.getName());

        mineSweepingGameDataMapper.insert(gameData);

        return gameData;
    }

    @Override
    public GameOverDialogData findGameOverData(MineSweepingGameData mineSweepingGameData) {
        String name;
        GameOverDialogData data = new GameOverDialogData();

        try {
            name = mineDao.findNowGameName();
        } catch (IOException e) {
            throw new RuntimeException(Information.playerDataError);
        }
        List<MineSweepingGameData> mineSweepingGameDataList = findByPlayerName(name);

        int gameNumber = mineSweepingGameDataList.size();
        int winsNumber = findWinsNumber(mineSweepingGameDataList);
        long shortestTime = findShortestTime(mineSweepingGameDataList);
        long time = mineSweepingGameData.getTime();

        DecimalFormat format = new DecimalFormat("0.00");

        data.setNowGameTime(time + "ms");
        data.setWinsNumber(winsNumber + "");
        data.setAverageWinPercentage(gameNumber==0?"-":(format.format((double)winsNumber/gameNumber))+"%");
        data.setFailNumber((gameNumber - winsNumber) + "");
        data.setAverageTimePercentage(winsNumber==0?"-":(format.format((double)findAllWinGameTime(mineSweepingGameDataList)/winsNumber))+"ms");
        data.setAllGameNumber(gameNumber + "");
        data.setShortestTime(shortestTime == Long.MAX_VALUE ? "-" : shortestTime + "ms");
        data.setWin(mineSweepingGameData.getIsWin() == 1);
        data.setBreakRecord(time == shortestTime);

        return data;
    }

    private int findWinsNumber(List<MineSweepingGameData> mineSweepingGameDataList) {
        int winsNumber = 0;
        for (MineSweepingGameData data : mineSweepingGameDataList) {
            if (data.getIsWin() == 1) {
                winsNumber++;
            }
        }
        return winsNumber;
    }

    private long findShortestTime(List<MineSweepingGameData> mineSweepingGameDataList) {
        long shortestTime = Long.MAX_VALUE;
        for (MineSweepingGameData data : mineSweepingGameDataList) {
            Long dataTime = data.getTime();
            if (data.getIsWin() == 1 && dataTime < shortestTime) {
                shortestTime = dataTime;
            }
        }
        return shortestTime;
    }

    private long findAllWinGameTime(List<MineSweepingGameData> mineSweepingGameDataList) {
        long allWinTime = 0L;
        for (MineSweepingGameData data : mineSweepingGameDataList) {
            if (data.getIsWin() == 1) {
                allWinTime += data.getTime();
            }
        }
        return allWinTime;
    }

    @Override
    public List<MineSweepingGameData> findByPlayerName(String playerName) {
        return mineSweepingGameDataMapper.findByPlayerName(playerName);
    }

}
