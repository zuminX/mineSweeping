package com.dao;

import com.domain.MineModel;

import java.io.IOException;

/**
 * 扫雷dao层的接口
 * 读取扫雷文件数据
 * 返回数据给业务层
 */
public interface GamePropertiesDao {
    /**
     * 根据参数创建一个地雷模式对象
     *
     * @param rowStr        行数字符串
     * @param columnStr     列数字符串
     * @param mineNumberStr 地雷数字符串
     * @return 地雷模式对象
     * @throws IOException I/O流异常
     */
    MineModel findCustomizeModelByData(String rowStr, String columnStr, String mineNumberStr) throws IOException;

    /**
     * 对配置文件进行更新自定义模式的数据
     *
     * @param mineModel 地雷模式对象
     * @throws IOException I/O流异常
     */
    void updateCustomizeModelInProperties(MineModel mineModel) throws IOException;

    /**
     * 改变游戏名
     *
     * @param name 游戏名
     * @throws IOException I/O流异常
     */
    void changeGameName(String name) throws IOException;

    /**
     * 获取当前游戏名
     *
     * @return 当前游戏名
     * @throws IOException I/O流异常
     */
    String findNowGameName() throws IOException;

    /**
     * 获取当前是否开启记录数据
     *
     * @return 开启记录->true 未开启记录->false
     * @throws IOException I/O流异常
     */
    boolean findNowOpenRecordStatus() throws IOException;

    /**
     * 改变是否记录数据的状态
     *
     * @param isSelected 是否选择了开启记录
     * @throws IOException I/O流异常
     */
    void changeOpenRecordStatus(boolean isSelected) throws IOException;
}
