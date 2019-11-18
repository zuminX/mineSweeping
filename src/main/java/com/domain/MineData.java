package com.domain;

import com.utils.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 地雷数据
 */
@Data
@AllArgsConstructor
public class MineData {
    /**
     * 地雷二维点的集合
     */
    private Set<Point> minePoint;
    /**
     * 扫雷游戏每一格的数据
     */
    private int[][] data;
}
