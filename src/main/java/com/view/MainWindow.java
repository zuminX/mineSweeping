/*
 * Created by JFormDesigner on Wed Sep 18 09:16:27 CST 2019
 */

package com.view;

import com.domain.*;
import com.jgoodies.forms.factories.Borders;
import com.controller.MineController;
import com.utils.BaseHolder;
import com.utils.ComponentImage;
import com.utils.Information;
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

    @Autowired
    private GameNowData gameNowData;

    @Autowired
    private ViewComponent viewComponent;

    /**
     * 初始化游戏界面
     */
    public MainWindow() {
        initComponents();

        ViewComponent viewComponent = BaseHolder.getBean("viewComponent", ViewComponent.class);

        viewComponent.setMainPanel(mainPanel);
        viewComponent.setRemainingMineNumberLabel(remainingMineNumberLabel);
        viewComponent.setExpressionLabel(expressionLabel);
        viewComponent.setTimeLabel(timeLabel);
        viewComponent.setButtonsPanel(buttonsPanel);
        viewComponent.setEasyModelRadioButtonMenuItem(easyModelButton);
        viewComponent.setRowTextField(rowTextField);
        viewComponent.setOrdinaryModelRadioButtonMenuItem(ordinaryModelButton);
        viewComponent.setColumnTextField(columnTextField);
        viewComponent.setHardModelRadioButtonMenuItem(hardModelButton);
        viewComponent.setMineNumberTextField(mineNumberTextField);
        viewComponent.setCustomizeModelRadioButtonMenuItem(customizeModelButton);
        viewComponent.setMineDensityTextField(mineDensityTextField);
        viewComponent.setGameNameField(gameNameField);
        viewComponent.setOpenRecordCheckBox(openRecordCheckBox);
        viewComponent.setEasyModelBestPlayerNameLabel(easyModelBestPlayerNameLabel);
        viewComponent.setEasyModelBestTimeLabel(easyModelBestTimeLabel);
        viewComponent.setOrdinaryModelBestPlayerNameLabel(ordinaryModelBestPlayerNameLabel);
        viewComponent.setOrdinaryModelBestTimeLabel(ordinaryModelBestTimeLabel);
        viewComponent.setHardModelBestPlayerNameLabel(hardModelBestPlayerNameLabel);
        viewComponent.setHardModelBestTimeLabel(hardModelBestTimeLabel);
        viewComponent.setCustomizeModelBestPlayerNameLabel(customizeModelBestPlayerNameLabel);
        viewComponent.setCustomizeModelBestTimeLabel(customizeModelBestTimeLabel);

        label3.setText(Information.helpGame1);
        label7.setText(Information.helpGame2);
        label8.setText(Information.helpGame3);
        label9.setText(Information.helpGame4);
        label11.setText(Information.helpSet1);
        label12.setText(Information.helpSet2);
        label13.setText(Information.helpSet3);
        label14.setText(Information.helpSet4);

        expressionLabel.setIcon(ComponentImage.getGameImageIcon(ComponentImage.confusedBufferedImage, expressionLabel.getPreferredSize()));
        //开启间隔1s的定时任务
        new Timer(1000, new TimerListener()).start();
    }

    /**
     * 显示游戏的界面
     */
    private void showGameActionPerformed() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card3");

        //预加载数据
        mineController.preLoadData();
    }

    /**
     * 显示设置的界面
     */
    private void changeSettingActionPerformed() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card2");
        //设置显示默认的模式和设置
        mineController.setDefaultModel();
        mineController.showNowOtherSetting();
    }

    /**
     * 显示帮助的页面
     */
    private void showHelpActionPerformed() {
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card4");
    }

    /**
     * 改变扫雷模式
     * @param e 事件源
     */
    private void changeModelActionPerformed(ActionEvent e) {
        mineController.changeModel(((JRadioButtonMenuItem) e.getSource()).getText());
    }

    /**
     * 重新创建一局游戏
     */
    private void reloadLabelMouseClicked() {
        //若还未加载数据，则无法进行重新创建新的游戏
        if (mineController.reloadGameData(gameNowData)) {
            return;
        }
        //清理游戏数据
        cleanGameData();
    }

    /**
     * 加载扫雷游戏数据
     */
    private void loadActionPerformed() {
        //加载数据
        //扫雷面板初始化
        final MineModel nowMineModel = mineController.getNowMineModel();
        buttonsPanel.removeAll();
        buttonsPanel.setLayout(new GridLayout(nowMineModel.getRow(), nowMineModel.getColumn()));

        //创建扫雷按钮组并添加到面板中
        mineController.newMineViewButtons();
        mineController.addButtonsToPanel(gameNowData.getButtons());

        //绘制扫雷按钮组面板
        buttonsPanel.updateUI();
        buttonsPanel.repaint();

        //设置窗口合适大小
        mineController.setWindowSize(this, Toolkit.getDefaultToolkit().getScreenSize());

        //设置按钮为默认的图片
        mineController.setMineJButtonDefaultIcon();

        //将窗口定位到屏幕中心
        setLocationRelativeTo(null);

        //清理游戏数据
        cleanGameData();
    }

    /**
     * 清理游戏数据
     */
    private void cleanGameData() {
        //预加载数据
        mineController.preLoadData();

        //移除按钮监听器
        mineController.removeButtonsListener();

        //动态显示经过的时间
        mineController.showDynamicTime();

        //初始化剩余的地雷数
        mineController.initRemainingMineNumberLabel();

        //初始化游戏当前数据
        gameNowData.setInitStatus();

        //添加按钮监听器
        mineController.addButtonsMouseListener();

        //设置默认的表情
        mineController.setDefaultExpression();

        //改变按钮的大小
        mineController.changeButtonsIconSize();

        //预加载数据
        mineController.preLoadData();
    }

    /**
     * 保存设置的数据
     */
    private void saveButtonActionPerformed() {
        mineController.saveSettingData();
    }

    /**
     * 显示排行榜
     */
    private void showLeaderboardActionPerformed() {
        mineController.showLeaderboard();
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "card5");
    }

    /**
     * 按钮监听器
     */
    @Component("mouseListener")
    public class ButtonMouseProcessor extends MouseAdapter {

        /**
         * 监听按钮点击事件
         * @param e 事件源
         */
        @Override
        public void mouseClicked(MouseEvent e) {
                //设置当前的点击的按钮
                viewComponent.setNowClickButton((MineJButton) e.getSource());

                //左键为打开区块
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    mineController.openSpace(gameNowData);
                }
                //右键为设置旗帜
                else if(e.getButton() == MouseEvent.BUTTON3)
                {
                    mineController.setFlag();
                }
        }
    }

    /**
     * 定时任务监听器
     */
    @Component
    private class TimerListener implements ActionListener {

        /**
         * 初始按钮大小
         */
        @Autowired
        private Dimension dimension;

        /**
         * 监听按钮大小变化
         *
         * @param e 事件
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            final MineJButton[][] buttons = gameNowData.getButtons();
            //若按钮组不为空且按钮的大小与初始化小不符
            if (buttons != null && !buttons[0][0].getSize().equals(dimension)) {
                //刷新按钮图片
                mineController.changeButtonsIconSize();
                dimension = buttons[0][0].getSize();
            }
        }
    }

    /**
     * 初始化组件
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
        menuItem5 = new JMenuItem();
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
        leaderboardPanel2 = new JPanel();
        panel1 = new JPanel();
        easyModelBestPlayerNameLabel = new JLabel();
        easyModelBestTimeLabel = new JLabel();
        panel2 = new JPanel();
        ordinaryModelBestPlayerNameLabel = new JLabel();
        ordinaryModelBestTimeLabel = new JLabel();
        panel4 = new JPanel();
        hardModelBestPlayerNameLabel = new JLabel();
        hardModelBestTimeLabel = new JLabel();
        panel5 = new JPanel();
        customizeModelBestPlayerNameLabel = new JLabel();
        customizeModelBestTimeLabel = new JLabel();

        //======== this ========
        setTitle("MineSweeping");
        setIconImage(new ImageIcon(getClass().getResource("/image/mine.png")).getImage());
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
                menuItem1.addActionListener(e -> showGameActionPerformed());
                menu1.add(menuItem1);
                menu1.addSeparator();

                //---- menuItem2 ----
                menuItem2.setText(" \u8bbe\u7f6e");
                menuItem2.addActionListener(e -> changeSettingActionPerformed());
                menu1.add(menuItem2);
                menu1.addSeparator();

                //---- menuItem4 ----
                menuItem4.setText(" \u52a0\u8f7d");
                menuItem4.addActionListener(e -> loadActionPerformed());
                menu1.add(menuItem4);
            }
            menuBar1.add(menu1);

            //======== menu2 ========
            {
                menu2.setText(" \u67e5\u770b");
                menu2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
                menu2.setMaximumSize(new Dimension(55, 32767));
                menu2.setMinimumSize(new Dimension(50, 27));
                menu2.setPreferredSize(new Dimension(53, 27));

                //---- menuItem3 ----
                menuItem3.setText(" \u5e2e\u52a9\u4fe1\u606f");
                menuItem3.addActionListener(e -> showHelpActionPerformed());
                menu2.add(menuItem3);

                //---- menuItem5 ----
                menuItem5.setText("  \u6392\u884c\u699c");
                menuItem5.setHorizontalAlignment(SwingConstants.LEFT);
                menuItem5.addActionListener(e -> showLeaderboardActionPerformed());
                menu2.add(menuItem5);
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
                    remainingMineNumberLabel.setText("0");
                    remainingMineNumberLabel.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
                    remainingMineNumberLabel.setForeground(Color.red);
                    remainingMineNumberLabel.setOpaque(true);
                    remainingMineNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    remainingMineNumberLabel.setMaximumSize(new Dimension(35, 30));
                    remainingMineNumberLabel.setMinimumSize(new Dimension(35, 30));
                    remainingMineNumberLabel.setPreferredSize(new Dimension(35, 30));
                    remainingMineNumberLabel.setToolTipText("\u5269\u4f59\u5730\u96f7\u6570");

                    //---- expressionLabel ----
                    expressionLabel.setText("text");
                    expressionLabel.setMaximumSize(new Dimension(30, 30));
                    expressionLabel.setMinimumSize(new Dimension(30, 30));
                    expressionLabel.setPreferredSize(new Dimension(30, 30));
                    expressionLabel.setIcon(new ImageIcon(getClass().getResource("/image/confused.png")));
                    expressionLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
                    expressionLabel.setToolTipText("\u91cd\u65b0\u5f00\u5c40");
                    expressionLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            reloadLabelMouseClicked();
                        }
                    });

                    //---- timeLabel ----
                    timeLabel.setText("0");
                    timeLabel.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
                    timeLabel.setBorder(LineBorder.createBlackLineBorder());
                    timeLabel.setForeground(Color.red);
                    timeLabel.setOpaque(true);
                    timeLabel.setBackground(Color.black);
                    timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    timeLabel.setMaximumSize(new Dimension(35, 30));
                    timeLabel.setMinimumSize(new Dimension(35, 30));
                    timeLabel.setPreferredSize(new Dimension(35, 30));
                    timeLabel.setToolTipText("\u5f53\u524d\u6240\u8fc7\u65f6\u95f4");

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(remainingMineNumberLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(expressionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(timeLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40))
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
                ((GridBagLayout)settingPanel.getLayout()).rowHeights = new int[] {73, 0, 0};
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
                    saveButton.addActionListener(e -> saveButtonActionPerformed());
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

            //======== leaderboardPanel2 ========
            {
                leaderboardPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)leaderboardPanel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)leaderboardPanel2.getLayout()).rowHeights = new int[] {55, 55, 55, 50, 0};
                ((GridBagLayout)leaderboardPanel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)leaderboardPanel2.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0E-4};

                //======== panel1 ========
                {
                    panel1.setBorder(new TitledBorder(null, "\u7b80\u5355", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
                        new Font("Microsoft YaHei UI", Font.PLAIN, 12)));
                    panel1.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 5, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- easyModelBestPlayerNameLabel ----
                    easyModelBestPlayerNameLabel.setText("text");
                    easyModelBestPlayerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    easyModelBestPlayerNameLabel.setToolTipText("\u7b80\u5355\u6a21\u5f0f\u7528\u65f6\u6700\u77ed\u7684\u73a9\u5bb6\u540d");
                    panel1.add(easyModelBestPlayerNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));

                    //---- easyModelBestTimeLabel ----
                    easyModelBestTimeLabel.setText("text");
                    easyModelBestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    easyModelBestTimeLabel.setToolTipText("\u7b80\u5355\u6a21\u5f0f\u7528\u65f6\u6700\u77ed\u7684\u73a9\u5bb6\u540d");
                    panel1.add(easyModelBestTimeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                leaderboardPanel2.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel2 ========
                {
                    panel2.setBorder(new TitledBorder(null, "\u666e\u901a", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
                    panel2.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- ordinaryModelBestPlayerNameLabel ----
                    ordinaryModelBestPlayerNameLabel.setText("text");
                    ordinaryModelBestPlayerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    ordinaryModelBestPlayerNameLabel.setToolTipText("\u666e\u901a\u6a21\u5f0f\u7528\u65f6\u6700\u77ed\u7684\u73a9\u5bb6\u540d");
                    panel2.add(ordinaryModelBestPlayerNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- ordinaryModelBestTimeLabel ----
                    ordinaryModelBestTimeLabel.setText("text");
                    ordinaryModelBestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    ordinaryModelBestTimeLabel.setToolTipText("\u666e\u901a\u6a21\u5f0f\u7684\u6700\u77ed\u7528\u65f6");
                    panel2.add(ordinaryModelBestTimeLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                leaderboardPanel2.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel4 ========
                {
                    panel4.setBorder(new TitledBorder(null, "\u56f0\u96be", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
                    panel4.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- hardModelBestPlayerNameLabel ----
                    hardModelBestPlayerNameLabel.setText("text");
                    hardModelBestPlayerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    hardModelBestPlayerNameLabel.setToolTipText("\u56f0\u96be\u6a21\u5f0f\u7528\u65f6\u6700\u77ed\u7684\u73a9\u5bb6\u540d");
                    panel4.add(hardModelBestPlayerNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- hardModelBestTimeLabel ----
                    hardModelBestTimeLabel.setText("text");
                    hardModelBestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    hardModelBestTimeLabel.setToolTipText("\u56f0\u96be\u6a21\u5f0f\u7684\u6700\u77ed\u7528\u65f6");
                    panel4.add(hardModelBestTimeLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                leaderboardPanel2.add(panel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel5 ========
                {
                    panel5.setBorder(new TitledBorder(null, "\u81ea\u5b9a\u4e49", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
                    panel5.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- customizeModelBestPlayerNameLabel ----
                    customizeModelBestPlayerNameLabel.setText("text");
                    customizeModelBestPlayerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    customizeModelBestPlayerNameLabel.setToolTipText("\u81ea\u5b9a\u4e49\u6a21\u5f0f\u7528\u65f6\u6700\u77ed\u7684\u73a9\u5bb6\u540d");
                    panel5.add(customizeModelBestPlayerNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                    //---- customizeModelBestTimeLabel ----
                    customizeModelBestTimeLabel.setText("text");
                    customizeModelBestTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    customizeModelBestTimeLabel.setToolTipText("\u81ea\u5b9a\u4e49\u6a21\u5f0f\u7684\u6700\u77ed\u7528\u65f6");
                    panel5.add(customizeModelBestTimeLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                leaderboardPanel2.add(panel5, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            mainPanel.add(leaderboardPanel2, "card5");
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
    private JMenuItem menuItem5;
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
    private JPanel leaderboardPanel2;
    private JPanel panel1;
    private JLabel easyModelBestPlayerNameLabel;
    private JLabel easyModelBestTimeLabel;
    private JPanel panel2;
    private JLabel ordinaryModelBestPlayerNameLabel;
    private JLabel ordinaryModelBestTimeLabel;
    private JPanel panel4;
    private JLabel hardModelBestPlayerNameLabel;
    private JLabel hardModelBestTimeLabel;
    private JPanel panel5;
    private JLabel customizeModelBestPlayerNameLabel;
    private JLabel customizeModelBestTimeLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
