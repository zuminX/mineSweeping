package com.domain;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component("viewComponent")
public class ViewComponent {
    private MineJButton nowClickButton;
    private JPanel mainPanel;
    private JLabel remainingMineNumberLabel;
    private JLabel expressionLabel;
    private JLabel timeLabel;
    private JPanel buttonsPanel;
    private JRadioButtonMenuItem easyModelButton;
    private JTextField rowTextField;
    private JRadioButtonMenuItem ordinaryModelButton;
    private JTextField columnTextField;
    private JRadioButtonMenuItem hardModelButton;
    private JTextField mineNumberTextField;
    private JRadioButtonMenuItem customizeModelButton;
    private JTextField mineDensityTextField;
    private JTextField gameNameField;
    private JCheckBox openRecordCheckBox;
    private JLabel easyModelBestPlayerNameLabel;
    private JLabel easyModelBestTimeLabel;
    private JLabel ordinaryModelBestPlayerNameLabel;
    private JLabel ordinaryModelBestTimeLabel;
    private JLabel hardModelBestPlayerNameLabel;
    private JLabel hardModelBestTimeLabel;
    private JLabel customizeModelBestPlayerNameLabel;
    private JLabel customizeModelBestTimeLabel;

    public MineJButton getNowClickButton() {
        return nowClickButton;
    }

    public void setNowClickButton(MineJButton nowClickButton) {
        this.nowClickButton = nowClickButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JLabel getRemainingMineNumberLabel() {
        return remainingMineNumberLabel;
    }

    public void setRemainingMineNumberLabel(JLabel remainingMineNumberLabel) {
        this.remainingMineNumberLabel = remainingMineNumberLabel;
    }

    public JLabel getExpressionLabel() {
        return expressionLabel;
    }

    public void setExpressionLabel(JLabel expressionLabel) {
        this.expressionLabel = expressionLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public void setButtonsPanel(JPanel buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public JRadioButtonMenuItem getEasyModelButton() {
        return easyModelButton;
    }

    public void setEasyModelButton(JRadioButtonMenuItem easyModelButton) {
        this.easyModelButton = easyModelButton;
    }

    public JTextField getRowTextField() {
        return rowTextField;
    }

    public void setRowTextField(JTextField rowTextField) {
        this.rowTextField = rowTextField;
    }

    public JRadioButtonMenuItem getOrdinaryModelButton() {
        return ordinaryModelButton;
    }

    public void setOrdinaryModelButton(JRadioButtonMenuItem ordinaryModelButton) {
        this.ordinaryModelButton = ordinaryModelButton;
    }

    public JTextField getColumnTextField() {
        return columnTextField;
    }

    public void setColumnTextField(JTextField columnTextField) {
        this.columnTextField = columnTextField;
    }

    public JRadioButtonMenuItem getHardModelButton() {
        return hardModelButton;
    }

    public void setHardModelButton(JRadioButtonMenuItem hardModelButton) {
        this.hardModelButton = hardModelButton;
    }

    public JTextField getMineNumberTextField() {
        return mineNumberTextField;
    }

    public void setMineNumberTextField(JTextField mineNumberTextField) {
        this.mineNumberTextField = mineNumberTextField;
    }

    public JRadioButtonMenuItem getCustomizeModelButton() {
        return customizeModelButton;
    }

    public void setCustomizeModelButton(JRadioButtonMenuItem customizeModelButton) {
        this.customizeModelButton = customizeModelButton;
    }

    public JTextField getMineDensityTextField() {
        return mineDensityTextField;
    }

    public void setMineDensityTextField(JTextField mineDensityTextField) {
        this.mineDensityTextField = mineDensityTextField;
    }

    public JTextField getGameNameField() {
        return gameNameField;
    }

    public void setGameNameField(JTextField gameNameField) {
        this.gameNameField = gameNameField;
    }

    public JCheckBox getOpenRecordCheckBox() {
        return openRecordCheckBox;
    }

    public void setOpenRecordCheckBox(JCheckBox openRecordCheckBox) {
        this.openRecordCheckBox = openRecordCheckBox;
    }

    public JLabel getEasyModelBestPlayerNameLabel() {
        return easyModelBestPlayerNameLabel;
    }

    public void setEasyModelBestPlayerNameLabel(JLabel easyModelBestPlayerNameLabel) {
        this.easyModelBestPlayerNameLabel = easyModelBestPlayerNameLabel;
    }

    public JLabel getEasyModelBestTimeLabel() {
        return easyModelBestTimeLabel;
    }

    public void setEasyModelBestTimeLabel(JLabel easyModelBestTimeLabel) {
        this.easyModelBestTimeLabel = easyModelBestTimeLabel;
    }

    public JLabel getOrdinaryModelBestPlayerNameLabel() {
        return ordinaryModelBestPlayerNameLabel;
    }

    public void setOrdinaryModelBestPlayerNameLabel(JLabel ordinaryModelBestPlayerNameLabel) {
        this.ordinaryModelBestPlayerNameLabel = ordinaryModelBestPlayerNameLabel;
    }

    public JLabel getOrdinaryModelBestTimeLabel() {
        return ordinaryModelBestTimeLabel;
    }

    public void setOrdinaryModelBestTimeLabel(JLabel ordinaryModelBestTimeLabel) {
        this.ordinaryModelBestTimeLabel = ordinaryModelBestTimeLabel;
    }

    public JLabel getHardModelBestPlayerNameLabel() {
        return hardModelBestPlayerNameLabel;
    }

    public void setHardModelBestPlayerNameLabel(JLabel hardModelBestPlayerNameLabel) {
        this.hardModelBestPlayerNameLabel = hardModelBestPlayerNameLabel;
    }

    public JLabel getHardModelBestTimeLabel() {
        return hardModelBestTimeLabel;
    }

    public void setHardModelBestTimeLabel(JLabel hardModelBestTimeLabel) {
        this.hardModelBestTimeLabel = hardModelBestTimeLabel;
    }

    public JLabel getCustomizeModelBestPlayerNameLabel() {
        return customizeModelBestPlayerNameLabel;
    }

    public void setCustomizeModelBestPlayerNameLabel(JLabel customizeModelBestPlayerNameLabel) {
        this.customizeModelBestPlayerNameLabel = customizeModelBestPlayerNameLabel;
    }

    public JLabel getCustomizeModelBestTimeLabel() {
        return customizeModelBestTimeLabel;
    }

    public void setCustomizeModelBestTimeLabel(JLabel customizeModelBestTimeLabel) {
        this.customizeModelBestTimeLabel = customizeModelBestTimeLabel;
    }
}
