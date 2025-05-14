package com.duckbot.core;

import net.sourceforge.tess4j.*;

import java.io.File;

public class OCRUtility {

    public static String solveCaptcha(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            ITesseract instance = new Tesseract();
            instance.setLanguage("eng");
            return instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
            return "";
        }
    }
}
