package com.dao.impl;

import com.dao.MineDao;
import com.domain.MineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Repository("mineDao")
public class MineDaoImpl implements MineDao {

    @Autowired
    private Properties properties;

    @Value("/properties/mineBasicSetting.properties")
    private String mineBasicSettingPath;

    @Value("/properties/playerDataSetting.properties")
    private String playerDataSettingPath;

    @Override
    public MineModel findCustomizeModelByData(String rowStr, String columnStr, String mineNumberStr) throws IOException {
        int row = Integer.parseInt(rowStr);
        int column = Integer.parseInt(columnStr);
        int mineNumber = Integer.parseInt(mineNumberStr);
        double mineDensity = (double) mineNumber / (row * column);

        properties.load(getClass().getResourceAsStream(mineBasicSettingPath));

        int maxRow = Integer.parseInt(properties.getProperty("model.maxRow"));
        int maxColumn = Integer.parseInt(properties.getProperty("model.maxColumn"));
        double maxMineDensity = Double.parseDouble(properties.getProperty("model.maxMineDensity"));

        if ((row > 0 && row <= maxRow) && (column > 0 && column <= maxColumn) && (mineNumber > 0 && mineDensity <= maxMineDensity)) {
            return new MineModel(row, column, mineNumber, "自定义");
        }
        return null;
    }

    @Override
    public void updateCustomizeModelInProperties(MineModel mineModel) throws IOException {
        properties.load(getClass().getResourceAsStream(mineBasicSettingPath));

        properties.setProperty("customize.row", String.valueOf(mineModel.getRow()));
        properties.setProperty("customize.column", String.valueOf(mineModel.getColumn()));
        properties.setProperty("customize.mineNumber", String.valueOf(mineModel.getMineNumber()));

        properties.store(new FileOutputStream(getClass().getResource(mineBasicSettingPath).getPath()), "");
    }

    @Override
    public void changeGameName(String name) throws IOException {
        properties.load(getClass().getResourceAsStream(playerDataSettingPath));

        properties.setProperty("game.name", name);

        properties.store(new FileOutputStream(getClass().getResource(playerDataSettingPath).getPath()), "");
    }

    @Override
    public String findNowGameName() throws IOException {
        properties.load(getClass().getResourceAsStream(playerDataSettingPath));

        return properties.getProperty("game.name");
    }

    @Override
    public boolean findNowOpenRecordStatus() throws IOException {
        properties.load(getClass().getResourceAsStream(playerDataSettingPath));

        return properties.getProperty("game.openRecord").equals("1");
    }

    @Override
    public void changeOpenRecordStatus(boolean isSelected) throws IOException {
        properties.load(getClass().getResourceAsStream(playerDataSettingPath));

        String oldOpenRecordStatus = properties.getProperty("game.openRecord");

        properties.setProperty("game.openRecord", isSelected?"1":"0");

        properties.store(new FileOutputStream(getClass().getResource(playerDataSettingPath).getPath()), "");
    }
}
