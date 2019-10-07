import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class test {
    private static class MineSweeping extends JFrame {
        private JButton[][] button = null;	//存放按钮的二维数组
        private int[][] data = null;	//存放地雷数数据的二维数组
        private Font font = new Font("宋体", Font.BOLD, 16);	//按钮的字体
        private Color colorVisible = new Color(0, 0, 0, 255);	//黑色、可见的颜色
        private Color colorInvisible = new Color(0, 0, 0, 0);	//黑色、不可见的颜色
        private Color colorFlag = new Color(0, 0, 255, 255);	//蓝色、可见的颜色
        private JPanel panelMain = new JPanel(new BorderLayout());	//主面板，采用边框布局
        private JPanel panelSet = new JPanel();	//设置面板
        private JPanel panelGame = new JPanel();	//游戏面板
        private JPanel cards = new JPanel(new CardLayout());     //卡片式布局的面板
        private JTextField[] setDataField = null;	//设置游戏数据的文本框
        private JRadioButton[] modelButton = null;	//设置模式的单选按钮
        private int row = 9;	//行数
        private int column = 9;	//列数
        private int mineNumber = 10;	//地雷数

        public MineSweeping() {
            setTitle("扫雷");    //设置窗体的标题
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置窗体退出时操作
            setSize(this.row*50, this.column*50);	//设置窗体大小
            setLocationRelativeTo(null);	//设置窗体在中间

            JToolBar toolBar = new JToolBar();	//工具栏
            addToolButtons(toolBar);
            panelMain.add(toolBar, BorderLayout.NORTH);	//将工具栏添加到主面板

            creatSetPanel(panelSet);	//创建设置面板的内容

            cards.add(panelMain, "card1");	//将主面板命名为"card1"添加到卡片式布局面板中
            cards.add(panelSet, "card2");	//将设置面板命名为"card2"添加到卡片式布局面板中
            ((CardLayout)(cards.getLayout())).show(cards, "card1");	//显示"card1"面板
            add(cards);	//将卡片布局的面板添加到框架中
        }

        //扫雷按钮的监听器
        class MineSweepingButtonActionListener extends MouseAdapter implements ActionListener {

            //监听按钮被右键
            @Override
            public void mouseClicked(MouseEvent me) {
                //若被右键且不为flag颜色，则设置为flag颜色，否则设置为默认颜色
                if(me.getButton() == MouseEvent.BUTTON3) {
                    JButton tempButton = (JButton)me.getSource();
                    if(tempButton.getBackground() != colorFlag) {
                        tempButton.setBackground(colorFlag);
                    }
                    else {
                        tempButton.setBackground(null);
                    }
                }

            }

            //监听按钮被单击
            @Override
            public void actionPerformed(ActionEvent ae) {
                update((JButton)ae.getSource());	//更新数据
            }
        }

        //工具按钮的监听器
        class ToolButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String buttonText = ae.getActionCommand();
                //若为新建，则调用方法新建主面板
                if(buttonText.equals("新建")) {
                    newMineSweeping(panelMain);
                }
                //若为设置，则显示“card2”面板
                else if(buttonText.equals("设置")){
                    ((CardLayout)(cards.getLayout())).show(cards, "card2");
                }
            }
        }

        //导航按钮的监听器
        class NavigationButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String buttonText = ae.getActionCommand();
                //若为返回，则显示“card1”面板
                if(buttonText.equals("返回")) {
                    ((CardLayout)(cards.getLayout())).show(cards, "card1");
                }
                //若为保存且为自定义模式，则判断文本框内容是否合法，若合法则保存给定的数据
                else if(buttonText.equals("保存") && setDataField[0].isEditable()) {
                    try {
                        int tempRow = Integer.parseInt(setDataField[0].getText());
                        int tempColumn = Integer.parseInt(setDataField[1].getText());
                        int tempMineNumber = Integer.parseInt(setDataField[2].getText());
                        //限制数据范围
                        if(tempRow < 9 || tempRow > 24 || tempColumn < 9 || tempColumn > 30 || tempMineNumber <= 0 || tempRow*tempColumn/tempMineNumber < 4) {
                            JOptionPane.showMessageDialog(getContentPane(), "错误，行数限制为9-24，列数限制为9-30，行*列/地雷数<4。", "异常", 0);
                        }
                        else {
                            row = tempRow;
                            column = tempColumn;
                            mineNumber = tempMineNumber;
                            JOptionPane.showMessageDialog(getContentPane(), "设置已保存。", "成功", 1);
                        }
                        //若捕捉到数字格式化的异常，则弹出提示框
                    } catch(NumberFormatException e) {
                        JOptionPane.showMessageDialog(getContentPane(), "输入数据异常！", "异常", 0);
                    }
                }
            }
        }

        //模式按钮的监听器
        class ModelButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String buttonText = ae.getActionCommand();
                //若为自定义，则将文本框设为可编辑
                if(buttonText.equals("自定义")) {
                    for(JTextField textField : setDataField) {
                        textField.setEditable(true);
                    }
                }
                //若为其他模式，则将文本框设为不可编辑，并根据模式的不同设置不同的数据
                else {
                    for(JTextField textField : setDataField) {
                        textField.setEditable(false);
                    }
                    if(buttonText.equals("简单")) {
                        row = 9;
                        column = 9;
                        mineNumber = 10;
                    }
                    else if(buttonText.equals("普通")) {
                        row = 16;
                        column = 16;
                        mineNumber = 40;
                    }
                    else if(buttonText.equals("困难")) {
                        row = 16;
                        column = 30;
                        mineNumber = 99;
                    }
                    setDataField[0].setText(String.valueOf(row));
                    setDataField[1].setText(String.valueOf(column));
                    setDataField[2].setText(String.valueOf(mineNumber));
                }
            }
        }

        /**
         * 向工具栏添加按钮
         * @param toolBar	工具栏
         */
        private void addToolButtons(JToolBar toolBar) {
            JButton toolButton = null;
            ToolButtonActionListener tbal = new ToolButtonActionListener();
            toolButton = makeButton("新建", "根据设置的数据创建一个扫雷游戏", tbal);
            toolBar.add(toolButton);
            toolButton = makeButton("设置", "设置扫雷游戏的数据", tbal);
            toolBar.add(toolButton);
        }

        /**
         * 创建设置面板
         * @param panel 面板
         */
        private void creatSetPanel(JPanel panel) {
            GridBagLayout gridBagLayout = new GridBagLayout();	//创建网格包布局管理器
            GridBagConstraints constraints = new GridBagConstraints();	//网格包布局的约束类
            //设置网格包的数据
            gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            panel.setLayout(gridBagLayout);	//将panel面板的布局设置网格包布局

            //创建模式单选按钮
            modelButton = new JRadioButton[4];
            creatModelButton(modelButton);

            //创建设置游戏数据的文本框
            setDataField = new JTextField[3];
            creatSetDataField(setDataField);

            //添加第一行内容
            constraints.weightx = 0.3;
            constraints.weighty = 0.3;
            constraints.insets = new Insets(0, 0, 5, 5);
            addComponent(modelButton[0], 1, 1, 1, 2, 0, 10, constraints, panel);
            addComponent(modelButton[1], 1, 1, 3, 2, 0, 10, constraints, panel);
            addComponent(modelButton[2], 1, 1, 5, 2, 0, 10, constraints, panel);
            addComponent(modelButton[3], 1, 1, 7, 2, 0, 10, constraints, panel);

            //添加第二行内容
            constraints.weightx = 0.3;
            constraints.weighty = 0.3;
            addComponent(new JLabel("行数："), 1, 1, 1, 4, 0, GridBagConstraints.EAST, constraints, panel);
            addComponent(setDataField[0], 1, 1, 2, 4, GridBagConstraints.HORIZONTAL, 10, constraints, panel);
            addComponent(new JLabel("列数："), 1, 1, 3, 4, 0, GridBagConstraints.EAST, constraints, panel);
            addComponent(setDataField[1], 1, 1, 4, 4, GridBagConstraints.HORIZONTAL, 10, constraints, panel);
            addComponent(new JLabel("地雷数："), 1, 1, 5, 4, 0, GridBagConstraints.EAST, constraints, panel);
            addComponent(setDataField[2], 1, 1, 6, 4, GridBagConstraints.HORIZONTAL, 10, constraints, panel);

            //添加第三行内容
            constraints.weightx = 0.3;
            constraints.weighty = 0.3;
            NavigationButtonActionListener nbal = new NavigationButtonActionListener();
            addComponent(makeButton("保存", "保存自定义设定的数据", nbal), 2, 2, 2, 6, 0, 10, constraints, panel);
            addComponent(makeButton("返回", "返回到扫雷界面", nbal), 2, 2, 5, 6, 0, 10, constraints, panel);

        }

        /**
         * 创建模式按钮
         * @param rb	单选按钮数组
         */
        private void creatModelButton(JRadioButton[] rb) {
            ModelButtonActionListener mbal = new ModelButtonActionListener();
            for(int i = 0; i < rb.length; ++i) {
                rb[i] = new JRadioButton();
            }
            rb[0] = makeRadioButton("简单", "", mbal);
            rb[1] = makeRadioButton("普通", "", mbal);
            rb[2] = makeRadioButton("困难", "", mbal);
            rb[3] = makeRadioButton("自定义", "", mbal);
            rb[0].setSelected(true);	//设置默认选择"简单"模式
            ButtonGroup bg = new ButtonGroup();	//将单选按钮添加到按钮组中
            for(JRadioButton b : modelButton) {
                bg.add(b);
            }
        }
        /**
         * 创建设置游戏数据的文本框
         * @param tf 文本框数组
         */
        private void creatSetDataField(JTextField[] tf) {
            for(int i = 0; i < tf.length; ++i) {
                tf[i] = new JTextField("");
                tf[i].setEditable(false);	//设置默认为不可编辑
                tf[i].setColumns(10);	//设置列数为10
            }
            //默认显示行数、列数、地雷个数
            tf[0].setText(String.valueOf(row));
            tf[1].setText(String.valueOf(column));
            tf[2].setText(String.valueOf(mineNumber));
        }

        /**
         * 添加组件和经过设置GridBagConstraints到面板中
         * @param jc	组件
         * @param gridwidth	组件所占的列数
         * @param gridheight	组件所占的行数
         * @param gridx	组件左上角在网格中的行数
         * @param gridy	组件左上角在网格中的列数
         * @param fill	组件填充网格方式
         * @param anchor	组件在显示区域的摆放位置
         * @param gbc	网格包的约束类
         * @param panel	面板
         */
        private void addComponent(JComponent jc, int gridwidth, int gridheight, int gridx, int gridy, int fill, int anchor, GridBagConstraints gbc, JPanel panel) {
            gbc.gridwidth = gridwidth;
            gbc.gridheight = gridheight;
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.fill = fill;
            gbc.anchor = anchor;
            panel.add(jc, gbc);
        }

        /**
         * 根据给定的参数创建按钮
         * @param altText	显示文本
         * @param toolTipText	提示文本
         * @param al	监听器
         * @return	按钮
         */
        private JButton makeButton(String altText, String toolTipText, ActionListener al) {
            JButton tempButton = new JButton(altText);
            tempButton.setToolTipText(toolTipText);
            tempButton.addActionListener(al);
            return tempButton;
        }

        /**
         * 根据给定的参数创建单选按钮
         * @param altText	显示文本
         * @param toolTipText	提示文本
         * @param al	监听器
         * @return	单选按钮
         */
        private JRadioButton makeRadioButton(String altText, String toolTipText, ActionListener al) {
            JRadioButton tempButton = new JRadioButton(altText);
            tempButton.setToolTipText(toolTipText);
            tempButton.addActionListener(al);
            return tempButton;
        }

        /**
         * 创建一个新的扫雷游戏
         * @param panel
         */
        private void newMineSweeping(JPanel panel) {
            panel.remove(panelGame);	//移除原先的游戏面板
            this.setSize(this.row*50, this.column*50);
            panelGame = new JPanel(new GridLayout(this.row, this.column));	//创建新的游戏面板
            //创建数据
            creatData();
            creatButton(panelGame);
            panel.add(panelGame, BorderLayout.CENTER);
            panel.validate();	//刷新面板，使其进行更新
            setLocationRelativeTo(null);
        }

        /**
         * 创建扫雷按钮
         * @param panel	面板
         */
        private void creatButton(JPanel panel) {
            this.button = new JButton[this.row][this.column];
            MineSweepingButtonActionListener bal = new MineSweepingButtonActionListener();
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.column; j++) {
                    //设置按钮的文本、字体、前景色、名字
                    this.button[i][j] = new JButton(this.data[i][j]==0?"":String.valueOf(this.data[i][j]));
                    this.button[i][j].setFont(this.font);
                    this.button[i][j].setForeground(colorInvisible);
                    this.button[i][j].setName(String.valueOf(i)+ " " +String.valueOf(j));
                }
            }
            //向按钮添加监听器，将按钮添加到面板中
            for (JButton[] bs : this.button) {
                for (JButton b : bs) {
                    b.addMouseListener(bal);
                    b.addActionListener(bal);
                    panel.add(b);
                }
            }
        }

        /**
         * 移除按钮的监听器
         * @param button	多个按钮
         */
        private void removeActionListener(JButton... button) {
            for (JButton b : button) {
                b.removeMouseListener(null==b.getMouseListeners()[0]?null:b.getMouseListeners()[0]);
                b.removeActionListener(null==b.getActionListeners()[0]?null:b.getActionListeners()[0]);
            }
        }

        /**
         * 创建扫雷数据
         */
        private void creatData() {
            Random r = new Random();
            this.data = new int[this.row][this.column];
            int i = 0;
            //随机生成行和列，若该行列上不是雷，则将该行列添加雷；若该行列上是雷，则重新随机
            while (i < this.mineNumber) {
                int randomR = r.nextInt(this.row);
                int randomC = r.nextInt(this.column);
                if (this.data[randomR][randomC] == -1) {
                    i--;
                } else {
                    this.data[randomR][randomC] = -1;
                }
                i++;
            }
            i = 0;
            //遍历所有非地雷区块，设置每个区块周围的地雷数
            while (i < this.row) {
                int j = 0;
                while (j < this.column) {
                    this.data[i][j] = (this.data[i][j] == -1) ? -1 : findAroundMineNumber(i, j);
                    j++;
                }
                i++;
            }
        }

        /**
         * 寻找指定行列周围9x9区域的地雷数
         * @param thisRow	行
         * @param thisColumn	列
         * @return	地雷数
         */
        private int findAroundMineNumber(int thisRow, int thisColumn) {
            int aroundMineNumber = 0;
            int i = thisRow - 1;
            while (i <= thisRow + 1) {
                if (i >= 0 && i < this.row) {
                    int j = thisColumn - 1;
                    while (j <= thisColumn + 1) {
                        if (j >= 0 && j < this.column && (!(i == thisRow && j == thisColumn) && this.data[i][j] == -1)) {
                            aroundMineNumber++;
                        }
                        j++;
                    }
                }
                i++;
            }
            return aroundMineNumber;
        }

        /**
         * 结束游戏时显示所有的按钮信息
         */
        private void showAllData() {
            for (JButton[] bs : this.button) {
                for (JButton b : bs) {
                    setOpenButtonStyle(b);
                }
                removeActionListener(bs);	//移除监听器
            }
        }

        /**
         * 打开空白按钮周围的区块
         * @param b	按钮
         */
        private void openAroundBlock(JButton b) {
            int[] rowAndColumn = getButtonRowAndColumn(b);
            int thisRow = rowAndColumn[0], thisColumn = rowAndColumn[1];
            int i = thisRow - 1;
            while (i <= thisRow + 1) {
                if (i >= 0 && i < this.row) {
                    int j = thisColumn - 1;
                    while (j <= thisColumn + 1) {
                        //若打开的区块也是空白的，则对其进行点击操作
                        if (j >= 0 && j < this.column && !((i == thisRow && j == thisColumn) || this.data[i][j] == -1 || this.button[i][j].getForeground() != this.colorInvisible)) {
                            if (this.data[i][j] == 0) {
                                this.button[i][j].doClick();
                                //否则，显示该按钮的信息
                            } else {
                                setOpenButtonStyle(this.button[i][j]);
                            }
                        }
                        j++;
                    }
                }
                i++;
            }
        }

        /**
         * 判断当前游戏是否胜利
         * @return
         */
        private boolean isWin() {
            for (JButton[] bs : this.button) {
                for (JButton b : bs) {
                    //若有区块未打开且该区块不是地雷，则当前游戏还未结束
                    if (b.getForeground() == this.colorInvisible && !b.getText().equals("-1")) {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * 根据动作事件获取点击的按钮，根据按钮上的信息去更新游戏的状态
         * @param tempButton	按钮
         */
        private void update(JButton tempButton) {
            int[] rowAndColumn = getButtonRowAndColumn(tempButton);
            int thisRow = rowAndColumn[0], thisColumn = rowAndColumn[1];
            //若该按钮对应的数据是地雷，则弹出游戏失败的窗口并显示所有的按钮信息
            if (data[thisRow][thisColumn] == -1) {
                JOptionPane.showMessageDialog(getContentPane(), "您踩到地雷了，游戏结束。", "失败", 0);
                showAllData();
                return;
            }
            //否则设置该按钮为打开状态
            setOpenButtonStyle(tempButton);
            //若该按钮对应的数据是空白，则打开周围的区块
            if (data[thisRow][thisColumn] == 0) {
                openAroundBlock(tempButton);
            }
            //若游戏胜利，则弹出游戏胜利的窗口并显示所有的按钮信息
            if (isWin()) {
                JOptionPane.showMessageDialog(getContentPane(), "您发现了所有的地雷，游戏结束。", "胜利", 1);
                showAllData();
            }
        }

        /**
         * 设置打开按钮的样式
         * @param button	按钮
         */
        private void setOpenButtonStyle(JButton button) {
            int[] rowAndColumn = getButtonRowAndColumn(button);
            button.setForeground(colorVisible);	//设置按钮的前景色可见
            //若该按钮对应的数据是空白，则将该按钮的背景色设为白色
            if (this.data[rowAndColumn[0]][rowAndColumn[1]] == 0) {
                button.setBackground(Color.WHITE);
            } else {
                //若该按钮对应的数据是地雷，则将该按钮的背景色设为红色
                if (this.data[rowAndColumn[0]][rowAndColumn[1]] == -1) {
                    button.setText("B");
                    button.setBackground(Color.RED);
                }
            }
        }

        /**
         * 根据按钮的名字获得对应的行和列
         * @param button	按钮
         * @return	包含行和列的数组
         */
        private int[] getButtonRowAndColumn(JButton button) {
            String[] dataXY = button.getName().split(" ");
            return new int[]{Integer.parseInt(dataXY[0]), Integer.parseInt(dataXY[1])};
        }

        /**
         * 创建MineSweeping框架
         * 设置窗口可见
         */
        public static void main(String[] args) {
            new MineSweeping().setVisible(true);
        }
    }
}
