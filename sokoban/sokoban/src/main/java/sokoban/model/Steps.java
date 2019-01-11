package sokoban.model;

import java.util.ArrayList;

public class Steps {

    private ArrayList<Integer> initialPosition = new ArrayList<>();
    private ArrayList<Integer> futurePosition = new ArrayList<>();

    public Steps(int initPosX, int initPosY, int futurePosX, int futurePosY) {
        initialPosition.add(initPosX);
        initialPosition.add(initPosY);
        futurePosition.add(futurePosX);
        futurePosition.add(futurePosY);
    }

    /**
     * Retourne la position initiale en x.
     *
     * @return coordonnés x de la position initial
     */
    public int getInitPosX() {
        return initialPosition.get(0);
    }

    /**
     * Retourne la position initiale en y.
     *
     * @return coordonnés y de la position initial.
     */
    public int getInitPosY() {
        return initialPosition.get(1);
    }

    /**
     * Retourne la position future en y.
     *
     * @return coordonnés y de la position futur.
     */
    public int getFuturePosX() {
        return futurePosition.get(0);
    }

    /**
     * Retourne la position future en y.
     *
     * @return coordonnés y de la position futur.
     */
    public int getFuturePosY() {
        return futurePosition.get(1);
    }

}
