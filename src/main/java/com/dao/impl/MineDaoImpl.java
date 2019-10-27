package com.dao.impl;

import com.dao.MineDao;
import com.domain.MineModel;
import com.utils.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 扫雷dao层
 * 读取扫雷文件数据
 * 返回数据给业务层
 */
@Repository("mineDao")
@SuppressWarnings("all")
public class MineDaoImpl implements MineDao {
    /**
     * 配置文件对象
     */
    @Autowired
    private Properties properties;
    /**
     * 地雷的基础设置配置文件的路径
     */
    @Value("/properties/mineBasicSetting.properties")
    private String mineBasicSettingPath;
    /**
     * 玩家数据设置配置文件的路径
     */
    @Value("/properties/playerDataSetting.properties")
    private String playerDataSettingPath;
    /**
     * 地雷模式设置配置文件的路径
     */
    @Value("/properties/mineModelSetting.properties")
    private String mineModelSettingPath;

    /**
     * 根据参数创建一个地雷模式对象
     *
     * @param rowStr        行数字符串
     * @param columnStr     列数字符串
     * @param mineNumberStr 地雷数字符串
     *
     * @return 地雷模式对象
     *
     * @throws IOException I/O流异常
     */
    @Override
    public MineModel findCustomizeModelByData(String rowStr, String columnStr, String mineNumberStr) throws IOException {
        int row = Integer.parseInt(rowStr);
        int column = Integer.parseInt(columnStr);

        //行数或列数等于零，抛出异常
        if (row == 0 || column == 0) {
            throw new RuntimeException(Information.changeCustomizeDataError);
        }

        int mineNumber = Integer.parseInt(mineNumberStr);
        double mineDensity = (double) mineNumber / (row * column);

        getLoad(mineBasicSettingPath);

        //获取自定义模式的限制
        int maxRow = Integer.parseInt(properties.getProperty("model.maxRow"));
        int maxColumn = Integer.parseInt(properties.getProperty("model.maxColumn"));
        double maxMineDensity = Double.parseDouble(properties.getProperty("model.maxMineDensity"));

        //若符合限制条件，则创建该模式对象
        if ((row > 0 && row <= maxRow) && (column > 0 && column <= maxColumn) && (mineNumber > 0 && mineDensity <= maxMineDensity)) {
            return new MineModel(row, column, mineNumber, "自定义");
        }
        return null;
    }

    /**
     * 对配置文件进行更新自定义模式的数据
     *
     * @param mineModel 地雷模式对象
     *
     * @throws IOException I/O流异常
     */
    @Override
    public void updateCustomizeModelInProperties(MineModel mineModel) throws IOException {
        getLoad(mineModelSettingPath);

        properties.setProperty("customize.row", String.valueOf(mineModel.getRow()));
        properties.setProperty("customize.column", String.valueOf(mineModel.getColumn()));
        properties.setProperty("customize.mineNumber", String.valueOf(mineModel.getMineNumber()));

        getStore(mineModelSettingPath);
    }

    /**
     * 改变游戏名
     *
     * @param name 游戏名
     *
     * @throws IOException I/O流异常
     */
    @Override
    public void changeGameName(String name) throws IOException {
        getLoad(playerDataSettingPath);

        properties.setProperty("game.name", name);

        getStore(playerDataSettingPath);
    }

    /**
     * 获取当前游戏名
     *
     * @return 当前游戏名
     *
     * @throws IOException I/O流异常
     */
    @Override
    public String findNowGameName() throws IOException {
        getLoad(playerDataSettingPath);

        return properties.getProperty("game.name");
    }

    /**
     * 获取当前是否开启记录数据
     *
     * @return 开启记录->true 未开启记录->false
     *
     * @throws IOException I/O流异常
     */
    @Override
    public boolean findNowOpenRecordStatus() throws IOException {
        getLoad(playerDataSettingPath);

        return properties.getProperty("game.openRecord").equals("1");
    }

    /**
     * 改变是否记录数据的状态
     *
     * @param isSelected 是否选择了开启记录
     *
     * @throws IOException I/O流异常
     */
    @Override
    public void changeOpenRecordStatus(boolean isSelected) throws IOException {
        getLoad(playerDataSettingPath);

        properties.setProperty("game.openRecord", isSelected ? "1" : "0");

        getStore(playerDataSettingPath);
    }

    /**
     * 保存配置文件
     *
     * @param path 配置文件路径
     *
     * @throws IOException I/O流异常
     */
    private void getStore(String path) throws IOException {
        properties.store(new FileOutputStream(getClass().getResource(path).getPath()), "");
    }

    /**
     * 加载配置文件
     *
     * @param path 配置文件路径
     *
     * @throws IOException I/O流异常
     */
    private void getLoad(String path) throws IOException {
        properties.load(getClass().getResourceAsStream(path));
    }
}
