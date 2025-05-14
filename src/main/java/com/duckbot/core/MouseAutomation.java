package com.duckbot.core;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseAutomation {

    public static void click(int x, int y) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
