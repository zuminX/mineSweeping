package com.domain;

import lombok.Data;

/**
 * 游戏结束显示的数据
 */
@Data
public class GameOverDialogData {
    /**
     * 本局游戏所用时间
     */
    private String nowGameTime;
    /**
     * 当前模式胜利次数
     */
    private String winsNumber;
    /**
     * 平均胜率
     */
    private String averageWinPercentage;
    /**
     * 当前模式失败次数
     */
    private String failNumber;
    /**
     * 平均胜利局数用时
     */
    private String averageTime;
    /**
     * 所有游戏次数
     */
    private String allGameNumber;
    /**
     * 胜利最短用时
     */
    private String shortestTime;
    /**
     * 本局游戏是否胜利
     */
    private boolean isWin;
    /**
     * 本局游戏是否打破记录
     */
    private boolean isBreakRecord;

}
