package sokoban.gui;

import sokoban.model.Game;
import sokoban.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Board representation of the Sokoban game.
 */
public class Board extends JPanel implements Observer {

    /**
     * The game to be represented.
     */
    private Game game;

    /**
     * Grid constraints.
     */
    private GridBagConstraints gbc;

    /**
     * Repertory theme name.
     */
    private String theme = "theme0";

    /**
     * Controller for this Board.
     */
    private GuiController controller;

    /**
     * Constructs a new board.
     *
     * @param game The game to be represented
     */
    public Board(Game game) {
        this.game = game;

        /* Add the Board as an sokoban.observer of the game */
        game.addObserver(this);

        /* Key listener definition */
        controller = new GuiController(game);

        /* Listen the keyboard */
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(controller);

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        /* Make the component fill its display area entirely */
        gbc.fill = GridBagConstraints.BOTH;

        //setSize(imageSize * game.getWidth(), imageSize * game.getHeight());
    }

    /**
     * Updates this board sokoban.view.
     */
    @Override
    public void update(Object obj) {
        displayBoard((Game) obj);
        updateUI();  // JPanel method

        if (game.hasEnded()) {

            /* Delete keyboard entry */
            this.removeKeyListener(controller);
            System.out.println("Congratulations Billy, you won!");
        }
    }

    /**
     * Displays the board of the game.
     *
     * @param game The game to be dispayed
     */
    public void displayBoard(Game game) {

        /* Remove the elements of the board to display the refreshed again */
        removeAll();
        int lineLength = game.level.getWidth();
        for (int row = 0; row < game.level.getHeight(); row++) {
            gbc.gridy = row;
            for (int col = 0; col < lineLength; col++) {
                gbc.gridx = col;
                //Component toRemove = getComponent(col + row * lineLength + 1);
                //remove(toRemove);
                try {
                    char element = game.board[row][col].getImage();
                    String imagePath = "src/main/resources/images/" + theme + "/" + element + ".png";
                    BufferedImage image = ImageIO.read(new File(imagePath));
                    JLabel imageLabel = new JLabel(new ImageIcon(image));
                    add(imageLabel, gbc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
