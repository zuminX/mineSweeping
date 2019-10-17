package com.service.impl;

import com.dao.MineDao;
import com.domain.*;
import com.service.MineService;
import com.utils.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service("mineService")
public class MineServiceImpl implements MineService {

    @Autowired
    private MineDao mineDao;

    @Autowired
    private ViewComponent viewComponent;

    private Mine mine = new Mine();
    private Preload preload = new Preload();

    private Queue<MineData> minePreloadData = new ConcurrentLinkedQueue<>();

    public MineServiceImpl() {
        new Thread(new Preload()).start();
    }

    @Override
    public Boolean changeModel(String modelName) {
        MineModel model = null;
        switch (modelName) {
            case "简单":
                model = mine.getEasyModel();
                break;
            case "普通":
                model = mine.getOrdinaryModel();
                break;
            case "困难":
                model = mine.getHardModel();
                break;
            case "自定义":
                model = mine.getCustomizeModel();
                break;
        }
        if (model == null) {
            throw new RuntimeException(Information.loadGameModelError);
        }
        mine.setNowMineModel(model);
        return false;
    }

    @Override
    public MineModel getNowMineModel() {
        return mine.getNowMineModel();
    }

    @Override
    public GameNowData newMineViewButtons() {
        final MineModel nowMineModel = getNowMineModel();
        final Integer row = nowMineModel.getRow();
        final Integer column = nowMineModel.getColumn();

        MineJButton[][] mineJButtons = new MineJButton[row][];
        for (int i = 0; i < row; i++) {
            mineJButtons[i] = new MineJButton[column];
            for (int j = 0; j < column; j++) {
                mineJButtons[i][j] = new MineJButton();
                mineJButtons[i][j].setPoint(new Point(i, j));
            }
        }
        return new GameNowData(mineJButtons, new GameNowStatus(nowMineModel.getMineNumber(), row * column));
    }

    @Override
    public void fillMineData(Point point, GameNowData gameNowData) {
        MineData mineData;
        do {
            if (minePreloadData.isEmpty()) {
                mineData = newMineData();
            } else {
                mineData = minePreloadData.poll();
            }
        } while (mineData.getMinePoint().contains(point));

        MineJButton[][] mineJButtons = gameNowData.getButtons();
        int[][] data = mineData.getData();
        for (int i = 0; i < mineJButtons.length; i++) {
            for (int j = 0; j < mineJButtons[i].length; j++) {
                mineJButtons[i][j].setStatus(data[i][j] == -1 ? MineJButton.MINE : MineJButton.HIDE_SPACE);
                mineJButtons[i][j].setData(data[i][j]);
            }
        }
    }

    @Override
    public Boolean saveSettingData() {
        MineModel customizeModelByData;
        try {
            customizeModelByData = mineDao.findCustomizeModelByData(viewComponent.getRowTextField().getText(),
                    viewComponent.getColumnTextField().getText(), viewComponent.getMineNumberTextField().getText());
        } catch (IOException e) {
            throw new RuntimeException(Information.loadGameBasicSettingError);
        }

        if (customizeModelByData == null) {
            throw new RuntimeException(Information.changeCustomizeDataError);
        }

        mine.setCustomizeModel(customizeModelByData);

        try {
            mineDao.updateCustomizeModelInProperties(customizeModelByData);
        } catch (IOException e) {
            throw new RuntimeException(Information.loadGameBasicSettingError);
        }

        final String name = viewComponent.getGameNameField().getText();
        if (name.trim().equals("")) {
            throw new RuntimeException(Information.playerNameNotNull);
        }
        try {
            mineDao.changeGameName(name);
        } catch (IOException e) {
            throw new RuntimeException(Information.loadPlayerDataSettingError);
        }

        try {
            mineDao.changeOpenRecordStatus(viewComponent.getOpenRecordCheckBox().isSelected());
        } catch (IOException e) {
            throw new RuntimeException(Information.loadPlayerDataSettingError);
        }
        return false;
    }

    private HashSet<Point> createMineData() {
        final MineModel nowMineModel = getNowMineModel();
        final Integer row = nowMineModel.getRow();
        final Integer column = nowMineModel.getColumn();
        final Integer mineNumber = nowMineModel.getMineNumber();
        Random random = new Random();
        HashSet<Point> points = new HashSet<>(mineNumber);
        while (points.size() < mineNumber) {
            final Point point = new Point(random.nextInt(row), random.nextInt(column));
            points.add(point);
        }
        return points;
    }

    private int[][] findAllSpaceAroundMineNumber(MineModel mineModel, Set<Point> mineData) {
        final int row = mineModel.getRow();
        final int column = mineModel.getColumn();
        int[][] aroundMineNumberData = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                aroundMineNumberData[i][j] = findAroundMineNumber(i, j, mineData);
            }
        }
        return aroundMineNumberData;
    }

    /**
     * 寻找指定行列周围9x9区域的地雷数
     *
     * @param row    行
     * @param column 列
     *
     * @return 地雷数
     */
    private int findAroundMineNumber(int row, int column, Set<Point> mineData) {
        int aroundMineNumber = 0;
        for (Point point : mineData) {
            final int i = point.getI();
            final int j = point.getJ();
            //若该地为地雷，则直接返回-1
            if (row == i && column == j) {
                return -1;
            }
            //该地雷在该地的范围内
            if (row >= i - 1 && row <= i + 1 && column >= j - 1 && column <= j + 1) {
                aroundMineNumber++;
            }
        }
        return aroundMineNumber;
    }

    private MineData newMineData() {
        final HashSet<Point> mineData = createMineData();
        final int[][] data = findAllSpaceAroundMineNumber(getNowMineModel(), mineData);
        return new MineData(mineData, data);
    }

    private class Preload implements Runnable {
        private MineModel oldMineModel = getNowMineModel();

        @Override
        public void run() {
            while (true) {
                try {
                    final MineModel nowMineModel = getNowMineModel();
                    if (!nowMineModel.equals(oldMineModel)) {
                        minePreloadData = new ConcurrentLinkedQueue<>();
                        oldMineModel = nowMineModel;
                    }
                    while (minePreloadData.size() < 10) {
                        minePreloadData.offer(newMineData());
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(Information.loadGameDataError);
                }
            }
        }
    }
}
