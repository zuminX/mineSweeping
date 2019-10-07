package com.service.impl;

import com.dao.MineDao;
import com.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.MineService;

import javax.swing.*;
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

    private Mine mine = new Mine();
    private Preload preload = new Preload();

    private Queue<MineData> minePreloadData = new ConcurrentLinkedQueue<>();

    @Override
    public String changeModel(JTextField rowTextField, JTextField columnTextField, JTextField mineNumberTextField, JTextField mineDensityTextField,
                              String modelName) {
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
            return "加载配置扫雷游戏模式设置时失败";
        }
        mine.setNowMineModel(model);
        return null;
    }

    @Override
    public MineModel getNowMineModel() {
        return mine.getNowMineModel();
    }

    @Override
    public String updateCustomizeData(String[] customizeData) {
        final MineModel customizeModelByData;
        try {
            customizeModelByData = mineDao.findCustomizeModelByData(customizeData[0], customizeData[1], customizeData[2]);
        } catch (IOException e) {
            throw new RuntimeException("");
        }
        if (customizeModelByData == null) {
            return "自定义扫雷数据失败，请检查数据的合法性";
        }
        mine.setCustomizeModel(customizeModelByData);
        try {
            mineDao.updateCustomizeModelInProperties(customizeModelByData);
        } catch (IOException e) {
            throw new RuntimeException("");
        }
        return null;
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
    public void preloadMineData() {
        new Thread(preload).start();
    }

    @Override
    public void changeGameName(String name) {
        if (name.trim().equals("")) {
            throw new RuntimeException("");
        }
        try {
            mineDao.changeGameName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOpenRecordStatus() {
        try {
            mineDao.changeOpenRecordStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            synchronized (MineServiceImpl.class) {
                final MineModel nowMineModel = getNowMineModel();
                if (!nowMineModel.equals(oldMineModel)) {
                    minePreloadData = new ConcurrentLinkedQueue<>();
                    oldMineModel = nowMineModel;
                }
                while (minePreloadData.size() < 10) {
                    minePreloadData.offer(newMineData());
                }
            }
        }
    }
}
