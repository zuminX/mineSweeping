package com.domain;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MineSweepingModelData implements Serializable {
    /**
     * modelId
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

    @Transient
    private Set<MineSweepingGameData> mineSweepingGameDataSet = new HashSet<>();

    private static final long serialVersionUID = 1L;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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

    public Set<MineSweepingGameData> getMineSweepingGameDataSet() {
        return mineSweepingGameDataSet;
    }

    public void setMineSweepingGameDataSet(Set<MineSweepingGameData> mineSweepingGameDataSet) {
        this.mineSweepingGameDataSet = mineSweepingGameDataSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MineSweepingModelData that = (MineSweepingModelData) o;
        return row.equals(that.row) && column.equals(that.column) && mineNumber.equals(that.mineNumber) && modelName.equals(that.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, mineNumber, modelName);
    }
}