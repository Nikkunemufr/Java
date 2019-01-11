package sokoban.launcher;

import sokoban.editor.EditorView;
import sokoban.editor.LevelEditor;
import sokoban.gui.GUI;
import sokoban.gui.SokButton;
import sokoban.model.Game;
import sokoban.model.LevelLoader;

import javax.swing.*;
import java.awt.*;

/**
 * Launcher for Sokoban game.
 */
public class Launcher extends JFrame {
    /**
     * The main panel.
     */
    private BackgroundPanel master = new BackgroundPanel("/images/background_menu_dog.png");
    /**
     * Panel for the buttonsPanel.
     */
    private JPanel buttonsPanel = new JPanel();
    private FlowLayout defaultLayout = new FlowLayout(FlowLayout.CENTER, 5, 5);
    /**
     * Number of levels.
     */
    private static final int NUMBER_OF_LEVELS = 10;

    /**
     * Constructs a new Launcher.
     */
    public Launcher() {
        /* Title of the frame */
        setTitle("Sokoban - Menu");
        /* Size of the frame */
        setSize(650, 400);
        /* The program stops when the cross is clicked */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* The frame is resizable or not */
        setResizable(false);
        /* Position of the frame in the screen */
        setLocationRelativeTo(null);  // centering the frame
        /* Set master layout to GridBagLayout for vertical centering */
        master.setLayout(new GridBagLayout());
        /* Add buttonsPanel */
        initButtons();
        setContentPane(master);
    }

    /**
     * (Re)-initialises buttons panel.
     */
    private void initButtonsPanel() {
        buttonsPanel.removeAll();
        buttonsPanel.revalidate();
        buttonsPanel.setLayout(defaultLayout);
    }

    /**
     * Adds buttonsPanel to the main panel.
     */
    private void initButtons() {
        initButtonsPanel();
        buttonsPanel.setOpaque(false); // background transparency
        buttonsPanel.add(new SokButton("New Game", e -> newGame()));
        buttonsPanel.add(new SokButton("Load", e -> load()));
        buttonsPanel.add(new SokButton("Level Editor", e -> levelEditor()));
        buttonsPanel.add(new SokButton("How to Play", e -> howToPlay()));
        master.add(buttonsPanel);
    }

    /**
     * Sets the buttons for loading a level or a game.
     */
    private void load() {
        initButtonsPanel();
        buttonsPanel.add(new SokButton("Load Level", e -> loadLevel()));
        buttonsPanel.add(new SokButton("Load Game", e -> singlePlayer()));
        buttonsPanel.add(new SokButton("Return", e -> initButtons()));
    }

    /**
     * Sets the buttons
     */
    private void loadLevel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        buttonsPanel.removeAll();
        buttonsPanel.revalidate();
        buttonsPanel.setLayout(new GridBagLayout());
        for (int n = 1; n <= NUMBER_OF_LEVELS; n++) {
            String levelPath = "src/main/resources/levels/level" + n + ".sok";
            gbc.gridx = (n - 1) % (NUMBER_OF_LEVELS / 2);
            gbc.gridy = (n - 1) / (NUMBER_OF_LEVELS / 2);
            SokButton sokButton = new SokButton("Level " + n, e -> singlePlayer(levelPath));
            buttonsPanel.add(sokButton, gbc);
        }
        gbc.gridx = 2;
        gbc.gridy = 2;
        buttonsPanel.add(new SokButton("Return", e -> load()), gbc);
    }

    /**
     * Sets the necessary buttons for launching a new game.
     */
    private void newGame() {
        initButtonsPanel();
        buttonsPanel.add(new SokButton("Single Player", e -> singlePlayer()));
        buttonsPanel.add(new SokButton("Player vs. AI", e -> singlePlayer()));
        buttonsPanel.add(new SokButton("Return", e -> initButtons()));
    }

    /**
     * Launches a single player game.
     */
    private void singlePlayer() {
        singlePlayer("src/main/resources/levels/level1.sok");
    }

    /**
     * Launches a single player game with a determined level.
     */
    private void singlePlayer(String levelPath) {
        LevelLoader level = new LevelLoader();
        level.loadLevel(levelPath);
        Game game = new Game(level);
        GUI gui = new GUI(game);
        gui.setVisible(true);
        dispose();
    }

    /**
     * Launches a level editor.
     */
    private void levelEditor() {
        new EditorView(new LevelEditor()).setVisible(true);
    }

    /**
     * Launches rules of the game
     */
    private void howToPlay() {
        Rules howToPlay = new Rules();
        howToPlay.setVisible(true);
    }
}