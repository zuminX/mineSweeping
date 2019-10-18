package com.controller.impl;

import com.controller.MineController;
import com.domain.*;
import com.service.MineService;
import com.service.MineSweepingGameDataService;
import com.service.ViewService;
import com.utils.BaseHolder;
import com.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    public boolean changeModel(String modelName) {
        if (mineService.changeModel(modelName) == null) {
            return true;
        }
        final MineModel nowMineModel = mineService.getNowMineModel();
        viewService.setModelEnabled(nowMineModel);
        viewService.setModelDataText(nowMineModel);
        return false;
    }

    @Override
    public boolean setDefaultModel() {
        viewService.setDefaultModel(mineService.getNowMineModel());
        return changeModel(mineService.getNowMineModel().getName());
    }

    @Override
    public MineModel getNowMineModel() {
        return mineService.getNowMineModel();
    }

    @Override
    public void setWindowSize(MainWindow mainWindow, Dimension screenSize) {
        viewService.setWindowSize(mineService.getNowMineModel(), mainWindow, screenSize);
    }

    @Override
    public void newMineViewButtons() {
        mineService.newMineViewButtons();
    }

    @Override
    public boolean openSpace(GameNowData gameNowData) {
        //选择合适地图，规避第一步就是雷
        MineJButton button = BaseHolder.getBean("viewComponent", ViewComponent.class).getNowClickButton();
        if (gameNowData.getOpenSpace() == 0) {
            mineService.fillMineData(button.getPoint(), gameNowData);
            gameNowData.setStartTime(System.currentTimeMillis());
        }
        if (viewService.openSpace(button, gameNowData) || gameNowData.isWin()) {
            gameNowData.setEndTime(System.currentTimeMillis());

            viewService.changeExpressionStatus();

            viewService.loadRemainderButtonsIcon(gameNowData);
            MineSweepingGameData gameData = mineSweepingGameDataService.insert(gameNowData, mineService.getNowMineModel());
            GameOverDialogData data = mineSweepingGameDataService.findGameOverData(gameData);

            viewService.showGameOverDialog(data);
            return true;
        }
        return false;
    }

    @Override
    public void setFlag() {
        viewService.setFlag();
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
    public void showDynamicTime() {
        viewService.showDynamicTime();
    }

    @Override
    public void initRemainingMineNumberLabel() {
        viewService.initRemainingMineNumberLabel(mineService.getNowMineModel());
    }

    @Override
    public void showNowOtherSetting() {
        viewService.showNowOtherSetting();
    }

    @Override
    public void addButtonsToPanel(MineJButton[][] buttons) {
        viewService.addButtonsToPanel(buttons);
    }

    @Override
    public void addButtonsMouseListener() {
        viewService.addButtonsMouseListener();
    }

    @Override
    public void removeButtonsListener() {
        viewService.removeButtonsListener();
    }

    @Override
    public void saveSettingData() {
        if (mineService.saveSettingData() != null) {
            viewService.showInformation("保存成功！");
        }
    }

    @Override
    public void setDefaultExpression() {
        viewService.setDefaultExpression();
    }

    @Override
    public void showLeaderboard() {
        final MineSweepingGameData[] allModelBestGameData = mineSweepingGameDataService.findAllModelBestGameData();
        viewService.setAllModelBestGameData(allModelBestGameData);
    }

}
