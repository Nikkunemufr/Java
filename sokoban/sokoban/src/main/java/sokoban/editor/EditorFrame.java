package sokoban.editor;

import sokoban.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class EditorFrame extends JPanel implements Observer {

    private LevelEditor level;
    private GridBagConstraints gbc;
    private String theme = "theme0";


    public EditorFrame(LevelEditor level) {
        this.level = level;

        /* Add the EditorFrame as an sokoban.observer of the game */
        level.addObserver(this);

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        /* Make the component fill its display area entirely */
        gbc.fill = GridBagConstraints.BOTH;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JPanel square = (JPanel) getComponentAt(e.getPoint());
                level.insertSquare(level.getType(), square.getY() / 45, square.getX() / 45);
            }
        });

    }

    /**
     * Updates this board sokoban.view.
     */
    @Override
    public void update(Object obj) {
        displayFrame();
        updateUI();  // JPanel method
    }

    public void displayFrame() {

        /* Remove the elements of the board to display the refreshed again */
        removeAll();
        for (int row = 0; row < level.getHeight(); row++) {
            gbc.gridy = row;
            for (int col = 0; col < level.getWidth(); col++) {
                gbc.gridx = col;
                try {
                    char element = level.getGame().get(row).charAt(col);
                    char elementImage = '0';
                    switch (element) {
                        case ' ':
                            elementImage = '0';
                            break;
                        case '#':
                            elementImage = '1';
                            break;
                        case '$':
                            elementImage = '2';
                            break;
                        case '@':
                            elementImage = '3';
                            break;
                        case '.':
                            elementImage = '4';
                            break;
                        case '*':
                            elementImage = '5';
                            break;
                        case '+':
                            elementImage = '6';
                            break;

                    }

                    String imagePath = "src/main/resources/images/" + theme + "/" + elementImage + ".png";
                    BufferedImage image = ImageIO.read(new File(imagePath));
                    JLabel imageLabel = new JLabel(new ImageIcon(image));
                    JPanel panel = new JPanel();
                    panel.add(imageLabel);
                    add(panel, gbc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
