package com.duckbot.core;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardAutomation {

    public static void type(String text) {
        try {
            Robot robot = new Robot();
            for (char c : text.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.delay(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
