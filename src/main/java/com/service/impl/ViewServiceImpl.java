package com.service.impl;

import com.dao.MineDao;
import com.domain.Point;
import com.domain.*;
import com.service.ViewService;
import com.utils.BaseHolder;
import com.utils.ButtonImage;
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
                    b.setIcon(ButtonImage.getGameImageIcon(ButtonImage.sweepMineBufferedImage, b));
                } else if (status == MineJButton.EXPLODE) {
                    b.setIcon(ButtonImage.getGameImageIcon(ButtonImage.explodeMineBufferedImage, b));
                } else if (status == MineJButton.MINE) {
                    b.setIcon(ButtonImage.getGameImageIcon(ButtonImage.mineBufferedImage, b));
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
        final GameNowStatus nowStatus = gameNowData.getNowStatus();

        if (button.getStatus() == MineJButton.MINE) {
            nowStatus.setFail(true);
            button.setStatus(MineJButton.EXPLODE);
            return true;
        }

        nowStatus.setOpenSpace(nowStatus.getOpenSpace() + 1);
        setButtonDisPlayStatus(button);
        if (data == 0) {
            openAroundSpace(button, gameNowData);
        } else {
            button.setIcon(ButtonImage.getGameImageIcon(ButtonImage.numberBufferedImage[data - 1], button));
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
            button.setIcon(ButtonImage.getGameImageIcon(ButtonImage.flagBufferedImage, button));
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
    public void showDynamicTime(GameNowStatus gameNowStatus) {
        if (thread.get() == null) {
            thread.set(new DynamicTime(viewComponent.getTimeLabel(), gameNowStatus));
            new Thread(thread.get()).start();
        }
        thread.get().setInitStatus(gameNowStatus);
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
    public void addButtonsMouseListener(MineJButton[][] buttons) {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                b.addMouseListener(listener);
            }
        }
    }

    @Override
    public void removeButtonsListener(MineJButton[][] buttons) {
        MouseListener listener = BaseHolder.getBean("mouseListener");
        for (JButton[] button : buttons) {
            for (JButton b : button) {
                b.removeMouseListener(listener);
            }
        }
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
                        mineJButton.setIcon(ButtonImage.getGameImageIcon(ButtonImage.numberBufferedImage[data - 1], mineJButton));
                    }
                    gameNowData.getNowStatus().setOpenSpace(gameNowData.getNowStatus().getOpenSpace() + 1);
                }
            }
        }
    }

    private static class DynamicTime implements Runnable {
        private JLabel timeLabel;
        private int time;
        private GameNowStatus gameNowStatus;
        private boolean flag;

        public DynamicTime(JLabel timeLabel, GameNowStatus gameNowStatus) {
            this.timeLabel = timeLabel;
            this.gameNowStatus = gameNowStatus;
        }

        public void setInitStatus(GameNowStatus gameNowStatus) {
            time = 0;
            this.gameNowStatus = gameNowStatus;
            flag = false;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    try {
                        //判断是否被中断
                        if (gameNowStatus.isWin() || gameNowStatus.isFail()) {
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
