package com.duckbot.recorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class RecorderPanel extends JPanel {
    private final JComboBox<String> gameSelector = new JComboBox<>(new String[]{"WestGame", "FarmSim", "Other"});
    private final JTextField imageNameField = new JTextField("button_", 12);
    private int imageCounter = 1;

    public RecorderPanel() {
        setLayout(new BorderLayout());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton captureBtn = new JButton("Capture Screenshot");

        controls.add(new JLabel("Game:"));
        controls.add(gameSelector);
        controls.add(new JLabel("Image Name:"));
        controls.add(imageNameField);
        controls.add(captureBtn);

        captureBtn.addActionListener(this::onCaptureImage);

        add(controls, BorderLayout.NORTH);
    }

    private void onCaptureImage(ActionEvent e) {
        String game = (String) gameSelector.getSelectedItem();
        String baseName = imageNameField.getText().trim();
        if (baseName.isEmpty()) baseName = "image";

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = baseName + "_" + timestamp + ".png";

        File folder = new File("scripts/" + game + "/images/");
        if (!folder.exists()) folder.mkdirs();

        File outFile = new File(folder, filename);

        try {
            // Simulate screenshot capture (replace with real screenshot logic)
            BufferedImage dummyImage = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = dummyImage.createGraphics();
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(0, 0, 200, 100);
            g2d.setColor(Color.WHITE);
            g2d.drawString("Simulated Screenshot", 30, 50);
            g2d.dispose();

            ImageIO.write(dummyImage, "png", outFile);

            JOptionPane.showMessageDialog(this, "Image saved to:\n" + outFile.getAbsolutePath(),
                    "Capture Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save image: " + ex.getMessage(),
                    "Capture Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
