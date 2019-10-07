package com.service;

import com.domain.MineModel;
import com.domain.GameNowData;
import com.domain.Point;

import javax.swing.*;

public interface MineService {
    String changeModel(JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField,
                       String modelName);

    MineModel getNowMineModel();

    String updateCustomizeData(String[] customizeData);

    GameNowData newMineViewButtons();

    void fillMineData(Point point, GameNowData gameNowData);

    void preloadMineData();

    void changeGameName(String name);

    void changeOpenRecordStatus();
}
