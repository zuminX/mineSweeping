package com.service;

import com.domain.GameOverDialogData;
import com.domain.MineModel;
import com.domain.GameNowData;
import com.domain.Point;

import javax.swing.*;

public interface MineService {
    boolean changeModel(JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField,
                        String modelName);

    MineModel getNowMineModel();

    boolean updateCustomizeData(String[] customizeData);

    GameNowData newMineViewButtons();

    void fillMineData(Point point, GameNowData gameNowData);

    boolean changeGameName(String name);

    boolean changeOpenRecordStatus();

}
