package planning;


/**
 * Interface class that has the following methods, set up the strategy pattern.
 *
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public interface Heuristic {
    /**
     * Cette méthode donne une indication pour la décision de l'algorithm A-star.
     *
     * @param actuel état actuel de la solution
     * @param goal   état final de la solutoin
     * @return une distance en fonction de la poistion final selon differents parametre pris en contre
     */
    public int heuristic(State actuel, State goal);
}
