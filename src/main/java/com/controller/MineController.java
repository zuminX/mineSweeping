package com.controller;

import com.domain.GameNowData;
import com.domain.MineJButton;
import com.domain.MineModel;
import com.view.MainWindow;

import java.awt.*;

/**
 * 控制层的接口
 * <p>
 * 接收视图层的数据,数据传递给业务层
 * 接受业务层返回的数据并返回给视图层
 */
public interface MineController {

    /**
     * 改变当前扫雷模式
     *
     * @param modelName 模式名称
     */
    void changeModel(String modelName);

    /**
     * 设置默认模式
     */
    void setDefaultModel();

    /**
     * 获取当前扫雷模式
     *
     * @return 扫雷模式对象
     */
    MineModel getNowMineModel();

    /**
     * 设置窗口大小
     *
     * @param mainWindow 游戏窗口
     * @param screenSize 窗口大小
     */
    void setWindowSize(MainWindow mainWindow, Dimension screenSize);

    /**
     * 创建新的扫雷视图按钮组
     */
    void newMineViewButtons();

    /**
     * 进行预加载数据
     */
    void preLoadData();

    /**
     * 打开空白方块
     *
     * @param gameNowData 扫雷当前数据对象
     */
    void openSpace(GameNowData gameNowData);

    /**
     * 设置当前格子为旗帜
     */
    void setFlag();

    /**
     * 重新加载当前游戏的数据
     *
     * @param gameNowData 扫雷当前数据对象
     *
     * @return 重新加载失败->true 重新加载成功->false
     */
    boolean reloadGameData(GameNowData gameNowData);

    /**
     * 动态显示当前时间
     */
    void showDynamicTime();

    /**
     * 初始化剩余地雷数
     */
    void initRemainingMineNumberLabel();

    /**
     * 显示当前的其他设置
     */
    void showNowOtherSetting();

    /**
     * 添加按钮组到面板上
     *
     * @param buttons 按钮组
     */
    void addButtonsToPanel(MineJButton[][] buttons);

    /**
     * 为按钮组添加监听器
     */
    void addButtonsMouseListener();

    /**
     * 移除按钮组的监听器
     */
    void removeButtonsListener();

    /**
     * 保存设置的数据
     */
    void saveSettingData();

    /**
     * 设置默认的表情
     */
    void setDefaultExpression();

    /**
     * 显示排行榜
     */
    void showLeaderboard();

    /**
     * 设置按钮的默认图片
     */
    void setMineJButtonDefaultIcon();

    /**
     * 改变按钮的图片大小
     */
    void changeButtonsIconSize();
}
