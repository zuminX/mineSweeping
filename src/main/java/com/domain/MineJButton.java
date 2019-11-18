package com.domain;

import com.utils.BaseHolder;
import com.utils.Point;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 扫雷游戏的按钮
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MineJButton extends JButton {
    /**
     * 隐藏
     */
    public static final int HIDE_SPACE = 0;
    /**
     * 显示空白区块
     */
    public static final int DISPLAY_SPACE = 1;
    /**
     * 显示地雷
     */
    public static final int MINE = 2;
    /**
     * 显示爆炸地雷
     */
    public static final int EXPLODE = 3;
    /**
     * 状态
     */
    private int status;
    /**
     * 代表周围有多个雷（自身是雷则为-1）
     */
    private int data;
    /**
     * 是否为旗帜
     */
    private boolean isFlag;
    /**
     * 位置信息
     */
    private Point point;
    /**
     * 按钮图片
     */
    private BufferedImage bufferedImage;

    /**
     * 设置默认的大小
     */
    public MineJButton() {
        super();
        setSize(BaseHolder.getBean("dimension", Dimension.class));
    }
}
