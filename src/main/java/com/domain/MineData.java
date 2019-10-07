package com.domain;

import java.util.Set;

public class MineData {
    private Set<Point> minePoint;
    private int[][] data;

    public MineData() {
    }

    public MineData(Set<Point> minePoint, int[][] data) {
        this.minePoint = minePoint;
        this.data = data;
    }

    public Set<Point> getMinePoint() {
        return minePoint;
    }

    public void setMinePoint(Set<Point> minePoint) {
        this.minePoint = minePoint;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }
}
