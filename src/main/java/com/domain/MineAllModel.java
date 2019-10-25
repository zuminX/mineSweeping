package com.domain;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Properties;

/**
 * 所有地雷模式对象
 */
@SuppressWarnings("all")
public class MineAllModel {
    /**
     * 配置文件对象
     */
    private final Properties properties = new Properties();
    /**
     * 简单模式
     */
    @Getter
    private MineModel easyModel;
    /**
     * 普通模式
     */
    @Getter
    private MineModel ordinaryModel;
    /**
     * 困难模式
     */
    @Getter
    private MineModel hardModel;
    /**
     * 自定义模式
     */
    @Getter
    @Setter
    private MineModel customizeModel;
    /**
     * 当前模式
     */
    @Setter
    private MineModel nowMineModel;

    /**
     * 构造出默认的四个扫雷模式
     */
    public MineAllModel() {
        easyModel = getMineModel("easy", "简单");
        ordinaryModel = getMineModel("ordinary", "普通");
        hardModel = getMineModel("hard", "困难");
        customizeModel = getMineModel("customize", "自定义");
    }

    /**
     * 给定模式名称获取该模式的行数、列数、地雷数
     *
     * @param modelName 模式名称
     *
     * @return 行数、列数、地雷数
     */
    private Integer[] getData(String modelName) {
        //加载失败，返回null
        try {
            properties.load(getClass().getResourceAsStream("/properties/mineModelSetting.properties"));
        } catch (IOException e) {
            return null;
        }
        final int row = Integer.parseInt(properties.getProperty(modelName + ".row"));
        final int column = Integer.parseInt(properties.getProperty(modelName + ".column"));
        final int mineNumber = Integer.parseInt(properties.getProperty(modelName + ".mineNumber"));
        return new Integer[]{row, column, mineNumber};
    }

    /**
     * 无设置则返回简单模式
     * @return 当前扫雷模式
     */
    public MineModel getNowMineModel() {
        //当前模式为空，则默认选择普通模式
        if (nowMineModel == null) {
            nowMineModel = easyModel;
        }
        return nowMineModel;
    }

    /**
     * 根据名字找到配置文件对应的数据创建指定名字的模式
     * @param modelNamePro 该模式在配置文件的名字
     * @param modelName 该模式的名字
     *
     * @return 扫雷模式
     */
    @Nullable
    private MineModel getMineModel(String modelNamePro, String modelName) {
        final Integer[] data = getData(modelNamePro);
        if (data == null) {
            return null;
        }
        return new MineModel(data[0], data[1], data[2], modelName);
    }

}
