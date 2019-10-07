package com.domain;

public class GameNowData {
    private MineJButton[][] buttons;
    private GameNowStatus nowStatus;

    public GameNowData() {
    }

    public GameNowData(MineJButton[][] buttons, GameNowStatus nowStatus) {
        this.buttons = buttons;
        this.nowStatus = nowStatus;
    }

    public GameNowStatus getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(GameNowStatus nowStatus) {
        this.nowStatus = nowStatus;
    }

    public MineJButton[][] getButtons() {
        return buttons;
    }

    public void setButtons(MineJButton[][] buttons) {
        this.buttons = buttons;
    }
}
