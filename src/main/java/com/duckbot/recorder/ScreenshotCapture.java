package com.duckbot.recorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotCapture {
    public static String captureAndCrop(String emulatorId, File outputDir, JFrame parent) throws IOException, InterruptedException {
        String name = JOptionPane.showInputDialog(parent, "Enter image file name (e.g., btn_ok.png):", "Capture Screenshot", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) return null;

        File raw = new File(outputDir, "__full_tmp.png");
        File finalFile = new File(outputDir, name);

        Process screencap = new ProcessBuilder("adb", "-s", emulatorId, "shell", "screencap", "-p", "/sdcard/screen.png").start();
        screencap.waitFor();
        Process pull = new ProcessBuilder("adb", "-s", emulatorId, "pull", "/sdcard/screen.png", raw.getAbsolutePath()).start();
        pull.waitFor();

        BufferedImage full = ImageIO.read(raw);
        Rectangle selection = showCropSelector(full, parent);
        if (selection == null) return null;

        BufferedImage cropped = full.getSubimage(selection.x, selection.y, selection.width, selection.height);
        ImageIO.write(cropped, "png", finalFile);
        raw.delete();
        return finalFile.getPath().replace("\\", "/");
    }

    private static Rectangle showCropSelector(BufferedImage image, JFrame parent) {
        final Rectangle[] result = new Rectangle[1];
        JDialog dialog = new JDialog(parent, "Crop Image", true);
        dialog.setSize(image.getWidth(), image.getHeight());
        dialog.setLocationRelativeTo(parent);

        JLabel label = new JLabel(new ImageIcon(image));
        JPanel panel = new JPanel(null) {
            Point start, end;
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) { start = e.getPoint(); }
                    public void mouseReleased(MouseEvent e) {
                        end = e.getPoint();
                        int x = Math.min(start.x, end.x);
                        int y = Math.min(start.y, end.y);
                        int w = Math.abs(start.x - end.x);
                        int h = Math.abs(start.y - end.y);
                        result[0] = new Rectangle(x, y, w, h);
                        dialog.dispose();
                    }
                });
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (start != null && end != null) {
                    int x = Math.min(start.x, end.x);
                    int y = Math.min(start.y, end.y);
                    int w = Math.abs(start.x - end.x);
                    int h = Math.abs(start.y - end.y);
                    g.setColor(new Color(0, 0, 255, 80));
                    g.fillRect(x, y, w, h);
                }
            }
        };
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        panel.add(label);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setVisible(true);
        return result[0];
    }
}