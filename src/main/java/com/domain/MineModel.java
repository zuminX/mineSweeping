package com.domain;

import java.util.Objects;

public class MineModel {
    private Integer row;
    private Integer column;
    private Integer mineNumber;
    private String  name;

    public MineModel() {
    }

    public MineModel(Integer row, Integer column, Integer mineNumber, String name) {
        this.row = row;
        this.column = column;
        this.mineNumber = mineNumber;
        this.name = name;
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

    public Double getMineDensity() {
        return Double.valueOf(mineNumber) / (row * column);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MineModel model = (MineModel) o;
        return Objects.equals(row, model.row) && Objects.equals(column, model.column) && Objects.equals(mineNumber, model.mineNumber) &&
               Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, mineNumber, name);
    }
}
