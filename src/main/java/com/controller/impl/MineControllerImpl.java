package com.controller.impl;

import com.controller.MineController;
import com.domain.*;
import com.service.MineService;
import com.service.MineSweepingGameDataService;
import com.service.ViewService;
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
@Controller("mineController")
@SuppressWarnings("all")
public class MineControllerImpl implements MineController {
    /**
     * 处理扫雷游戏数据的业务层对象
     */
    @Autowired
    private MineService mineService;
    /**
     * 处理扫雷游戏显示的业务层对象
     */
    @Autowired
    private ViewService viewService;
    /**
     * 控制扫雷数据库的对象
     */
    @Autowired
    private MineSweepingGameDataService mineSweepingGameDataService;

    /**
     * 改变当前扫雷模式
     *
     * @param modelName 模式名称
     */
    @Override
    public void changeModel(String modelName) {
        //发生异常直接返回
        if (mineService.changeModel(modelName) == null) {
            return;
        }
        final MineModel nowMineModel = mineService.getNowMineModel();
        //设置显示为当前模式和数据

        viewService.setModelEnabled(nowMineModel);
        viewService.setModelDataText(nowMineModel);
    }

    /**
     * 设置默认模式
     */
    @Override
    public void setDefaultModel() {
        viewService.setDefaultModel(mineService.getNowMineModel());
        changeModel(mineService.getNowMineModel().getName());
    }

    /**
     * 获取当前扫雷模式
     *
     * @return 扫雷模式对象
     */
    @Override
    public MineModel getNowMineModel() {
        return mineService.getNowMineModel();
    }

    /**
     * 设置窗口大小
     *
     * @param mainWindow 游戏窗口
     * @param screenSize 窗口大小
     */
    @Override
    public void setWindowSize(MainWindow mainWindow, Dimension screenSize) {
        viewService.setWindowSize(mineService.getNowMineModel(), mainWindow, screenSize);
    }

    /**
     * 创建新的扫雷视图按钮组
     */
    @Override
    public void newMineViewButtons() {
        mineService.newMineViewButtons();
    }

    /**
     * 进行预加载数据
     */
    @Override
    public void preLoadData() {
        //预加载数据
        mineService.preLoadData();
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
            mineService.fillMineData(button.getPoint());
            //设置开始时间
            gameNowData.setStartTime(System.currentTimeMillis());
        }
        //游戏胜利或失败
        if (viewService.openSpace(button) || gameNowData.isWin()) {
            //设置结束时间
            gameNowData.setEndTime(System.currentTimeMillis());

            //改变当前表情
            viewService.changeExpressionStatus();

            //加载剩余按钮图片
            viewService.loadRemainderButtonsIcon();

            //移除按钮监听器
            viewService.removeButtonsListener();

            //向数据库插入本局游戏数据
            MineSweepingGameData gameData = mineSweepingGameDataService.insert(gameNowData, mineService.getNowMineModel());

            //获得结束游戏的显示数据
            GameOverDialogData data = mineSweepingGameDataService.findGameOverData(gameData);

            //以弹出框的形式显示游戏结束的数据
            viewService.showGameOverDialog(data);

        }
    }

    /**
     * 设置当前格子为旗帜
     */
    @Override
    public void setFlag() {
        viewService.setFlag();
    }

    /**
     * 重新加载当前游戏的数据
     *
     * @param gameNowData 扫雷当前数据对象
     *
     * @return 重新加载失败->true 重新加载成功->false
     */
    @Override
    public boolean reloadGameData(GameNowData gameNowData) {
        //当前游戏还未加载
        if (gameNowData == null || gameNowData.getButtons() == null) {
            return true;
        }
        //清理视图数据
        viewService.cleanViewData();
        //预加载数据
        return false;
    }

    /**
     * 动态显示当前时间
     */
    @Override
    public void showDynamicTime() {
        viewService.showDynamicTime();
    }

    /**
     * 初始化剩余地雷数
     */
    @Override
    public void initRemainingMineNumberLabel() {
        viewService.initRemainingMineNumberLabel(mineService.getNowMineModel());
    }

    /**
     * 显示当前的其他设置
     */
    @Override
    public void showNowOtherSetting() {
        viewService.showNowOtherSetting();
    }

    /**
     * 添加按钮组到面板上
     *
     * @param buttons 按钮组
     */
    @Override
    public void addButtonsToPanel(MineJButton[][] buttons) {
        viewService.addButtonsToPanel(buttons);
    }

    /**
     * 为按钮组添加监听器
     */
    @Override
    public void addButtonsMouseListener() {
        viewService.addButtonsMouseListener();
    }

    /**
     * 移除按钮组的监听器
     */
    @Override
    public void removeButtonsListener() {
        viewService.removeButtonsListener();
    }

    /**
     * 保存设置的数据
     */
    @Override
    public void saveSettingData() {
        if (mineService.saveSettingData() != null) {
            viewService.showInformation(Information.saveDataSucceed);
        }
    }

    /**
     * 设置默认的表情
     */
    @Override
    public void setDefaultExpression() {
        viewService.setDefaultExpression();
    }

    /**
     * 显示排行榜
     */
    @Override
    public void showLeaderboard() {
        final MineSweepingGameData[] allModelBestGameData = mineSweepingGameDataService.findAllModelBestGameData();
        viewService.setAllModelBestGameData(allModelBestGameData);
    }

    /**
     * 设置按钮的默认图片
     */
    @Override
    public void setMineJButtonDefaultIcon() {
        viewService.setMineJButtonDefaultIcon();
    }

    /**
     * 改变按钮的图片大小
     */
    @Override
    public void changeButtonsIconSize() {
        viewService.changeButtonsIconSize();
    }

}
