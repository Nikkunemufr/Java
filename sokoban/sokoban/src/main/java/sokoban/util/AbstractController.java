package sokoban.util;

/**
 * Controller for the Sokoban game.
 */
public abstract class AbstractController {

    /**
     * Performs a move in a given direction.
     *
     * @param dir A direction
     */
    public abstract void move(int dir);

}
