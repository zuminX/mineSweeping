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
     * 第一维的位置
     */
    private int i;
    /**
     * 第二维的位置
     */
    private int j;
}
