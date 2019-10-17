package com.service;

import com.domain.GameOverDialogData;
import com.domain.MineModel;
import com.domain.GameNowData;
import com.domain.Point;

import javax.swing.*;

public interface MineService {
    Boolean changeModel(String modelName);

    MineModel getNowMineModel();

    GameNowData newMineViewButtons();

    void fillMineData(Point point, GameNowData gameNowData);

    Boolean saveSettingData();
}
