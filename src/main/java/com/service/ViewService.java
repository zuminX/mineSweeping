package com.service;

import com.domain.*;
import com.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public interface ViewService {
    void setModelEnabled(MineModel model);

    void setModelDataText(MineModel model);

    void showErrorInformation(String err);

    void showInformation(String information);

    void setDefaultModel(MineModel model);

    void setWindowSize(MineModel nowMineModel, MainWindow mainWindow, Dimension screenSize);

    void loadRemainderButtonsIcon(GameNowData gameNowData);

    boolean openSpace(MineJButton button, GameNowData gameNowData);

    void setFlag();

    void cleanViewData(GameNowData gameNowData);

    void showDynamicTime(GameNowStatus gameNowStatus);

    void initRemainingMineNumberLabel(MineModel nowMineModel);

    void showNowOtherSetting();

    void showGameOverDialog(GameOverDialogData data);

    void addButtonsToPanel(MineJButton[][] buttons);

    void addButtonsMouseListener(MineJButton[][] buttons);

    void removeButtonsListener(MineJButton[][] buttons);
}