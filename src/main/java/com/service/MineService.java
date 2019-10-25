package com.service;

import com.domain.MineModel;
import com.domain.Point;

/**
 * 扫雷业务层的接口
 * 接收控制层的数据
 * 调用dao层获得扫雷数据
 * 返回数据给控制层
 */
public interface MineService {
    /**
     * 根据名称改变当前扫雷模式
     *
     * @param modelName 模式名称
     *
     * @return false 没有发生异常；null 发生异常
     */
    Boolean changeModel(String modelName);

    /**
     * 获取当前扫雷模式
     *
     * @return 当前模式
     */
    MineModel getNowMineModel();

    /**
     * 创建一个扫雷视图按钮组
     */
    void newMineViewButtons();

    /**
     * 向扫雷地图添加地雷数据
     *
     * @param point 点
     */
    void fillMineData(Point point);

    /**
     * 保存设置的数据
     *
     * @return false 没有发生异常；null 发生异常
     */
    Boolean saveSettingData();
}
