package com.controller;

import com.domain.GameNowData;
import com.domain.MineJButton;
import com.domain.MineModel;
import com.view.MainWindow;

import java.awt.*;

public interface MineController {
    boolean changeModel(String modelName);

    boolean setDefaultModel();

    MineModel getNowMineModel();

    void setWindowSize(MainWindow mainWindow, Dimension screenSize);

    void newMineViewButtons();

    boolean openSpace(GameNowData gameNowData);

    void setFlag();

    boolean reloadGameData(GameNowData gameNowData);

    void showDynamicTime();

    void initRemainingMineNumberLabel();

    void showNowOtherSetting();

    void addButtonsToPanel(MineJButton[][] buttons);

    void addButtonsMouseListener();

    void removeButtonsListener();

    void saveSettingData();

    void setDefaultExpression();

    void showLeaderboard();
}
