package sokoban.gui;

import javax.swing.*;

import sokoban.editor.EditorView;
import sokoban.editor.LevelEditor;
import sokoban.launcher.Launcher;
import sokoban.model.Game;
import sokoban.model.LevelLoader;

import java.awt.*;
import java.time.LocalDateTime;

/**
 * Graphical user interface for Sokoban game.
 */
public class GUI extends JFrame {

    /**
     * The current game.
     */
    private Game game;

    /**
     * The master panel.
     */
    private JPanel master = new JPanel();

    /**
     * The panel for the buttons.
     */
    private JPanel buttons = new JPanel(new GridBagLayout());

    /**
     * Constraints for the buttons.
     */
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Constructs a new graphical user interface.
     *
     * @param game The game to be displayed
     */
    public GUI(Game game) {
        this.game = game;

        /* Title of the frame */
        setTitle("Sokoban");

        /* Size of the frame */
        int frameWidth = game.getBoard().length;
        setSize(1000, 1000);

        /* The program disposes when the cross is clicked */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* The frame is resizable or not */
        setResizable(false);

        /* Position of the frame in the screen */
        setLocationRelativeTo(null);  // centering

        /* Add the button on the master */
        setContentPane(master);

        /* Set the layout of the JFrame and the master pane */
        setLayout(null);
        master.setLayout(new BorderLayout());

        /* Board sokoban.view (JPanel) */
        Board board = new Board(game);
        board.displayBoard(game);

        /* Add buttons */
        addButtons();

        master.add(board, BorderLayout.WEST);
    }

    /**
     * Adds buttons to the master panel.
     */
    private void addButtons() {
        gbc.insets = new Insets(10, 0, 5,50);

        JButton returnMenu = new SokButton("Return to Menu", e -> returnMenu());
        gbc.gridy = 1;
        buttons.add(returnMenu, gbc);

        JButton saveGame = new SokButton("Save", e -> saveGame());
        gbc.gridy = 2;
        buttons.add(saveGame, gbc);

        JButton editLevel = new SokButton("Edit Level", e -> editLevel());
        gbc.gridy = 3;
        buttons.add(editLevel, gbc);

        master.add(buttons, BorderLayout.EAST);
    }

    /**
     * Makes a return to the launcher.
     */
    private void returnMenu() {
        new Launcher().setVisible(true);
        dispose();
    }

    /**
     * Saves the current game.
     */
    private void saveGame() {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();
        String levelName = "Save_of_" + year + "-" + month + "-" + day + "_"
                + hour + "." + minute + "." + second;
        LevelLoader.saveLevel(game, levelName);
    }

    /**
     * Edits the current game.
     */
    private void editLevel() {
        new EditorView(new LevelEditor()).setVisible(true);
    }
}
