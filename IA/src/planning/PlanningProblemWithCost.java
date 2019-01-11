package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import representations.Rule;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class PlanningProblemWithCost extends PlanningProblem {

    public Map<Action, Integer> mapAction;
    public Heuristic h = new SimpleHeuristic();

    public int noeudDijkstra = 0;                   // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme Dijkstra
    public int noeudAstar = 0;                      // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme A star

    /**
     *
     * Creer un probleme avec une liste d'action à effectuer possednat un cout,
     * un etat de debut et un etat de fin.
     *
     * @param etatInitial etat de debut
     * @param etatFinal etat à atteindre à partir de l'état debut à l'aide de la
     * liste d'actions
     * @param tabActions tableau d'action qui grace à une application d'une
     * action sur un etat, permet de passer à un etat suivant
     */
    public PlanningProblemWithCost(State etatInitial, State etatFinal, ArrayList<Action> tabActions) {
        super(etatInitial, etatFinal, tabActions);
        this.mapAction = createCostAction(tabActions);
    }

    /**
     *
     * Methode du pattern Strategy, permet de changer une heuristque avec une
     * autre heuristque étant dans l'interface Heuristic.
     *
     * @param strategy objet d'une classeF possedant l'interface Heuristic
     */
    public void setHeuristic(Heuristic strategy) {
        this.h = strategy;
    }

    /**
     *
     * Algorithme de recherche à plus court chemin, qui permet de trouver un
     * plan d'action pour passer de l'état début à l'état final.
     *
     * @return le plan qui permet d'atteindre la solution
     */
    public Stack<Action> dijkstra() {
        Map<State, Integer> distance = new HashMap<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        LinkedList<State> open = new LinkedList<>();

        open.add(this.etatInitial);
        distance.put(this.etatInitial, 0);
        father.put(this.etatInitial, null);

        while (!open.isEmpty()) {
            State state = argmin(open, distance);
            open.remove(state);
            if (state.satisfies(this.etatFinal.mapState)) {
                return getPlan(father, plan, state);
            }
            for (Action act : this.tabActions) {
                if (is_applicable(act, state)) {
                    State next = act.apply(state);
                    if (!distance.containsKey(next)) {
                        distance.put(next, 10000);
                    }
                    if (distance.get(next) > distance.get(state) + mapAction.get(act)) {
                        distance.put(next, distance.get(state) + mapAction.get(act));
                        father.put(next, state);
                        plan.put(next, act);
                        if (!seTrouveDans(next, open)) {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return new Stack<Action>();
    }

    /**
     *
     * Algorithme de recherche de chemin, qui permet de trouver un plan d'action
     * pour passer de l'état début à l'état final.
     *
     * @return le plan qui permet d'atteindre la solution
     */
    public Stack<Action> astar() {
        LinkedList<State> open = new LinkedList<>();
        Map<State, State> father = new HashMap<>();
        Map<State, Integer> distance = new HashMap<>();
        Map<State, Integer> value = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();

        open.add(this.etatInitial);
        father.put(this.etatInitial, null);
        distance.put(this.etatInitial, 0);
        value.put(this.etatInitial, h.heuristic(this.etatInitial, this.etatFinal));

        while (!open.isEmpty()) {
            State state = argmin(open, value);
            if (state.satisfies(this.etatFinal.mapState)) {
                return getPlan(father, plan, state);
            }
            open.remove(state);
            for (Action act : this.tabActions) {
                if (is_applicable(act, state)) {
                    State next = act.apply(state);
                    if (!distance.containsKey(next)) {
                        distance.put(next, 10000);
                    }
                    if (distance.get(next) > distance.get(state) + mapAction.get(act)) {
                        distance.put(next, distance.get(state) + mapAction.get(act));
                        value.put(next, distance.get(next) + h.heuristic(next, this.etatFinal));
                        father.put(next, state);
                        plan.put(next, act);
                        if (!seTrouveDans(next, open)) {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return new Stack<Action>();
    }

    /**
     *
     * Methode permettant de donner un cout à une action.
     *
     * @param tabActions liste d'action sans cout
     * @return une liste d'action avec leur cout
     */
    private Map<Action, Integer> createCostAction(ArrayList<Action> tabActions) {
        Map<Action, Integer> mapActionWithCost = new HashMap<>();
        Integer cost;

        for (Action a : tabActions) {
            cost = calculCost(a);
            mapActionWithCost.put(a, cost);
        }
        return mapActionWithCost;
    }

    /**
     *
     * Algorithm permettant de definir la valeur d'un cout (ici plus il y a de
     * conclusion moins le cout de cette action sera élevé).
     *
     * @param action
     * @return
     */
    private Integer calculCost(Action action) {
        Integer size = 0;

        for (Rule r : action.getRules()) {
            size += r.getConclusion().size();
        }
        return 6 - size;
    }

    /**
     *
     * Méthode permettant de reproduire les effets de la fonction argmin, elle
     * recherche un état possedant le cout le plus petit parmis une liste
     * d'état.
     *
     * @param listState liste d'état
     * @param distance liste de réference des couts d'action
     * @return état qui a le plus petit cout
     */
    private State argmin(List<State> listState, Map<State, Integer> distance) {
        int min = 20000;
        int compteur = 0;
        int index = compteur;

        for (State state : listState) {
            if (min > distance.get(state)) {
                min = distance.get(state);
                index = compteur;
            }
            compteur++;
        }
        return listState.get(index);
    }
}
