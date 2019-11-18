package com.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 封装扫雷模式数据
 * 写入数据库中
 */
@Data
public class MineSweepingModelData implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 扫雷游戏数据
     * 建立一对多关系
     */
    private final Set<MineSweepingGameData> mineSweepingGameDataSet = new HashSet<>();
    /**
     * 模式的id
     */
    private Integer modelId;
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

    /**
     * 判断两个扫雷模式是否相等
     *
     * @param o 判断对象
     * @return 相等返回true，不等返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MineSweepingModelData that = (MineSweepingModelData) o;
        return Objects.equals(row, that.row) && Objects.equals(column, that.column) && Objects.equals(mineNumber, that.mineNumber) &&
               Objects.equals(modelName, that.modelName);
    }

    /**
     * 根据行、列、地雷数、模式名称计算出哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column, mineNumber, modelName);
    }
}