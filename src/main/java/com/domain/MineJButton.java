package com.domain;

import javax.swing.*;

public class MineJButton extends JButton {
    public static final int HIDE_SPACE = 0;
    public static final int DISPLAY_SPACE = 1;
    public static final int MINE = 2;
    public static final int EXPLODE = 3;
    private int status;
    private int data;
    private boolean isFlag;
    private Point point;

    public MineJButton() {
        super();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
