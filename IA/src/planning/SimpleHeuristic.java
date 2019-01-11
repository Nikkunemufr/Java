package planning;

import java.util.Map;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class SimpleHeuristic implements Heuristic {

    /**
     *
     * Cette méthode donne une indication pour la décision de l'algorithm
     * A-star, elle incrémente le compteur de cout de distance sur les pièces ne
     * correspondant pas à la solution.
     *
     * @param actuel état actuel de la solution
     * @param goal état final de la solutoin
     * @return une distance en fonction de la poistion final selon differents
     * parametre pris en contre
     */
    @Override
    public int heuristic(State actuel, State goal) {
        int compteur = 0;

        for (Map.Entry<Variable, String> entry : actuel.getMapState().entrySet()) {
            if (!goal.getMapState().get(entry.getKey()).equals(entry.getValue())) {
                compteur++;
            }
        }
        return compteur;
    }

}
