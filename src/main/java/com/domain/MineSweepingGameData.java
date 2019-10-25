package com.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装一局扫雷游戏数据
 * 写入数据库中
 */
@Data
public class MineSweepingGameData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 数据的id
     */
    private Integer dataId;
    /**
     * 玩家名称
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
     * 扫雷模式
     * 建立多对一关系
     */
    private MineSweepingModelData mineSweepingModelData;

}