package com.domain;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Mine {
    private final Properties properties = new Properties();
    private MineModel easyModel;
    private MineModel ordinaryModel;
    private MineModel hardModel;
    private MineModel customizeModel;
    private MineModel nowMineModel;

    private Integer[] getData(String modelName) {
        try {
            properties.load(getClass().getResourceAsStream("/properties/mineModelSetting.properties"));
        } catch (IOException e) {
            LoggerFactory.getLogger(Mine.class).error("", e);
            return null;
        }
        final int row = Integer.parseInt(properties.getProperty(modelName + ".row"));
        final int column = Integer.parseInt(properties.getProperty(modelName + ".column"));
        final int mineNumber = Integer.parseInt(properties.getProperty(modelName + ".mineNumber"));
        return new Integer[]{row, column, mineNumber};
    }

    public MineModel getEasyModel() {
        final Integer[] data = getData("easy");
        if (data == null) {
            return null;
        }
        if (easyModel == null ||
            !(easyModel.getRow().equals(data[0]) && easyModel.getColumn().equals(data[1]) && easyModel.getMineNumber().equals(data[2]))) {
            easyModel = new MineModel(data[0], data[1], data[2], "简单");
        }
        return easyModel;
    }

    public MineModel getOrdinaryModel() {
        final Integer[] data = getData("ordinary");
        if (data == null) {
            return null;
        }
        if (ordinaryModel == null ||
            !(ordinaryModel.getRow().equals(data[0]) && ordinaryModel.getColumn().equals(data[1]) && ordinaryModel.getMineNumber().equals(data[2]))) {
            ordinaryModel = new MineModel(data[0], data[1], data[2], "普通");
        }
        return ordinaryModel;
    }

    public MineModel getHardModel() {
        final Integer[] data = getData("hard");
        if (data == null) {
            return null;
        }
        if (hardModel == null ||
            !(hardModel.getRow().equals(data[0]) && hardModel.getColumn().equals(data[1]) && hardModel.getMineNumber().equals(data[2]))) {
            hardModel = new MineModel(data[0], data[1], data[2], "困难");
        }
        return hardModel;
    }

    public MineModel getCustomizeModel() {
        final Integer[] data = getData("customize");
        if (data == null) {
            return null;
        }
        if (customizeModel == null || !(customizeModel.getRow().equals(data[0]) && customizeModel.getColumn().equals(data[1]) &&
                                        customizeModel.getMineNumber().equals(data[2]))) {
            customizeModel = new MineModel(data[0], data[1], data[2], "自定义");
        }
        return customizeModel;
    }

    public void setCustomizeModel(MineModel customizeModel) {
        this.customizeModel = customizeModel;
    }

    public MineModel getNowMineModel() {
        if (nowMineModel == null) {
            nowMineModel = getEasyModel();
        }
        return nowMineModel;
    }

    public void setNowMineModel(MineModel nowMineModel) {
        this.nowMineModel = nowMineModel;
    }

}