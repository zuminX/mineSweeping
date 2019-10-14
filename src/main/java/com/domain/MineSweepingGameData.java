package com.domain;

import java.io.Serializable;

public class MineSweepingGameData implements Serializable {
    /**
     * 数据的id
     */
    private Integer dataId;

    /**
     * 玩家名
     */
    private String playerName;

    /**
     * 游戏时间
     */
    private Long time;

    /**
     * 是否获胜
     */
    private Byte isWin;

    private MineSweepingModelData mineSweepingModelData;

    private static final long serialVersionUID = 1L;

    public MineSweepingModelData getMineSweepingModelData() {
        return mineSweepingModelData;
    }

    public void setMineSweepingModelData(MineSweepingModelData mineSweepingModelData) {
        this.mineSweepingModelData = mineSweepingModelData;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Byte getIsWin() {
        return isWin;
    }

    public void setIsWin(Byte isWin) {
        this.isWin = isWin;
    }
}