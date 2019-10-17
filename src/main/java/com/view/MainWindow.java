/*
 * Created by JFormDesigner on Wed Sep 18 09:16:27 CST 2019
 */

package com.view;

import com.domain.*;
import com.jgoodies.forms.factories.Borders;
import com.controller.MineController;
import com.utils.BaseHolder;
import com.utils.Information;
import net.miginfocom.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 视图层，进行程序的显示和与用户交互的界面
 * 将数据传递给controller层实现指定方法
 * 获取controller层返回的数据并进行操作
 *
 * @author zumin
 */
@Component("viewWindow")
public class MainWindow extends JFrame {
    @Autowired
    private MineController mineController;

    private GameNowData gameNowData;

    @Autowired
    private ViewComponent viewComponent;

    public MainWindow() {
        initComponents();

        ViewComponent viewComponent = BaseHolder.getBean("viewComponent", ViewComponent.class);
        viewComponent.setMainPanel(mainPanel);
        viewComponent.setRemainingMineNumberLabel(remainingMineNumberLabel);
        viewComponent.setExpressionLabel(expressionLabel);
        viewComponent.setTimeLabel(timeLabel);
        viewComponent.setButtonsPanel(buttonsPanel);
        viewComponent.setEasyModelButton(easyModelButton);
        viewComponent.setRowTextField(rowTextField);
        viewComponent.setOrdinaryModelButton(ordinaryModelButton);
        viewComponent.setColumnTextField(columnTextField);
        viewComponent.setHardModelButton(hardModelButton);
        viewComponent.setMineNumberTextField(mineNumberTextField);
        viewComponent.setCustomizeModelButton(customizeModelButton);
        viewComponent.setMineDensityTextField(mineDensityTextField);
        viewComponent.setGameNameField(gameNameField);
        viewComponent.setOpenRecordCheckBox(openRecordCheckBox);
    }

    private void showGameActionPerformed(ActionEvent e) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card3");
    }


    private void changeSettingActionPerformed(ActionEvent e) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card2");
        mineController.setDefaultModel();
        mineController.showNowOtherSetting();
    }

    private void showHelpActionPerformed(ActionEvent e) {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card4");
    }

    private void changeModelActionPerformed(ActionEvent e) {
        mineController.changeModel(((JRadioButtonMenuItem) e.getSource()).getText());
    }

    private void reloadLabelMouseClicked(MouseEvent e) {
        if (mineController.reloadGameData(gameNowData)) {
            return;
        }
        cleanGameData();
    }

    private void loadActionPerformed(ActionEvent e) {
        // 加载数据
        //扫雷面板初始化
        final MineModel nowMineModel = mineController.getNowMineModel();

        buttonsPanel.removeAll();
        buttonsPanel.setLayout(new GridLayout(nowMineModel.getRow(), nowMineModel.getColumn()));

        //创建扫雷按钮组并增加监听器添加到面板中
        gameNowData = mineController.newMineViewButtons();
        mineController.addButtonsToPanel(gameNowData.getButtons());

        //绘制扫雷按钮组面板
        buttonsPanel.updateUI();
        buttonsPanel.repaint();

        //设置窗口合适大小
        mineController.setWindowSize(this, Toolkit.getDefaultToolkit().getScreenSize());

        //将窗口定位到屏幕中心
        setLocationRelativeTo(null);

        cleanGameData();
    }

    private void cleanGameData() {
        GameNowStatus nowStatus = gameNowData.getNowStatus();

        mineController.removeButtonsListener(gameNowData.getButtons());

        mineController.showDynamicTime(nowStatus);
        mineController.initRemainingMineNumberLabel();
        nowStatus.setInitStatus();

        mineController.addButtonsMouseListener(gameNowData.getButtons());
    }

    private void saveButtonActionPerformed(ActionEvent e) {
        mineController.saveSettingData();
    }

    @Component("mouseListener")
    public class ButtonMouseProcessor extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            viewComponent.setNowClickButton((MineJButton) e.getSource());
            //左键为打开区块
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                if (mineController.openSpace(gameNowData)) {
                    mineController.removeButtonsListener(gameNowData.getButtons());
                }
            }
            //右键为设置旗帜
            else if(e.getButton() == MouseEvent.BUTTON3)
            {
                mineController.setFlag();
            }
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
        mainPanel = new JPanel();
        gamePanel = new JPanel();
        panel3 = new JPanel();
        remainingMineNumberLabel = new JLabel();
        expressionLabel = new JLabel();
        timeLabel = new JLabel();
        buttonsPanel = new JPanel();
        settingPanel = new JPanel();
        panel6 = new JPanel();
        easyModelButton = new JRadioButtonMenuItem();
        label4 = new JLabel();
        rowTextField = new JTextField();
        ordinaryModelButton = new JRadioButtonMenuItem();
        label5 = new JLabel();
        columnTextField = new JTextField();
        hardModelButton = new JRadioButtonMenuItem();
        label6 = new JLabel();
        mineNumberTextField = new JTextField();
        customizeModelButton = new JRadioButtonMenuItem();
        label10 = new JLabel();
        mineDensityTextField = new JTextField();
        panel7 = new JPanel();
        label1 = new JLabel();
        gameNameField = new JTextField();
        label2 = new JLabel();
        openRecordCheckBox = new JCheckBox();
        saveButton = new JButton();
        helpPanel = new JPanel();
        panel8 = new JPanel();
        label3 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        panel9 = new JPanel();
        label11 = new JLabel();
        label12 = new JLabel();
        label13 = new JLabel();
        label14 = new JLabel();

        //======== this ========
        setTitle("MineSweeping");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText(" \u6e38\u620f");
                menu1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
                menu1.setMaximumSize(new Dimension(55, 32767));
                menu1.setMinimumSize(new Dimension(50, 27));
                menu1.setPreferredSize(new Dimension(53, 27));

                //---- menuItem1 ----
                menuItem1.setText(" \u5f00\u59cb");
                menuItem1.setHorizontalAlignment(SwingConstants.LEFT);
                menuItem1.addActionListener(e -> showGameActionPerformed(e));
                menu1.add(menuItem1);
                menu1.addSeparator();

                //---- menuItem2 ----
                menuItem2.setText(" \u8bbe\u7f6e");
                menuItem2.addActionListener(e -> changeSettingActionPerformed(e));
                menu1.add(menuItem2);
                menu1.addSeparator();

                //---- menuItem4 ----
                menuItem4.setText(" \u52a0\u8f7d");
                menuItem4.addActionListener(e -> loadActionPerformed(e));
                menu1.add(menuItem4);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText(" \u5e2e\u52a9");
                menu2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
                menu2.setMaximumSize(new Dimension(55, 32767));
                menu2.setMinimumSize(new Dimension(50, 27));
                menu2.setPreferredSize(new Dimension(53, 27));

                //---- menuItem3 ----
                menuItem3.setText("  \u67e5\u770b");
                menuItem3.addActionListener(e -> showHelpActionPerformed(e));
                menu2.add(menuItem3);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);

        //======== mainPanel ========
        {
            mainPanel.setLayout(new CardLayout());

            //======== gamePanel ========
            {
                gamePanel.setBackground(new Color(204, 204, 204));
                gamePanel.setLayout(new BorderLayout());

                //======== panel3 ========
                {
                    panel3.setBackground(new Color(204, 204, 204));
                    panel3.setBorder(new BevelBorder(BevelBorder.LOWERED));

                    //---- remainingMineNumberLabel ----
                    remainingMineNumberLabel.setBorder(LineBorder.createBlackLineBorder());
                    remainingMineNumberLabel.setBackground(Color.black);
                    remainingMineNumberLabel.setText("000");
                    remainingMineNumberLabel.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
                    remainingMineNumberLabel.setForeground(Color.red);
                    remainingMineNumberLabel.setOpaque(true);
                    remainingMineNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- expressionLabel ----
                    expressionLabel.setText("text");
                    expressionLabel.setMaximumSize(new Dimension(25, 25));
                    expressionLabel.setMinimumSize(new Dimension(25, 25));
                    expressionLabel.setPreferredSize(new Dimension(25, 25));
                    expressionLabel.setIcon(new ImageIcon(getClass().getResource("/image/confused.png")));
                    expressionLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
                    expressionLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            reloadLabelMouseClicked(e);
                        }
                    });

                    //---- timeLabel ----
                    timeLabel.setText("000");
                    timeLabel.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
                    timeLabel.setBorder(LineBorder.createBlackLineBorder());
                    timeLabel.setForeground(Color.red);
                    timeLabel.setOpaque(true);
                    timeLabel.setBackground(Color.black);
                    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addContainerGap(36, Short.MAX_VALUE)
                                .addComponent(remainingMineNumberLabel)
                                .addGap(112, 112, 112)
                                .addComponent(expressionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                                .addComponent(timeLabel)
                                .addGap(33, 33, 33))
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(remainingMineNumberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(timeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(expressionLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                    );
                }
                gamePanel.add(panel3, BorderLayout.NORTH);

                //======== buttonsPanel ========
                {
                    buttonsPanel.setBorder(Borders.TABBED_DIALOG_BORDER);
                    buttonsPanel.setLayout(new GridLayout());
                }
                gamePanel.add(buttonsPanel, BorderLayout.CENTER);
            }
            mainPanel.add(gamePanel, "card3");

            //======== settingPanel ========
            {
                settingPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)settingPanel.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)settingPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)settingPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)settingPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //======== panel6 ========
                {
                    panel6.setBorder(new CompoundBorder(
                        new TitledBorder("\u6e38\u620f\u8bbe\u7f6e"),
                        Borders.DLU2_BORDER));
                    panel6.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {95, 68, 0, 0};
                    ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                    ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- easyModelButton ----
                    easyModelButton.setText("\u7b80\u5355");
                    easyModelButton.addActionListener(e -> changeModelActionPerformed(e));
                    panel6.add(easyModelButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- label4 ----
                    label4.setText("\u884c\u6570\uff1a");
                    panel6.add(label4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- rowTextField ----
                    rowTextField.setText("text");
                    rowTextField.setEnabled(false);
                    panel6.add(rowTextField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- ordinaryModelButton ----
                    ordinaryModelButton.setText("\u666e\u901a");
                    ordinaryModelButton.addActionListener(e -> changeModelActionPerformed(e));
                    panel6.add(ordinaryModelButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- label5 ----
                    label5.setText("\u5217\u6570\uff1a");
                    panel6.add(label5, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- columnTextField ----
                    columnTextField.setText("text");
                    columnTextField.setEnabled(false);
                    panel6.add(columnTextField, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- hardModelButton ----
                    hardModelButton.setText("\u56f0\u96be");
                    hardModelButton.addActionListener(e -> changeModelActionPerformed(e));
                    panel6.add(hardModelButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- label6 ----
                    label6.setText("\u5730\u96f7\u4e2a\u6570\uff1a");
                    panel6.add(label6, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                        new Insets(0, 0, 5, 5), 0, 0));

                    //---- mineNumberTextField ----
                    mineNumberTextField.setText("text");
                    mineNumberTextField.setEnabled(false);
                    panel6.add(mineNumberTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- customizeModelButton ----
                    customizeModelButton.setText("\u81ea\u5b9a\u4e49");
                    customizeModelButton.addActionListener(e -> changeModelActionPerformed(e));
                    panel6.add(customizeModelButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- label10 ----
                    label10.setText("\u5730\u96f7\u5bc6\u5ea6\uff1a");
                    panel6.add(label10, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- mineDensityTextField ----
                    mineDensityTextField.setText("text");
                    mineDensityTextField.setEnabled(false);
                    panel6.add(mineDensityTextField, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                settingPanel.add(panel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel7 ========
                {
                    panel7.setBorder(new CompoundBorder(
                        new TitledBorder("\u5176\u4ed6\u8bbe\u7f6e"),
                        Borders.DLU2_BORDER));
                    panel7.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {66, 0, 0};
                    ((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                    ((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                    //---- label1 ----
                    label1.setText("\u6e38\u620f\u540d\uff1a");
                    panel7.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                    panel7.add(gameNameField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label2 ----
                    label2.setText("\u5f00\u542f\u8bb0\u5f55\uff1a");
                    panel7.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                    panel7.add(openRecordCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- saveButton ----
                    saveButton.setText("\u4fdd\u5b58");
                    saveButton.addActionListener(e -> saveButtonActionPerformed(e));
                    panel7.add(saveButton, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
                        GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                settingPanel.add(panel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            mainPanel.add(settingPanel, "card2");

            //======== helpPanel ========
            {
                helpPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)helpPanel.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)helpPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)helpPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)helpPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //======== panel8 ========
                {
                    panel8.setBorder(new CompoundBorder(
                        new TitledBorder("\u6e38\u620f"),
                        Borders.DLU2_BORDER));
                    panel8.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
                    panel8.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel8.getLayout()).columnWidths = new int[] {90, 0};
                    ((GridBagLayout)panel8.getLayout()).rowHeights = new int[] {27, 27, 27, 22, 0};
                    ((GridBagLayout)panel8.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)panel8.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- label3 ----
                    label3.setText("text");
                    panel8.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label7 ----
                    label7.setText("text");
                    panel8.add(label7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label8 ----
                    label8.setText("text");
                    panel8.add(label8, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label9 ----
                    label9.setText("text");
                    panel8.add(label9, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                helpPanel.add(panel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel9 ========
                {
                    panel9.setBorder(new CompoundBorder(
                        new TitledBorder("\u8bbe\u7f6e"),
                        Borders.DLU2_BORDER));
                    panel9.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0};
                    ((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {27, 27, 27, 22, 0};
                    ((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

                    //---- label11 ----
                    label11.setText("text");
                    panel9.add(label11, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label12 ----
                    label12.setText("text");
                    panel9.add(label12, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label13 ----
                    label13.setText("text");
                    panel9.add(label13, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                    //---- label14 ----
                    label14.setText("text");
                    panel9.add(label14, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                helpPanel.add(panel9, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            mainPanel.add(helpPanel, "card4");
        }
        contentPane.add(mainPanel, BorderLayout.CENTER);
        setSize(400, 355);
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(easyModelButton);
        buttonGroup1.add(ordinaryModelButton);
        buttonGroup1.add(hardModelButton);
        buttonGroup1.add(customizeModelButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents


    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem4;
    private JMenu menu2;
    private JMenuItem menuItem3;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel panel3;
    private JLabel remainingMineNumberLabel;
    private JLabel expressionLabel;
    private JLabel timeLabel;
    private JPanel buttonsPanel;
    private JPanel settingPanel;
    private JPanel panel6;
    private JRadioButtonMenuItem easyModelButton;
    private JLabel label4;
    private JTextField rowTextField;
    private JRadioButtonMenuItem ordinaryModelButton;
    private JLabel label5;
    private JTextField columnTextField;
    private JRadioButtonMenuItem hardModelButton;
    private JLabel label6;
    private JTextField mineNumberTextField;
    private JRadioButtonMenuItem customizeModelButton;
    private JLabel label10;
    private JTextField mineDensityTextField;
    private JPanel panel7;
    private JLabel label1;
    private JTextField gameNameField;
    private JLabel label2;
    private JCheckBox openRecordCheckBox;
    private JButton saveButton;
    private JPanel helpPanel;
    private JPanel panel8;
    private JLabel label3;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JPanel panel9;
    private JLabel label11;
    private JLabel label12;
    private JLabel label13;
    private JLabel label14;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
