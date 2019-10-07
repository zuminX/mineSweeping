package com.controller;

import com.domain.GameNowStatus;
import com.domain.MineJButton;
import com.domain.MineModel;
import com.domain.GameNowData;
import com.view.MainWindow;

import javax.swing.*;
import java.awt.*;

public interface MineController {
    boolean changeModel(JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField,
                       String modelName);

    boolean setDefaultModel(JRadioButtonMenuItem easyModelButton, JRadioButtonMenuItem ordinaryModelButton, JRadioButtonMenuItem hardModelButton,
                           JRadioButtonMenuItem customizeModelButton, JTextField rowTextField, JTextField columnTextField,
                           JTextField mineNumberTextField, JTextField mineDensityTextField);

    MineModel getNowMineModel();

    boolean updateCustomizeData(String[] customizeData);

    void setWindowSize(MainWindow mainWindow, Dimension screenSize);

    GameNowData newMineViewButtons();

    void preloadMineData();

    /**
     * 加载按钮的图片
     *
     * @param gameNowData 扫雷按钮组
     */
    void loadRemainderButtonsIcon(GameNowData gameNowData);

    boolean openSpace(MineJButton button, GameNowData gameNowData);

    void setFlag(MineJButton button, JLabel remainingMineNumberLabel);

    boolean reloadGameData(GameNowData gameNowData);

    void showDynamicTime(JLabel timeLabel, GameNowStatus gameNowStatus);

    void initRemainingMineNumberLabel(JLabel remainingMineNumberLabel);

    void changeGameName(String name);

    void showNowOtherSetting(JTextField gameNameField, JCheckBox openRecordCheckBox);

    void changeOpenRecordStatus();
}
