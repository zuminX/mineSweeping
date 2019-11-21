package com.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维的点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    /**
     * i的位移
     */
    public static final int[] positionI;
    /**
     * j的位移
     */
    public static final int[] positionJ;

    static {
        //向八个位置进行位移
        positionI = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
        positionJ = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
    }

    /**
     * 第一维的位置
     */
    private int i;
    /**
     * 第二维的位置
     */
    private int j;
}
