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

    /**
     * 扫雷地图的行
     */
    private Integer row;

    /**
     * 扫雷地图的列
     */
    private Integer column;

    /**
     * 地雷个数
     */
    private Integer mineNumber;

    /**
     * 模式名称
     */
    private String modelName;

    private static final long serialVersionUID = 1L;

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

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getMineNumber() {
        return mineNumber;
    }

    public void setMineNumber(Integer mineNumber) {
        this.mineNumber = mineNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}