package com.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ComponentImage {
    public static BufferedImage mineBufferedImage;
    public static BufferedImage flagBufferedImage;
    public static BufferedImage explodeMineBufferedImage;
    public static BufferedImage sweepMineBufferedImage;
    public static BufferedImage[] numberBufferedImage;
    public static ImageIcon confusedImageIcon;
    public static ImageIcon angryImageIcon;
    public static ImageIcon happyImageIcon;

    static {
        try {
            mineBufferedImage = ImageIO.read(new FileInputStream(ComponentImage.class.getResource("/image/mine.png").getPath()));
            flagBufferedImage = ImageIO.read(new FileInputStream(ComponentImage.class.getResource("/image/flag.png").getPath()));
            explodeMineBufferedImage = ImageIO.read(new FileInputStream(ComponentImage.class.getResource("/image/explodeMine.png").getPath()));
            sweepMineBufferedImage = ImageIO.read(new FileInputStream(ComponentImage.class.getResource("/image/sweepMine.png").getPath()));
            numberBufferedImage = new BufferedImage[8];
            for (int i = 0; i < numberBufferedImage.length; i++) {
                numberBufferedImage[i] = ImageIO.read(new FileInputStream(ComponentImage.class.getResource("/image/" + (i + 1) + ".png").getPath()));
            }
            confusedImageIcon = new ImageIcon(ComponentImage.class.getResource("/image/confused.png"));
            angryImageIcon = new ImageIcon(ComponentImage.class.getResource("/image/angry.png"));
            happyImageIcon = new ImageIcon(ComponentImage.class.getResource("/image/happy.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getGameImageIcon(BufferedImage bufferedImage, JButton button) {
        return new ImageIcon(bufferedImage.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_DEFAULT));
    }
}
