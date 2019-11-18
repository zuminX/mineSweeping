package com.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 组件的图片
 */
public class ComponentImage {
    /**
     * 地雷图片
     */
    public static BufferedImage mineBufferedImage;
    /**
     * 旗帜图片
     */
    public static BufferedImage flagBufferedImage;
    /**
     * 爆炸地雷图片
     */
    public static BufferedImage explodeMineBufferedImage;
    /**
     * 将雷扫除的图片
     */
    public static BufferedImage sweepMineBufferedImage;
    /**
     * 显示方块的图片
     */
    public static BufferedImage displaySpaceMineBufferedImage;
    /**
     * 隐藏方块的图片
     */
    public static BufferedImage hideSpaceMineBufferedImage;
    /**
     * 数字图片
     */
    public static BufferedImage[] numberBufferedImage;
    /**
     * 默认表情
     */
    public static BufferedImage confusedBufferedImage;
    /**
     * 游戏失败的表情
     */
    public static BufferedImage angryBufferedImage;
    /**
     * 游戏胜利的表情
     */
    public static BufferedImage happyBufferedImage;

    //加载图片
    static {
        try {
            mineBufferedImage = getIconBufferImage("/image/mine.png");
            flagBufferedImage = getIconBufferImage("/image/flag.png");
            explodeMineBufferedImage = getIconBufferImage("/image/explodeMine.png");
            sweepMineBufferedImage = getIconBufferImage("/image/sweepMine.png");
            displaySpaceMineBufferedImage = getIconBufferImage("/image/displaySpace.png");
            hideSpaceMineBufferedImage = getIconBufferImage("/image/hideSpace.png");
            numberBufferedImage = new BufferedImage[8];
            for (int i = 0; i < numberBufferedImage.length; i++) {
                numberBufferedImage[i] = getIconBufferImage("/image/" + (i + 1) + ".png");
            }
            confusedBufferedImage = getIconBufferImage("/image/confused.png");
            angryBufferedImage = getIconBufferImage("/image/angry.png");
            happyBufferedImage = getIconBufferImage("/image/happy.png");
        } catch (IOException e) {
            //若有异常则抛出初始化异常的错误
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 获取指定文件路径下的缓冲图像
     *
     * @param path 文件路径
     * @return 缓冲图像
     * @throws IOException I/O流异常
     */
    private static BufferedImage getIconBufferImage(String path) throws IOException {
        return ImageIO.read(new FileInputStream(ComponentImage.class.getResource(path).getPath()));
    }

    /**
     * 获取填满该组件大小的图像
     *
     * @param bufferedImage 缓冲图像
     * @param jComponent    组件
     * @return 图像
     */
    public static ImageIcon getGameImageIcon(BufferedImage bufferedImage, JComponent jComponent) {
        return new ImageIcon(bufferedImage.getScaledInstance(jComponent.getWidth(), jComponent.getHeight(), Image.SCALE_DEFAULT));
    }

    /**
     * 获取指定大小的图像
     *
     * @param bufferedImage 缓冲图像
     * @param dimension     大小
     * @return 图像
     */
    public static ImageIcon getGameImageIcon(BufferedImage bufferedImage, Dimension dimension) {
        return new ImageIcon(bufferedImage.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), Image.SCALE_DEFAULT));
    }

}
