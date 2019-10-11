package com.controller.impl;

import com.controller.MineController;
import com.domain.*;
import com.service.MineService;
import com.service.MineSweepingGameDataService;
import com.service.ViewService;
import com.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;

@Controller("mineController")
public class MineControllerImpl implements MineController {
    @Autowired
    private MineService mineService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private MineSweepingGameDataService mineSweepingGameDataService;

    @Override
    public boolean changeModel(JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField,
                               String modelName) {
        final boolean err = mineService.changeModel(rowTextField, columnTextField, mineNumberTextField, mineDensityTextField, modelName);
        if (err) {
            return true;
        }
        final MineModel nowMineModel = mineService.getNowMineModel();
        viewService.setModelEnabled(nowMineModel, rowTextField, columnTextField, mineNumberTextField);
        viewService.setModelDataText(nowMineModel, rowTextField, columnTextField, mineNumberTextField, mineDensityTextField);
        return false;
    }

    @Override
    public boolean setDefaultModel(JRadioButtonMenuItem easyModelButton, JRadioButtonMenuItem ordinaryModelButton,
                                   JRadioButtonMenuItem hardModelButton, JRadioButtonMenuItem customizeModelButton, JTextField rowTextField,
                                   JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField) {
        viewService.setDefaultModel(mineService.getNowMineModel(), easyModelButton, ordinaryModelButton, hardModelButton, customizeModelButton);
        return changeModel(rowTextField, columnTextField, mineNumberTextField, mineDensityTextField, mineService.getNowMineModel().getName());
    }

    @Override
    public MineModel getNowMineModel() {
        return mineService.getNowMineModel();
    }

    @Override
    public boolean updateCustomizeData(String[] customizeData) {
        return mineService.updateCustomizeData(customizeData);
    }

    @Override
    public void setWindowSize(MainWindow mainWindow, Dimension screenSize) {
        viewService.setWindowSize(mineService.getNowMineModel(), mainWindow, screenSize);
    }

    @Override
    public GameNowData newMineViewButtons() {
        return mineService.newMineViewButtons();
    }

    @Override
    public boolean openSpace(MineJButton button, GameNowData gameNowData) {
        //选择合适地图，规避第一步就是雷
        GameNowStatus nowStatus = gameNowData.getNowStatus();
        if (nowStatus.getOpenSpace() == 0) {
            mineService.fillMineData(button.getPoint(), gameNowData);
            nowStatus.setStartTime(System.currentTimeMillis());
        }
        if (viewService.openSpace(button, gameNowData) || nowStatus.isWin()) {
            nowStatus.setEndTime(System.currentTimeMillis());

            viewService.loadRemainderButtonsIcon(gameNowData);
            MineSweepingGameData gameData = mineSweepingGameDataService.insert(gameNowData, mineService.getNowMineModel());
            GameOverDialogData data = mineSweepingGameDataService.findGameOverData(gameData);

            viewService.showGameOverDialog(data);
            return true;
        }
        return false;
    }

    @Override
    public void setFlag(MineJButton button, JLabel remainingMineNumberLabel) {
        viewService.setFlag(button, remainingMineNumberLabel);
    }

    @Override
    public boolean reloadGameData(GameNowData gameNowData) {
        if (gameNowData == null || gameNowData.getButtons() == null) {
            return true;
        }
        viewService.cleanViewData(gameNowData);
        return false;
    }

    @Override
    public void showDynamicTime(JLabel timeLabel, GameNowStatus gameNowStatus) {
        viewService.showDynamicTime(timeLabel, gameNowStatus);
    }

    @Override
    public void initRemainingMineNumberLabel(JLabel remainingMineNumberLabel) {
        viewService.initRemainingMineNumberLabel(remainingMineNumberLabel, mineService.getNowMineModel());
    }

    @Override
    public void changeGameName(String name) {
        mineService.changeGameName(name);
    }

    @Override
    public void showNowOtherSetting(JTextField gameNameField, JCheckBox openRecordCheckBox) {
        viewService.showNowOtherSetting(gameNameField, openRecordCheckBox);
    }

    @Override
    public void changeOpenRecordStatus() {
        mineService.changeOpenRecordStatus();
    }

    @Override
    public void addButtonsToPanel(MineJButton[][] buttons, JPanel buttonsPanel) {
        viewService.addButtonsToPanel(buttons, buttonsPanel);
    }

    @Override
    public void addButtonsMouseListener(MineJButton[][] buttons) {
        viewService.addButtonsMouseListener(buttons);
    }

    @Override
    public void removeButtonsListener(MineJButton[][] buttons) {
        viewService.removeButtonsListener(buttons);
    }

}
