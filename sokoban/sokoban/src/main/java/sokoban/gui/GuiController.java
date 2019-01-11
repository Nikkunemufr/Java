package sokoban.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import sokoban.util.AbstractController;
import sokoban.model.Game;

public class GuiController extends AbstractController implements KeyListener {

    Game game;

    public GuiController(Game game) {
        this.game = game;
    }

    /**
     * Defines what to do when a key is pushed down.
     *
     * @param e The event which indicates that a keystroke occurred in a component
     */
    @Override
    public void keyPressed(KeyEvent e) {
        ;
    }

    /**
     * Defines what to do when a key is let up.
     *
     * @param e The event which indicates that a key has been let up
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (game == null) {
            return;
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    System.out.println("UP");
                    move(3);
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("RIGHT");
                    move(2);
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("DOWN");
                    move(4);
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("LEFT");
                    move(1);
                    break;
            }
        }
    }

    /**
     * Defines what to do when a character is entered.
     *
     * @param e The event which indicates that a character has been entered
     */
    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }

    /**
     * Tells the game to do a move.
     *
     * @param dir The direction of the move to be performed
     */
    public void move(int dir) {
        switch (dir) {
            case 1: //1 = LEFT
                game.move(0, -1);
                break;
            case 2: //2 = RIGHT
                game.move(0, 1);
                break;
            case 3: //3 = UP
                game.move(-1, 0);
                break;
            case 4: //4 = DOWN
                game.move(1, 0);
                break;
        }
    }
}