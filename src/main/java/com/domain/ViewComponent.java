package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * 封装显示的组件
 */
@Component("viewComponent")
@Data
public class ViewComponent {
    /**
     * 当前点击的按钮
     */
    private MineJButton nowClickButton;
    /**
     * 主面板
     */
    private JPanel mainPanel;
    /**
     * 显示剩余地雷个数的标签
     */
    private JLabel remainingMineNumberLabel;
    /**
     * 表情的标签
     */
    private JLabel expressionLabel;
    /**
     * 显示时间的标签
     */
    private JLabel timeLabel;
    /**
     * 显示按钮的面板
     */
    private JPanel buttonsPanel;
    /**
     * 简单模式的单选按钮
     */
    private JRadioButtonMenuItem easyModelRadioButtonMenuItem;
    /**
     * 普通模式的单选按钮
     */
    private JRadioButtonMenuItem ordinaryModelRadioButtonMenuItem;
    /**
     * 困难模式的单选按钮
     */
    private JRadioButtonMenuItem hardModelRadioButtonMenuItem;
    /**
     * 自定义模式的单选按钮
     */
    private JRadioButtonMenuItem customizeModelRadioButtonMenuItem;
    /**
     * 行数的文本框
     */
    private JTextField rowTextField;
    /**
     * 列数的文本框
     */
    private JTextField columnTextField;
    /**
     * 地雷个数的文本框
     */
    private JTextField mineNumberTextField;
    /**
     * 地雷密度的文本框
     */
    private JTextField mineDensityTextField;
    /**
     * 游戏名称的文本框
     */
    private JTextField gameNameField;
    /**
     * 开启记录的选项
     */
    private JCheckBox openRecordCheckBox;
    /**
     * 简单模式最短用时玩家名称的标签
     */
    private JLabel easyModelBestPlayerNameLabel;
    /**
     * 简单模式最短用时时间的标签
     */
    private JLabel easyModelBestTimeLabel;
    /**
     * 普通模式最短用时玩家名称的标签
     */
    private JLabel ordinaryModelBestPlayerNameLabel;
    /**
     * 普通模式最短用时时间的标签
     */
    private JLabel ordinaryModelBestTimeLabel;
    /**
     * 困难模式最短用时玩家名称的标签
     */
    private JLabel hardModelBestPlayerNameLabel;
    /**
     * 困难模式最短用时时间的标签
     */
    private JLabel hardModelBestTimeLabel;
    /**
     * 自定义模式最短用时玩家名称的标签
     */
    private JLabel customizeModelBestPlayerNameLabel;
    /**
     * 自定义模式最短用时时间的标签
     */
    private JLabel customizeModelBestTimeLabel;
}
