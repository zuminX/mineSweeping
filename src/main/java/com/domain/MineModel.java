package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装扫雷模式的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MineModel {
    /**
     * 行数
     */
    private int row;
    /**
     * 列数
     */
    private int column;
    /**
     * 地雷个数
     */
    private int mineNumber;
    /**
     * 模式名称
     */
    private String name;

    /**
     * 获取该模式地雷的密度
     *
     * @return 密度
     */
    public double getMineDensity() {
        return (double) mineNumber / (row * column);
    }
}
