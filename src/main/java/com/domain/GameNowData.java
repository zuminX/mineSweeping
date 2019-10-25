package com.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 当前游戏模式数据
 */
@Component("gameNowData")
@Data
public class GameNowData {
    /**
     * 扫雷游戏按钮组
     */
    private MineJButton[][] buttons;
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long endTime;
    /**
     * 打开的方格数
     */
    private int openSpace;
    /**
     * 地雷个数
     */
    private int mineNumber;
    /**
     * 方块数
     */
    private int space;
    /**
     * 是否失败
     */
    private boolean isFail;

    /**
     * 设置初始化状态
     */
    public void setInitStatus() {
        isFail = false;
        openSpace = 0;
    }

    /**
     * 当打开空格数等于地图所有空格数减地雷数即为游戏胜利
     *
     * @return 胜利->true 失败->false
     */
    public boolean isWin() {
        return space - mineNumber == openSpace;
    }
}
