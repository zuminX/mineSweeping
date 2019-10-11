package com.domain;

public class GameOverDialogData {
    private String nowGameTime;
    private String winsNumber;
    private String averageWinPercentage;
    private String failNumber;
    private String averageTimePercentage;
    private String allGameNumber;
    private String shortestTime;
    private boolean isWin;
    private boolean isBreakRecord;

    public String getNowGameTime() {
        return nowGameTime;
    }

    public void setNowGameTime(String nowGameTime) {
        this.nowGameTime = nowGameTime;
    }

    public String getWinsNumber() {
        return winsNumber;
    }

    public void setWinsNumber(String winsNumber) {
        this.winsNumber = winsNumber;
    }

    public String getAverageWinPercentage() {
        return averageWinPercentage;
    }

    public void setAverageWinPercentage(String averageWinPercentage) {
        this.averageWinPercentage = averageWinPercentage;
    }

    public String getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(String failNumber) {
        this.failNumber = failNumber;
    }

    public String getAverageTimePercentage() {
        return averageTimePercentage;
    }

    public void setAverageTimePercentage(String averageTimePercentage) {
        this.averageTimePercentage = averageTimePercentage;
    }

    public String getAllGameNumber() {
        return allGameNumber;
    }

    public void setAllGameNumber(String allGameNumber) {
        this.allGameNumber = allGameNumber;
    }

    public String getShortestTime() {
        return shortestTime;
    }

    public void setShortestTime(String shortestTime) {
        this.shortestTime = shortestTime;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isBreakRecord() {
        return isBreakRecord;
    }

    public void setBreakRecord(boolean breakRecord) {
        isBreakRecord = breakRecord;
    }
}
