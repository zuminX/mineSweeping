package com.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ButtonImage {
    public static BufferedImage mineBufferedImage;
    public static BufferedImage flagBufferedImage;
    public static BufferedImage explodeMineBufferedImage;
    public static BufferedImage sweepMineBufferedImage;
    public static BufferedImage[] numberBufferedImage;

    static {
        try {
            mineBufferedImage = ImageIO.read(new FileInputStream(ButtonImage.class.getResource("/image/mine.png").getPath()));
            flagBufferedImage = ImageIO.read(new FileInputStream(ButtonImage.class.getResource("/image/flag.png").getPath()));
            explodeMineBufferedImage = ImageIO.read(new FileInputStream(ButtonImage.class.getResource("/image/explodeMine.png").getPath()));
            sweepMineBufferedImage = ImageIO.read(new FileInputStream(ButtonImage.class.getResource("/image/sweepMine.png").getPath()));
            numberBufferedImage = new BufferedImage[8];
            for (int i = 0; i < numberBufferedImage.length; i++) {
                numberBufferedImage[i] = ImageIO.read(new FileInputStream(ButtonImage.class.getResource("/image/"+(i+1)+".png").getPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getGameImageIcon(BufferedImage bufferedImage, JButton button) {
        return new ImageIcon(bufferedImage.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_DEFAULT));
    }
}
