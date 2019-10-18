package com.service.impl;

import com.dao.MineDao;
import com.domain.Point;
import com.domain.*;
import com.service.ViewService;
import com.utils.BaseHolder;
import com.utils.ComponentImage;
import com.utils.GameOverDialog;
import com.utils.Information;
import com.view.MainWindow;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;

@Service("viewService")
public class ViewServiceImpl implements ViewService {

    private final ThreadLocal<DynamicTime> thread = new ThreadLocal<DynamicTime>();

    @Autowired
    private ViewComponent viewComponent;

    @Autowired
    private MineDao mineDao;

    @Autowired
    private GameNowData gameNowData;

    @Override
    public void setModelEnabled(MineModel model) {
        setEnabled(model.getName().equals("自定义"));
    }

    @Override
    public void setModelDataText(MineModel model) {
        viewComponent.getRowTextField().setText(String.valueOf(model.getRow()));
        viewComponent.getColumnTextField().setText(String.valueOf(model.getColumn()));
        viewComponent.getMineNumberTextField().setText(String.valueOf(model.getMineNumber()));
        viewComponent.getMineDensityTextField().setText(String.valueOf(model.getMineDensity()));
    }

    private void setEnabled(boolean enabled) {
        viewComponent.getRowTextField().setEnabled(enabled);
        viewComponent.getColumnTextField().setEnabled(enabled);
        viewComponent.getMineNumberTextField().setEnabled(enabled);
    }

    @Override
    public void showErrorInformation(String err) {
        JOptionPane.showMessageDialog(null, err, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInformation(String information) {
        JOptionPane.showMessageDialog(null, information, "Tip", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setDefaultModel(MineModel model) {
        switch (model.getName()) {
            case "简单":
                viewComponent.getEasyModelButton().setSelected(true);
                break;
            case "普通":
                viewComponent.getOrdinaryModelButton().setSelected(true);
                break;
            case "困难":
                viewComponent.getHardModelButton().setSelected(true);
                break;
            case "自定义":
                viewComponent.getCustomizeModelButton().setSelected(true);
                break;
        }
    }

    @Override
    public void setWindowSize(MineModel nowMineModel, MainWindow mainWindow, Dimension screenSize) {
        int width = Math.min(nowMineModel.getColumn() * 35, (int) (screenSize.getWidth() / 1.5));
        int height = Math.min(nowMineModel.getRow() * 35, (int) (screenSize.getHeight() / 1.5));
        mainWindow.setSize(width, height);
    }

    @Override
    public void loadRemainderButtonsIcon(GameNowData gameNowData) {
        final MineJButton[][] buttons = gameNowData.getButtons();

        for (MineJButton[] button : buttons) {
            for (MineJButton b : button) {
                int status = b.getStatus();
                if (status == MineJButton.MINE && b.isFlag()) {
                    b.setIcon(ComponentImage.getGameImageIcon(ComponentImage.sweepMineBufferedImage, b));
                } else if (status == MineJButton.EXPLODE) {
                    b.setIcon(ComponentImage.getGameImageIcon(ComponentImage.explodeMineBufferedImage, b));
                } else if (status == MineJButton.MINE) {
                    b.setIcon(ComponentImage.getGameImageIcon(ComponentImage.mineBufferedImage, b));
                }
            }
        }
    }

    @Override
    public boolean openSpace(MineJButton button, GameNowData gameNowData) {
        if (button.isFlag()) {
            return false;
        }

        final int data = button.getData();

        if (button.getStatus() == MineJButton.MINE) {
            gameNowData.setFail(true);
            button.setStatus(MineJButton.EXPLODE);
            return true;
        }

        gameNowData.setOpenSpace(gameNowData.getOpenSpace() + 1);
        setButtonDisPlayStatus(button);
        if (data == 0) {
            openAroundSpace(button, gameNowData);
        } else {
            button.setIcon(ComponentImage.getGameImageIcon(ComponentImage.numberBufferedImage[data - 1], button));
        }

        removeButtonListener(button);

        return false;
    }

    @Override
    public void setFlag() {
        MineJButton button = viewComponent.getNowClickButton();
        JLabel remainingMineNumberLabel = viewComponent.getRemainingMineNumberLabel();
        if (button.isFlag()) {
            button.setIcon(null);
            remainingMineNumberLabel.setText((Integer.parseInt(remainingMineNumberLabel.getText()) + 1) + "");
            button.setFlag(false);
        } else {
            button.setIcon(ComponentImage.getGameImageIcon(ComponentImage.flagBufferedImage, button));
            remainingMineNumberLabel.setText((Integer.parseInt(remainingMineNumberLabel.getText()) - 1) + "");
            button.setFlag(true);
        }
    }

    @Override
    public void cleanViewData(GameNowData gameNowData) {
        final MineJButton[][] buttons = gameNowData.getButtons();
        for (MineJButton[] button : buttons) {
            for (MineJButton mineJButton : button) {
                mineJButton.setIcon(null);
                mineJButton.setText("");
                mineJButton.setBackground(null);
                mineJButton.setStatus(mineJButton.getData() == -1 ? MineJButton.MINE : MineJButton.HIDE_SPACE);
            }
        }
    }

    @Override
    public void showDynamicTime() {
        if (thread.get() == null) {
            thread.set(new DynamicTime(viewComponent.getTimeLabel()));
            new Thread(thread.get()).start();
        }
        thread.get().setInitStatus();
    }

    @Override
    public void initRemainingMineNumberLabel(MineModel nowMineModel) {
        viewComponent.getRemainingMineNumberLabel().setText(nowMineModel.getMineNumber() + "");
    }

    @Override
    public void showNowOtherSetting() {
        String gameName = null;
        boolean nowOpenRecordStatus = false;
        try {
            gameName = mineDao.findNowGameName();
            nowOpenRecordStatus = mineDao.findNowOpenRecordStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gameName == null || gameName.trim().equals("")) {
            throw new RuntimeException();
        }
        viewComponent.getGameNameField().setText(gameName);
        viewComponent.getOpenRecordCheckBox().setSelected(nowOpenRecordStatus);
    }

    @Override
    public void showGameOverDialog(GameOverDialogData data) {
        if (data == null) {
            return;
        }

        GameOverDialog gameOverDialog = new GameOverDialog();

        gameOverDialog.setTitle(
                data.isWin() ? (data.isBreakRecord() ? Information.titleWinAndBreakRecord : Information.titleWin) : Information.titleFail);

        gameOverDialog.initShowData(data);

        gameOverDialog.setVisible(true);
    }

    /**
     * 添加扫雷按钮组到扫雷面板中
     */
    @Override
    public void addButtonsToPanel(MineJButton[][] buttons) {
        JPanel buttonsPanel = viewComponent.getButtonsPanel();
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                buttonsPanel.add(b);
            }
        }
    }

    @Override
    public void addButtonsMouseListener() {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        for (JButton[] button : gameNowData.getButtons()) {
            for (JButton b : button) {
                b.addMouseListener(listener);
            }
        }
    }

    @Override
    public void removeButtonsListener() {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        for (JButton[] button : gameNowData.getButtons()) {
            for (JButton b : button) {
                b.removeMouseListener(listener);
            }
        }
    }

    @Override
    public void changeExpressionStatus() {
        JLabel label = viewComponent.getExpressionLabel();
        if (gameNowData.isFail()) {
            label.setIcon(ComponentImage.angryImageIcon);
        } else {
            label.setIcon(ComponentImage.happyImageIcon);
        }
    }

    @Override
    public void setDefaultExpression() {
        viewComponent.getExpressionLabel().setIcon(ComponentImage.confusedImageIcon);
    }

    @Override
    public void setAllModelBestGameData(MineSweepingGameData[] allModelBestGameData) {
        String[] playerName = new String[4];
        String[] time = new String[4];
        int i = 0;
        for (MineSweepingGameData modelBestGameData : allModelBestGameData) {
            if (modelBestGameData == null) {
                playerName[i] = "-";
                time[i] = "-";
            } else {
                playerName[i] = modelBestGameData.getPlayerName();
                time[i] = modelBestGameData.getTime().toString()+"ms";
            }
            i++;
        }
        viewComponent.getEasyModelBestPlayerNameLabel().setText(playerName[0]);
        viewComponent.getEasyModelBestTimeLabel().setText(time[0]);

        viewComponent.getOrdinaryModelBestPlayerNameLabel().setText(playerName[1]);
        viewComponent.getOrdinaryModelBestTimeLabel().setText(time[1]);

        viewComponent.getHardModelBestPlayerNameLabel().setText(playerName[2]);
        viewComponent.getHardModelBestTimeLabel().setText(time[2]);

        viewComponent.getCustomizeModelBestPlayerNameLabel().setText(playerName[3]);
        viewComponent.getCustomizeModelBestTimeLabel().setText(time[3]);
    }

    private void removeButtonListener(MineJButton button) {
        button.removeMouseListener(BaseHolder.getBean("mouseListener"));
    }

    private void setButtonDisPlayStatus(MineJButton button) {
        button.setBackground(Color.WHITE);
        button.setStatus(MineJButton.DISPLAY_SPACE);
    }

    private void openAroundSpace(MineJButton button, GameNowData gameNowData) {
        Point point = button.getPoint();
        MineJButton[][] mineViewButtons = gameNowData.getButtons();
        int row = mineViewButtons.length;
        for (int i = point.getI() - 1; i <= point.getI() + 1 && i < row; i++) {
            if (i < 0) {
                continue;
            }
            int column = mineViewButtons[i].length;
            for (int j = point.getJ() - 1; j <= point.getJ() + 1 && j < column; j++) {
                if (j < 0) {
                    continue;
                }
                MineJButton mineJButton = mineViewButtons[i][j];
                int data = mineJButton.getData();
                if (mineJButton.getStatus() != MineJButton.DISPLAY_SPACE) {
                    setButtonDisPlayStatus(mineJButton);

                    removeButtonListener(mineJButton);

                    if (data == 0) {
                        openAroundSpace(mineJButton, gameNowData);
                    } else {
                        mineJButton.setIcon(ComponentImage.getGameImageIcon(ComponentImage.numberBufferedImage[data - 1], mineJButton));
                    }
                    gameNowData.setOpenSpace(gameNowData.getOpenSpace() + 1);
                }
            }
        }
    }

    private class DynamicTime implements Runnable {
        private JLabel timeLabel;
        private int time;
        private boolean flag;

        public DynamicTime(JLabel timeLabel) {
            this.timeLabel = timeLabel;
        }

        public void setInitStatus() {
            time = 0;
            flag = false;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    try {
                        //判断是否被中断
                        if (gameNowData.isWin() || gameNowData.isFail()) {
                            flag = true;
                            while (flag) {
                                Thread.sleep(1000);
                            }
                        }
                        timeLabel.setText(String.valueOf(time++));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LoggerFactory.getLogger(ViewServiceImpl.class).error("", e);
                    }
                }
            }
        }
    }
}
