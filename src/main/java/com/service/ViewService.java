package com.service;

import com.domain.GameOverDialogData;
import com.domain.MineJButton;
import com.domain.MineModel;
import com.domain.MineSweepingGameData;
import com.view.MainWindow;

import java.awt.*;

/**
 * 处理扫雷页面的业务层的接口
 * 接收控制层的数据
 * 返回数据给控制层
 */
public interface ViewService {
    /**
     * 根据扫雷模式设置文本框的编辑性
     *
     * @param model 扫雷模式
     */
    void setModelEnabled(MineModel model);

    /**
     * 向文本框中设置扫雷模式的数据
     *
     * @param model 扫雷模式
     */
    void setModelDataText(MineModel model);

    /**
     * 显示错误信息的提示框
     *
     * @param err 错误信息
     */
    void showErrorInformation(String err);

    /**
     * 显示信息的提示框
     *
     * @param information 信息
     */
    void showInformation(String information);

    /**
     * 设置默认模式
     *
     * @param model 模式对象
     */
    void setDefaultModel(MineModel model);

    /**
     * 设置窗口大小
     *
     * @param nowMineModel 当前扫雷模式
     * @param mainWindow   游戏窗口
     * @param screenSize   窗口大小
     */
    void setWindowSize(MineModel nowMineModel, MainWindow mainWindow, Dimension screenSize);

    /**
     * 加载剩余按钮的图片
     */
    void loadRemainderButtonsIcon();

    /**
     * 打开空白方块
     *
     * @param button 点击的按钮
     */
    boolean openSpace(MineJButton button);

    /**
     * 设置当前格子为旗帜或取消显示旗帜
     */
    void setFlag();

    /**
     * 清理视图的数据
     */
    void cleanViewData();

    /**
     * 动态显示当前时间
     */
    void showDynamicTime();

    /**
     * 初始化剩余地雷数
     *
     * @param nowMineModel 当前扫雷模式
     */
    void initRemainingMineNumberLabel(MineModel nowMineModel);

    /**
     * 显示当前的其他设置
     */
    void showNowOtherSetting();

    /**
     * 显示游戏结束后的对话框
     *
     * @param data 对话框数据
     */
    void showGameOverDialog(GameOverDialogData data);

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
     * 改变表情标签的状态
     */
    void changeExpressionStatus();

    /**
     * 设置默认的表情
     */
    void setDefaultExpression();

    /**
     * 设置各个模式最短用时的数据
     *
     * @param allModelBestGameData 所有模式最短用时的游戏数据
     */
    void setAllModelBestGameData(MineSweepingGameData[] allModelBestGameData);

    /**
     * 设置按钮的默认图片
     */
    void setMineJButtonDefaultIcon();

    /**
     * 改变按钮的图片大小
     */
    void changeButtonsIconSize();
}