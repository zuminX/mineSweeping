package com.controller.impl;

import com.controller.MineController;
import com.domain.GameNowData;
import com.domain.GameNowStatus;
import com.domain.MineJButton;
import com.domain.MineModel;
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
        final String err = mineService.changeModel(rowTextField, columnTextField, mineNumberTextField, mineDensityTextField, modelName);
        if (err != null) {
            viewService.showErrorInformation(err);
            return false;
        }
        final MineModel nowMineModel = mineService.getNowMineModel();
        viewService.setModelEnabled(nowMineModel, rowTextField, columnTextField, mineNumberTextField);
        viewService.setModelDataText(nowMineModel, rowTextField, columnTextField, mineNumberTextField, mineDensityTextField);
        return true;
    }

    @Override
    public boolean setDefaultModel(JRadioButtonMenuItem easyModelButton, JRadioButtonMenuItem ordinaryModelButton,
                                   JRadioButtonMenuItem hardModelButton, JRadioButtonMenuItem customizeModelButton, JTextField rowTextField,
                                   JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField) {
        viewService.setDefaultModel(mineService.getNowMineModel(), easyModelButton, ordinaryModelButton, hardModelButton, customizeModelButton);
        final boolean err = changeModel(rowTextField, columnTextField, mineNumberTextField, mineDensityTextField,
                mineService.getNowMineModel().getName());
        return !err;
    }

    @Override
    public MineModel getNowMineModel() {
        return mineService.getNowMineModel();
    }

    @Override
    public boolean updateCustomizeData(String[] customizeData) {
        final String err = mineService.updateCustomizeData(customizeData);
        if (err != null) {
            viewService.showErrorInformation(err);
            return false;
        }
        return true;
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
    public void preloadMineData() {
        mineService.preloadMineData();
    }

    @Override
    public void loadRemainderButtonsIcon(GameNowData gameNowData) {
        final String err = viewService.loadRemainderButtonsIcon(gameNowData);
        if (err != null) {
            viewService.showErrorInformation(err);
        }
    }

    @Override
    public boolean openSpace(MineJButton button, GameNowData gameNowData) {
        //选择合适地图
        if (gameNowData.getNowStatus().getOpenSpace() == 0) {
            mineService.fillMineData(button.getPoint(), gameNowData);
        }
        if (viewService.openSpace(button, gameNowData)) {
            viewService.showInformation("你踩到地雷了！");
            mineSweepingGameDataService.insert(gameNowData, mineService.getNowMineModel());
            return true;
        } else {
            if (gameNowData.getNowStatus().isWin()) {
                viewService.showInformation("你赢了！");
                viewService.loadRemainderButtonsIcon(gameNowData);
                mineSweepingGameDataService.insert(gameNowData, mineService.getNowMineModel());
                return true;
            }
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

}
