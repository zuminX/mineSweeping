package com.service;

import com.domain.*;
import com.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public interface ViewService {
    void setModelEnabled(MineModel model, JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField);

    void setModelDataText(MineModel model, JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField,
                          JTextField mineDensityTextField);

    void showErrorInformation(String err);

    void showInformation(String information);

    void setDefaultModel(MineModel model, JRadioButtonMenuItem easyModelButton, JRadioButtonMenuItem ordinaryModelButton,
                         JRadioButtonMenuItem hardModelButton, JRadioButtonMenuItem customizeModelButton);

    void setWindowSize(MineModel nowMineModel, MainWindow mainWindow, Dimension screenSize);

    String loadRemainderButtonsIcon(GameNowData gameNowData);

    boolean openSpace(MineJButton button, GameNowData gameNowData);

    String setFlag(MineJButton button, JLabel remainingMineNumberLabel);

    void cleanViewData(GameNowData gameNowData);

    void showDynamicTime(JLabel timeLabel, GameNowStatus gameNowStatus);

    void initRemainingMineNumberLabel(JLabel remainingMineNumberLabel, MineModel nowMineModel);

    void showNowOtherSetting(JTextField gameNameField, JCheckBox openRecordCheckBox);

    void showGameOverDialog(GameOverDialogData data);

    void addButtonsToPanel(MineJButton[][] buttons, JPanel buttonsPanel);

    void addButtonsMouseListener(MineJButton[][] buttons);

    void removeButtonsListener(MineJButton[][] buttons);
}