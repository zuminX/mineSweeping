package com.utils;

import com.domain.GameOverDialogData;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏结束后的对话框
 */
public class GameOverDialog extends JDialog {
    /**
     * 当前游戏时间的标签
     */
    private JLabel nowGameTimeLabel;
    /**
     * 胜利次数的标签
     */
    private JLabel winsNumberLabel;
    /**
     * 平均胜率的标签
     */
    private JLabel averageWinPercentageLabel;
    /**
     * 失败次数的标签
     */
    private JLabel failNumberLabel;
    /**
     * 平均用时的标签
     */
    private JLabel averageTimePercentageLabel;
    /**
     * 所有游戏次数的标签
     */
    private JLabel allGameNumberLabel;
    /**
     * 最短用时的标签
     */
    private JLabel shortestTimeLabel;

    /**
     * 初始化对话框
     */
    public GameOverDialog() {
        super(JOptionPane.getRootFrame());

        JLabel label3 = new JLabel();
        nowGameTimeLabel = new JLabel();
        JLabel label7 = new JLabel();
        JLabel label8 = new JLabel();
        winsNumberLabel = new JLabel();
        JLabel label16 = new JLabel();
        averageWinPercentageLabel = new JLabel();
        JLabel label9 = new JLabel();
        failNumberLabel = new JLabel();
        JLabel label17 = new JLabel();
        averageTimePercentageLabel = new JLabel();
        JLabel label11 = new JLabel();
        allGameNumberLabel = new JLabel();
        JLabel label18 = new JLabel();
        shortestTimeLabel = new JLabel();

        var dialog1ContentPane = this.getContentPane();
        dialog1ContentPane.setLayout(new MigLayout("hidemode 3",
                // columns
                "[center]" + "[fill]" + "[fill]" + "[fill]" + "[fill]" + "[120,fill]",
                // rows
                "[34]" + "[]" + "[]" + "[]"));

        //---- label3 ----
        label3.setText("\u672c\u5c40\u7528\u65f6\uff1a");
        label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        dialog1ContentPane.add(label3, "cell 2 0");

        //---- nowGameTimeLabel ----
        nowGameTimeLabel.setText("text");
        nowGameTimeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        dialog1ContentPane.add(nowGameTimeLabel, "cell 3 0");

        //---- label7 ----
        label7.setText("\u5386\u53f2\u8bb0\u5f55\uff1a");
        dialog1ContentPane.add(label7, "cell 0 1");

        //---- label8 ----
        label8.setText("\u80dc\u573a\uff1a");
        dialog1ContentPane.add(label8, "cell 1 1");

        //---- winsNumberLabel ----
        winsNumberLabel.setText("text");
        dialog1ContentPane.add(winsNumberLabel, "cell 2 1");

        //---- label16 ----
        label16.setText("\u5e73\u5747\u80dc\u7387\uff1a");
        dialog1ContentPane.add(label16, "cell 4 1");

        //---- averageWinPercentageLabel ----
        averageWinPercentageLabel.setText("text");
        dialog1ContentPane.add(averageWinPercentageLabel, "cell 5 1");

        //---- label9 ----
        label9.setText("\u8d1f\u573a\uff1a");
        dialog1ContentPane.add(label9, "cell 1 2");

        //---- failNumberLabel ----
        failNumberLabel.setText("text");
        dialog1ContentPane.add(failNumberLabel, "cell 2 2");

        //---- label17 ----
        label17.setText("\u5e73\u5747\u7528\u65f6\uff1a");
        dialog1ContentPane.add(label17, "cell 4 2");

        //---- averageTimePercentageLabel ----
        averageTimePercentageLabel.setText("text");
        dialog1ContentPane.add(averageTimePercentageLabel, "cell 5 2");

        //---- label11 ----
        label11.setText("\u603b\u5c40\u6570\uff1a");
        dialog1ContentPane.add(label11, "cell 1 3");

        //---- allGameNumberLabel ----
        allGameNumberLabel.setText("text");
        dialog1ContentPane.add(allGameNumberLabel, "cell 2 3");

        //---- label18 ----
        label18.setText("\u6700\u77ed\u7528\u65f6\uff1a");
        dialog1ContentPane.add(label18, "cell 4 3");

        //---- shortestTimeLabel ----
        shortestTimeLabel.setText("text");
        dialog1ContentPane.add(shortestTimeLabel, "cell 5 3");
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
    }

    /**
     * 初始化对话框数据
     *
     * @param data 对话框数据
     */
    public void initShowData(@NotNull GameOverDialogData data) {
        nowGameTimeLabel.setText(data.getNowGameTime());
        winsNumberLabel.setText(data.getWinsNumber());
        failNumberLabel.setText(data.getFailNumber());
        allGameNumberLabel.setText(data.getAllGameNumber());
        averageWinPercentageLabel.setText(data.getAverageWinPercentage());
        averageTimePercentageLabel.setText(data.getAverageTime());
        shortestTimeLabel.setText(data.getShortestTime());
    }

}
