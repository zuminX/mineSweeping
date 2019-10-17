package com.controller;

import com.domain.GameNowData;
import com.domain.GameNowStatus;
import com.domain.MineJButton;
import com.domain.MineModel;
import com.view.MainWindow;

import java.awt.*;

public interface MineController {
    boolean changeModel(String modelName);

    boolean setDefaultModel();

    MineModel getNowMineModel();

    void setWindowSize(MainWindow mainWindow, Dimension screenSize);

    GameNowData newMineViewButtons();

    boolean openSpace(GameNowData gameNowData);

    void setFlag();

    boolean reloadGameData(GameNowData gameNowData);

    void showDynamicTime(GameNowStatus gameNowStatus);

    void initRemainingMineNumberLabel();

    void showNowOtherSetting();

    void addButtonsToPanel(MineJButton[][] buttons);

    void addButtonsMouseListener(MineJButton[][] buttons);

    void removeButtonsListener(MineJButton[][] buttons);

    void saveSettingData();
}
