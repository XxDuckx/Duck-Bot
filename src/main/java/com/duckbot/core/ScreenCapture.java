package com.duckbot.core;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ScreenCapture {
    public static void captureScreenshot(String fileName) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenImage, "PNG", new File(fileName));
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
