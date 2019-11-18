package com.service.impl;

import com.dao.GamePropertiesDao;
import com.domain.*;
import com.pojo.MineSweepingGameData;
import com.service.GameViewService;
import com.utils.Point;
import com.utils.*;
import com.view.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * 处理扫雷页面的业务层
 * 接收控制层的数据
 * 返回数据给控制层
 */
@Service("gameViewService")
public class GameViewServiceImpl implements GameViewService {
    /**
     * 视图组件
     */
    private final ViewComponent viewComponent;
    /**
     * dao层对象
     */
    private final GamePropertiesDao mineDao;
    /**
     * 游戏当前数据
     */
    private final GameNowData gameNowData;
    /**
     * 存放显示动态时间的类
     */
    private DynamicTime dynamicTime;

    /**
     * 注入成员变量
     */
    @Autowired
    public GameViewServiceImpl(ViewComponent viewComponent, GamePropertiesDao mineDao, GameNowData gameNowData) {
        this.viewComponent = viewComponent;
        this.mineDao = mineDao;
        this.gameNowData = gameNowData;
    }

    /**
     * 根据扫雷模式设置文本框的编辑性
     *
     * @param model 扫雷模式
     */
    @Override
    public void setModelEnabled(MineModel model) {
        setEnabled(model.getName().equals("自定义"));
    }

    /**
     * 设置文本框的编辑性
     *
     * @param enabled 是否可以编辑
     */
    private void setEnabled(boolean enabled) {
        viewComponent.getRowTextField().setEnabled(enabled);
        viewComponent.getColumnTextField().setEnabled(enabled);
        viewComponent.getMineNumberTextField().setEnabled(enabled);
    }

    /**
     * 向文本框中设置扫雷模式的数据
     *
     * @param model 扫雷模式
     */
    @Override
    public void setModelDataText(MineModel model) {
        DecimalFormat format = BaseHolder.getBean("highDecimalFormat", DecimalFormat.class);
        viewComponent.getRowTextField().setText(String.valueOf(model.getRow()));
        viewComponent.getColumnTextField().setText(String.valueOf(model.getColumn()));
        viewComponent.getMineNumberTextField().setText(String.valueOf(model.getMineNumber()));
        viewComponent.getMineDensityTextField().setText(format.format(model.getMineDensity()));
    }

    /**
     * 显示错误信息的提示框
     *
     * @param err 错误信息
     */
    @Override
    public void showErrorInformation(String err) {
        JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 显示信息的提示框
     *
     * @param information 信息
     */
    @Override
    public void showInformation(String information) {
        JOptionPane.showMessageDialog(null, information, "Tip", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 设置默认模式
     *
     * @param model 模式对象
     */
    @Override
    public void setDefaultModel(MineModel model) {
        switch (model.getName()) {
            case "简单":
                viewComponent.getEasyModelRadioButtonMenuItem().setSelected(true);
                break;
            case "普通":
                viewComponent.getOrdinaryModelRadioButtonMenuItem().setSelected(true);
                break;
            case "困难":
                viewComponent.getHardModelRadioButtonMenuItem().setSelected(true);
                break;
            case "自定义":
                viewComponent.getCustomizeModelRadioButtonMenuItem().setSelected(true);
                break;
        }
    }

    /**
     * 设置窗口大小
     *
     * @param nowMineModel 当前扫雷模式
     * @param mainWindow   游戏窗口
     * @param screenSize   窗口大小
     */
    @Override
    public void setWindowSize(MineModel nowMineModel, MainWindow mainWindow, Dimension screenSize) {
        //根据地图大小设置窗口大小
        int width = Math.min(nowMineModel.getColumn() * 35, (int) (screenSize.getWidth()));
        int height = Math.min(nowMineModel.getRow() * 35, (int) (screenSize.getHeight()));
        mainWindow.setSize(width, height);
    }

    /**
     * 加载剩余按钮的图片
     */
    @Override
    public void loadRemainderButtonsIcon() {
        for (MineJButton[] button : gameNowData.getButtons()) {
            for (MineJButton b : button) {
                int status = b.getStatus();
                //如果该区块是地雷且插了旗帜，则显示该雷被扫除的图片
                if (status == MineJButton.MINE && b.isFlag()) {
                    setButtonIcon(b, ComponentImage.sweepMineBufferedImage);
                    //如果该区块是地雷爆炸，则显示地雷爆炸的图片
                } else if (status == MineJButton.EXPLODE) {
                    setButtonIcon(b, ComponentImage.explodeMineBufferedImage);
                    //如果该区块是地雷，则显示地雷的图片
                } else if (status == MineJButton.MINE) {
                    setButtonIcon(b, ComponentImage.mineBufferedImage);
                }
            }
        }
    }

    /**
     * 打开空白方块
     *
     * @param button 点击的按钮
     */
    @Override
    public boolean openSpace(MineJButton button) {
        //限制旗帜方块的打开
        if (button.isFlag()) {
            return false;
        }

        //如果该区块是地雷，则游戏结束
        if (button.getStatus() == MineJButton.MINE) {
            gameNowData.setFail(true);
            button.setStatus(MineJButton.EXPLODE);
            return true;
        }

        //否则将该区块打开
        final int data = button.getData();
        //打开的区块加一
        gameNowData.setOpenSpace(gameNowData.getOpenSpace() + 1);
        //设置按钮为显示状态
        setButtonDisPlayStatus(button);
        //尝试打开周围无雷的区块
        if (data == 0) {
            setButtonIcon(button, ComponentImage.displaySpaceMineBufferedImage);
            openAroundSpace(button, gameNowData.getButtons());
        } else {
            //周围有雷，则显示雷的个数
            setButtonIcon(button, ComponentImage.numberBufferedImage[data - 1]);
        }

        return false;
    }

    /**
     * 设置当前格子为旗帜或取消显示旗帜
     */
    @Override
    public void setFlag() {
        MineJButton button = viewComponent.getNowClickButton();
        JLabel remainingMineNumberLabel = viewComponent.getRemainingMineNumberLabel();
        final boolean flag = button.isFlag();

        //若为旗帜则显示旗帜的图片，否则改为隐藏区块的图片
        setButtonIcon(button, flag ? ComponentImage.hideSpaceMineBufferedImage : ComponentImage.flagBufferedImage);
        //反转按钮是否为旗帜
        button.setFlag(!flag);
        //设置剩余的地雷数（插一个旗子则减一个地雷数）
        remainingMineNumberLabel.setText((Integer.parseInt(remainingMineNumberLabel.getText()) + (flag ? 1 : -1)) + "");
    }

    /**
     * 设置按钮的图片
     *
     * @param button        按钮
     * @param bufferedImage 按钮图片
     */
    private void setButtonIcon(MineJButton button, BufferedImage bufferedImage) {
        button.setIcon(ComponentImage.getGameImageIcon(bufferedImage, button));
        button.setBufferedImage(bufferedImage);
    }

    /**
     * 清理视图的数据
     */
    @Override
    public void cleanViewData() {
        for (MineJButton[] button : gameNowData.getButtons()) {
            for (MineJButton mineJButton : button) {
                //设置隐藏区块图片
                setButtonIcon(mineJButton, ComponentImage.hideSpaceMineBufferedImage);
                //设置为非旗帜
                mineJButton.setFlag(false);
                //重新设置扫雷按钮的状态
                mineJButton.setStatus(mineJButton.getData() == -1 ? MineJButton.MINE : MineJButton.HIDE_SPACE);
            }
        }
    }

    /**
     * 动态显示当前时间
     */
    @Override
    public void showDynamicTime() {
        //若该对象为空，则创建其并运行
        if (dynamicTime == null) {
            dynamicTime = new DynamicTime(viewComponent.getTimeLabel());
            new Thread(dynamicTime).start();
        }
        //获得该对象并设置其为初始化状态
        dynamicTime.setInitStatus();
    }

    /**
     * 初始化剩余地雷数
     *
     * @param nowMineModel 当前扫雷模式
     */
    @Override
    public void initRemainingMineNumberLabel(MineModel nowMineModel) {
        viewComponent.getRemainingMineNumberLabel().setText(nowMineModel.getMineNumber() + "");
    }

    /**
     * 显示当前的其他设置
     */
    @Override
    public void showNowOtherSetting() {
        //获取玩家名称和开启记录的状态
        String gameName;
        boolean nowOpenRecordStatus;
        try {
            gameName = mineDao.findNowGameName();
            nowOpenRecordStatus = mineDao.findNowOpenRecordStatus();
        } catch (IOException e) {
            //有异常则抛出加载玩家数据基础设置异常
            throw new RuntimeException(Information.loadPlayerDataSettingError);
        }
        //若名字为空则抛出玩家名为空的异常
        if (StringUtils.isEmpty(gameName)) {
            throw new RuntimeException(Information.playerNameNotNull);
        }

        //显示玩家名称和是否开启记录
        viewComponent.getGameNameField().setText(gameName);
        viewComponent.getOpenRecordCheckBox().setSelected(nowOpenRecordStatus);
    }

    /**
     * 显示游戏结束后的对话框
     *
     * @param data 对话框数据
     */
    @Override
    public void showGameOverDialog(GameOverDialogData data) {
        //若数据为空，则直接返回
        if (data == null) {
            return;
        }

        //创建一个对话框
        GameOverDialog gameOverDialog = new GameOverDialog();
        gameOverDialog.setTitle(
                data.isWin() ? (data.isBreakRecord() ? Information.titleWinAndBreakRecord : Information.titleWin) : Information.titleFail);
        gameOverDialog.initShowData(data);
        //显示对话框
        gameOverDialog.setVisible(true);
    }

    /**
     * 添加按钮组到面板上
     *
     * @param buttons 按钮组
     */
    @Override
    public void addButtonsToPanel(MineJButton[][] buttons) {
        JPanel buttonsPanel = viewComponent.getButtonsPanel();
        Arrays.stream(buttons).flatMap(Arrays::stream).forEach(button -> buttonsPanel.add(button));
    }

    /**
     * 为按钮组添加监听器
     */
    @Override
    public void addButtonsMouseListener() {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        Arrays.stream(gameNowData.getButtons()).flatMap(Arrays::stream).forEach(button -> button.addMouseListener(listener));
    }

    /**
     * 移除按钮组的监听器
     */
    @Override
    public void removeButtonsListener() {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        Arrays.stream(gameNowData.getButtons()).flatMap(Arrays::stream).forEach(button -> button.removeMouseListener(listener));

    }

    /**
     * 改变表情标签的状态
     */
    @Override
    public void changeExpressionStatus() {
        final JLabel label = viewComponent.getExpressionLabel();
        //游戏失败则显示生气表情的图片，游戏胜利则显示开心表情的图片
        label.setIcon(
                ComponentImage.getGameImageIcon(gameNowData.isFail() ? ComponentImage.angryBufferedImage : ComponentImage.happyBufferedImage, label));
    }

    /**
     * 设置默认的表情
     */
    @Override
    public void setDefaultExpression() {
        final JLabel label = viewComponent.getExpressionLabel();
        label.setIcon(ComponentImage.getGameImageIcon(ComponentImage.confusedBufferedImage, label));
    }

    /**
     * 设置各个模式最短用时的数据
     *
     * @param allModelBestGameData 所有模式最短用时的游戏数据
     */
    @Override
    public void setAllModelBestGameData(MineSweepingGameData[] allModelBestGameData) {
        String[] playerName = new String[4];
        String[] time = new String[4];

        //设置各个模式最短用时的玩家名称和时间
        int i = 0;
        for (MineSweepingGameData modelBestGameData : allModelBestGameData) {
            if (modelBestGameData == null) {
                playerName[i] = "-";
                time[i] = "-";
            } else {
                playerName[i] = modelBestGameData.getPlayerName();
                time[i] = modelBestGameData.getTime().toString() + "ms";
            }
            i++;
        }

        //将数据显示在标签上
        viewComponent.getEasyModelBestPlayerNameLabel().setText(playerName[0]);
        viewComponent.getEasyModelBestTimeLabel().setText(time[0]);
        viewComponent.getOrdinaryModelBestPlayerNameLabel().setText(playerName[1]);
        viewComponent.getOrdinaryModelBestTimeLabel().setText(time[1]);
        viewComponent.getHardModelBestPlayerNameLabel().setText(playerName[2]);
        viewComponent.getHardModelBestTimeLabel().setText(time[2]);
        viewComponent.getCustomizeModelBestPlayerNameLabel().setText(playerName[3]);
        viewComponent.getCustomizeModelBestTimeLabel().setText(time[3]);
    }

    /**
     * 设置按钮的默认图片
     */
    @Override
    public void setMineJButtonDefaultIcon() {
        Arrays.stream(gameNowData.getButtons())
                .flatMap(Arrays::stream)
                .forEach(button -> setButtonIcon(button, ComponentImage.hideSpaceMineBufferedImage));
    }

    /**
     * 改变按钮的图片大小
     */
    @Override
    public void changeButtonsIconSize() {
        Arrays.stream(gameNowData.getButtons()).flatMap(Arrays::stream).forEach(button -> setButtonIcon(button, button.getBufferedImage()));
    }

    /**
     * 移除按钮的监听器
     *
     * @param button 按钮
     */
    private void removeButtonListener(MineJButton button) {
        button.removeMouseListener(BaseHolder.getBean("mouseListener"));
    }

    /**
     * 设置按钮的显示状态
     *
     * @param button 按钮
     */
    private void setButtonDisPlayStatus(MineJButton button) {
        setButtonIcon(button, ComponentImage.displaySpaceMineBufferedImage);
        button.setStatus(MineJButton.DISPLAY_SPACE);
        //移除按钮的监听器
        removeButtonListener(button);
    }

    /**
     * 打开该区块周围的空地
     *
     * @param button 按钮
     */
    private void openAroundSpace(MineJButton button, MineJButton[][] mineViewButtons) {
        //获取该按钮的位置
        Point point = button.getPoint();
        //搜索周围3x3区块（不包括自身）
        for (int i = point.getI() - 1; i <= point.getI() + 1 && i < mineViewButtons.length; i++) {
            if (i < 0) {
                continue;
            }
            for (int j = point.getJ() - 1; j <= point.getJ() + 1 && j < mineViewButtons[i].length; j++) {
                if (j < 0) {
                    continue;
                }
                //当前点的按钮
                MineJButton mineJButton = mineViewButtons[i][j];
                int data = mineJButton.getData();

                //若该按钮的状态不为显示状态且不为旗帜
                if (mineJButton.getStatus() != MineJButton.DISPLAY_SPACE && !mineJButton.isFlag()) {
                    //设置按钮为显示状态
                    setButtonDisPlayStatus(mineJButton);

                    //若该区块周围没有地雷，则递归打开该区块
                    if (data == 0) {
                        openAroundSpace(mineJButton, mineViewButtons);
                        //否则设置该区块周围的地雷数
                    } else {
                        setButtonIcon(mineJButton, ComponentImage.numberBufferedImage[data - 1]);
                    }
                    //打开的区块数加一
                    gameNowData.setOpenSpace(gameNowData.getOpenSpace() + 1);
                }
            }
        }
    }

    /**
     * 动态显示当前时间的类
     */
    private class DynamicTime implements Runnable {
        /**
         * 时间标签
         */
        private JLabel timeLabel;
        /**
         * 时间
         */
        private int time;
        /**
         * 计数门闩
         */
        private CountDownLatch countDownLatch = new CountDownLatch(1);

        /**
         * 初始化时间标签
         *
         * @param timeLabel 时间标签
         */
        public DynamicTime(JLabel timeLabel) {
            this.timeLabel = timeLabel;
        }

        /**
         * 设置初始化状态
         */
        public void setInitStatus() {
            time = 0;
            //当门闩数大于0时，门闩数减一
            if (countDownLatch.getCount() > 0L) {
                countDownLatch.countDown();
            }
        }

        /**
         * 显示当前游戏所经过的时间
         */
        @Override
        public void run() {
            while (true) {
                try {
                    //判断是否被中断
                    if (gameNowData.isWin() || gameNowData.isFail()) {
                        //重新创建一个门闩，并进行等待
                        countDownLatch = new CountDownLatch(1);
                        countDownLatch.await();
                    }
                    //设置时间
                    timeLabel.setText(String.valueOf(time++));
                    //当前线程休眠1s
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //若有异常则抛出显示当前时间的异常
                    throw new RuntimeException(Information.showNowTimeError);
                }
            }
        }
    }
}
