package com.service;

import com.domain.GameNowData;
import com.domain.GameOverDialogData;
import com.domain.MineModel;
import com.domain.MineSweepingGameData;

import java.util.List;

public interface MineSweepingGameDataService {
    GameOverDialogData findGameOverData(MineSweepingGameData mineSweepingGameData);

    List<MineSweepingGameData> findByPlayerName(String playerName);

    MineSweepingGameData insert(GameNowData gameNowData, MineModel nowMineModel);
}
