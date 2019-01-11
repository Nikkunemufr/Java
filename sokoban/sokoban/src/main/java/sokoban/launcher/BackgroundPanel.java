package sokoban.launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A JPanel with a background image.
 */
public class BackgroundPanel extends JPanel {

    /**
     * The image to be painted.
     */
    private BufferedImage bgImage;

    /**
     * Constructs a JPanel and insert a background image in it.
     */
    public BackgroundPanel(String imagePath) {
        try {
            bgImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }

}
