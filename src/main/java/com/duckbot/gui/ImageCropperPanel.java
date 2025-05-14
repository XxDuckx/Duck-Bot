package com.duckbot.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCropperPanel extends JPanel {
    private final JComboBox<String> gameSelector = new JComboBox<>(new String[]{"WestGame", "FarmSim", "Other"});
    private final JLabel imageLabel = new JLabel();
    private BufferedImage loadedImage;
    private Rectangle cropRect;
    private Point startDrag;

    public ImageCropperPanel() {
        setLayout(new BorderLayout());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadBtn = new JButton("Load Image");
        JButton saveCropBtn = new JButton("Save Crop");
        JTextField filenameField = new JTextField("cropped_", 12);

        controls.add(new JLabel("Game:"));
        controls.add(gameSelector);
        controls.add(loadBtn);
        controls.add(new JLabel("Name:"));
        controls.add(filenameField);
        controls.add(saveCropBtn);
        add(controls, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);

        loadBtn.addActionListener(e -> onLoadImage());
        saveCropBtn.addActionListener(e -> {
            if (loadedImage == null || cropRect == null) return;
            String game = (String) gameSelector.getSelectedItem();
            String name = filenameField.getText().trim();
            if (name.isEmpty()) name = "cropped";

            File outDir = new File("scripts/" + game + "/images/");
            outDir.mkdirs();

            try {
                BufferedImage cropped = loadedImage.getSubimage(
                        cropRect.x, cropRect.y, cropRect.width, cropRect.height);
                File outFile = new File(outDir, name + ".png");
                ImageIO.write(cropped, "png", outFile);
                JOptionPane.showMessageDialog(this, "Saved cropped image to:\n" + outFile.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Crop failed: " + ex.getMessage());
            }
        });

        // Crop interaction
        imageLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (loadedImage != null) startDrag = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                if (loadedImage != null && startDrag != null) {
                    int x = Math.min(startDrag.x, e.getX());
                    int y = Math.min(startDrag.y, e.getY());
                    int w = Math.abs(e.getX() - startDrag.x);
                    int h = Math.abs(e.getY() - startDrag.y);
                    cropRect = new Rectangle(x, y, w, h);
                    repaint();
                }
            }
        });

        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (loadedImage != null && startDrag != null) {
                    int x = Math.min(startDrag.x, e.getX());
                    int y = Math.min(startDrag.y, e.getY());
                    int w = Math.abs(e.getX() - startDrag.x);
                    int h = Math.abs(e.getY() - startDrag.y);
                    cropRect = new Rectangle(x, y, w, h);
                    repaint();
                }
            }
        });
    }

    private void onLoadImage() {
        String game = (String) gameSelector.getSelectedItem();
        JFileChooser chooser = new JFileChooser(new File("scripts/" + game + "/images/"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                loadedImage = ImageIO.read(chooser.getSelectedFile());
                imageLabel.setIcon(new ImageIcon(loadedImage));
                cropRect = null;
                imageLabel.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load image: " + ex.getMessage());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (loadedImage != null && cropRect != null) {
            Graphics2D g2 = (Graphics2D) imageLabel.getGraphics();
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2));
            g2.draw(cropRect);
        }
    }
}
