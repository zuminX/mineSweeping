package com.service;

import com.domain.GameNowData;
import com.domain.MineModel;
import com.domain.MineSweepingGameData;

import java.util.List;

public interface MineSweepingGameDataService {
    List<MineSweepingGameData> findByPlayerName(String playerName);

    int insert(GameNowData gameNowData, MineModel nowMineModel);
}
