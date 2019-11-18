package com.controller.impl;

import com.controller.GameController;
import com.domain.*;
import com.pojo.MineSweepingGameData;
import com.service.GameDataService;
import com.service.GameViewService;
import com.service.MineSweepingGameDataService;
import com.utils.BaseHolder;
import com.utils.Information;
import com.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;

/**
 * 控制层
 * 接收视图层的数据,数据传递给业务层
 * 接受业务层返回的数据并返回给视图层
 */
@Controller("gameController")
public class GameControllerImpl implements GameController {
    /**
     * 处理扫雷游戏数据的业务层对象
     */
    private final GameDataService gameDataService;
    /**
     * 处理扫雷游戏显示的业务层对象
     */
    private final GameViewService gameViewService;
    /**
     * 控制扫雷数据库的对象
     */
    private final MineSweepingGameDataService mineSweepingGameDataService;

    /**
     * 注入成员变量
     */
    @Autowired
    public GameControllerImpl(GameDataService gameDataService, GameViewService gameViewService,
                              MineSweepingGameDataService mineSweepingGameDataService) {
        this.gameDataService = gameDataService;
        this.gameViewService = gameViewService;
        this.mineSweepingGameDataService = mineSweepingGameDataService;
    }

    /**
     * 改变当前扫雷模式
     *
     * @param modelName 模式名称
     */
    @Override
    public void changeModel(String modelName) {
        //发生异常直接返回
        if (gameDataService.changeModel(modelName) == null) {
            return;
        }
        final MineModel nowMineModel = gameDataService.getNowMineModel();

        //设置显示为当前模式和数据
        gameViewService.setModelEnabled(nowMineModel);
        gameViewService.setModelDataText(nowMineModel);
    }

    /**
     * 设置默认模式
     */
    @Override
    public void setDefaultModel() {
        gameViewService.setDefaultModel(gameDataService.getNowMineModel());
        changeModel(gameDataService.getNowMineModel().getName());
    }

    /**
     * 获取当前扫雷模式
     *
     * @return 扫雷模式对象
     */
    @Override
    public MineModel getNowMineModel() {
        return gameDataService.getNowMineModel();
    }

    /**
     * 设置窗口大小
     *
     * @param mainWindow 游戏窗口
     * @param screenSize 窗口大小
     */
    @Override
    public void setWindowSize(MainWindow mainWindow, Dimension screenSize) {
        gameViewService.setWindowSize(gameDataService.getNowMineModel(), mainWindow, screenSize);
    }

    /**
     * 创建新的扫雷视图按钮组
     */
    @Override
    public void newMineViewButtons() {
        gameDataService.newMineViewButtons();
    }

    /**
     * 进行预加载数据
     */
    @Override
    public void preLoadData() {
        gameDataService.preLoadData();
    }

    /**
     * 打开空白方块
     *
     * @param gameNowData 扫雷当前数据对象
     */
    @Override
    public void openSpace(GameNowData gameNowData) {
        MineJButton button = BaseHolder.getBean("viewComponent", ViewComponent.class).getNowClickButton();
        //选择合适地图，规避第一步就是雷
        if (gameNowData.getOpenSpace() == 0) {
            gameDataService.fillMineData(button.getPoint());
            //设置开始时间
            gameNowData.setStartTime(System.currentTimeMillis());
        }
        //游戏胜利或失败
        if (gameViewService.openSpace(button) || gameNowData.isWin()) {
            //设置结束时间
            gameNowData.setEndTime(System.currentTimeMillis());

            //改变当前表情
            gameViewService.changeExpressionStatus();

            //加载剩余按钮图片
            gameViewService.loadRemainderButtonsIcon();

            //移除按钮监听器
            gameViewService.removeButtonsListener();

            //向数据库插入本局游戏数据
            MineSweepingGameData gameData = mineSweepingGameDataService.insert(gameNowData, gameDataService.getNowMineModel());

            //获得结束游戏的显示数据
            GameOverDialogData data = mineSweepingGameDataService.findGameOverData(gameData);

            //以弹出框的形式显示游戏结束的数据
            gameViewService.showGameOverDialog(data);

        }
    }

    /**
     * 设置当前格子为旗帜
     */
    @Override
    public void setFlag() {
        gameViewService.setFlag();
    }

    /**
     * 重新加载当前游戏的数据
     *
     * @param gameNowData 扫雷当前数据对象
     * @return 重新加载失败->true 重新加载成功->false
     */
    @Override
    public boolean reloadGameData(GameNowData gameNowData) {
        //当前游戏还未加载
        if (gameNowData == null || gameNowData.getButtons() == null) {
            return true;
        }
        //清理视图数据
        gameViewService.cleanViewData();
        //预加载数据
        return false;
    }

    /**
     * 动态显示当前时间
     */
    @Override
    public void showDynamicTime() {
        gameViewService.showDynamicTime();
    }

    /**
     * 初始化剩余地雷数
     */
    @Override
    public void initRemainingMineNumberLabel() {
        gameViewService.initRemainingMineNumberLabel(gameDataService.getNowMineModel());
    }

    /**
     * 显示当前的其他设置
     */
    @Override
    public void showNowOtherSetting() {
        gameViewService.showNowOtherSetting();
    }

    /**
     * 添加按钮组到面板上
     *
     * @param buttons 按钮组
     */
    @Override
    public void addButtonsToPanel(MineJButton[][] buttons) {
        gameViewService.addButtonsToPanel(buttons);
    }

    /**
     * 为按钮组添加监听器
     */
    @Override
    public void addButtonsMouseListener() {
        gameViewService.addButtonsMouseListener();
    }

    /**
     * 移除按钮组的监听器
     */
    @Override
    public void removeButtonsListener() {
        gameViewService.removeButtonsListener();
    }

    /**
     * 保存设置的数据
     */
    @Override
    public void saveSettingData() {
        if (gameDataService.saveSettingData() != null) {
            gameViewService.showInformation(Information.saveDataSucceed);
        }
    }

    /**
     * 设置默认的表情
     */
    @Override
    public void setDefaultExpression() {
        gameViewService.setDefaultExpression();
    }

    /**
     * 显示排行榜
     */
    @Override
    public void showLeaderboard() {
        final MineSweepingGameData[] allModelBestGameData = mineSweepingGameDataService.findAllModelBestGameData();
        gameViewService.setAllModelBestGameData(allModelBestGameData);
    }

    /**
     * 设置按钮的默认图片
     */
    @Override
    public void setMineJButtonDefaultIcon() {
        gameViewService.setMineJButtonDefaultIcon();
    }

    /**
     * 改变按钮的图片大小
     */
    @Override
    public void changeButtonsIconSize() {
        gameViewService.changeButtonsIconSize();
    }

}
