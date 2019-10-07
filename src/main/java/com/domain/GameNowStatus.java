package com.domain;

public class GameNowStatus {
    private long startTime;
    private long endTime;
    private int openSpace;
    private int mineNumber;
    private int space;
    private boolean isFail;

    public GameNowStatus() {
    }

    public GameNowStatus(int mineNumber, int space) {
        this.mineNumber = mineNumber;
        this.space = space;
    }

    public void setInitStatus() {
        startTime = System.currentTimeMillis();
        isFail = false;
        openSpace = 0;
    }

    public boolean isWin() {
        return space - mineNumber == openSpace;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getOpenSpace() {
        return openSpace;
    }

    public void setOpenSpace(int openSpace) {
        this.openSpace = openSpace;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    public void setMineNumber(int mineNumber) {
        this.mineNumber = mineNumber;
    }
}
