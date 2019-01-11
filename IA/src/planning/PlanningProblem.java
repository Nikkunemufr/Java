package planning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import representations.Rule;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class PlanningProblem {

    public State etatInitial;
    public State etatFinal;
    public ArrayList<Action> tabActions;

    public int noeudDFSRec = 0;             // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme DFS
    public int noeudDFSIt = 0;              // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme DFS
    public int noeudBFS = 0;                // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme BFS

    /**
     *
     * Creer un probleme avec une liste d'action à effectuer, un etat de debut
     * et un etat de fin.
     *
     * @param etatInitial etat de debut
     * @param etatFinal etat à atteindre à partir de l'état debut à l'aide de la
     * liste d'actions
     * @param tabActions tableau d'action qui grace à une application d'une
     * action sur un etat, permet de passer à un etat suivant
     */
    public PlanningProblem(State etatInitial, State etatFinal, ArrayList<Action> tabActions) {
        this.etatInitial = etatInitial;
        this.etatFinal = etatFinal;
        this.tabActions = tabActions;
    }

    /**
     *
     * Algorithme de recherche en profondeur, qui permet de trouver un plan
     * d'action pour passer de l'état début à l'état final.
     *
     * @param state état present
     * @param plan permet de stocker des actions afin de créer un plan qui mène
     * à l'état final
     * @param closed permet de stocker les états déjà parcouru afin d'éviter un
     * parcour infinie
     * @return le plan qui permet d'atteindre la solution
     */
    public Stack<Action> dfs(State state, Stack<Action> plan, ArrayList<State> closed) {
        if (state.satisfies(this.etatFinal.mapState)) {
            return plan;
        } else {
            for (Action act : this.tabActions) {
                if (is_applicable(act, state)) {
                    State next = act.apply(state);
                    if (!seTrouveDans(next, closed)) {
                        plan.push(act);
                        closed.add(next);
                        Stack<Action> subPlan = this.dfs(next, plan, closed);
                        if (subPlan != null) {
                            return subPlan;
                        } else {
                            plan.pop();
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     *
     * Algorithme de recherche en profondeur en récursif (voir dfs(State, Stack,
     * ArrayList)).
     *
     * @return le plan qui permet d'atteindre la solution
     */
    public Stack<Action> dfs() {
        boolean has_been_changed;
        ArrayList<State> closed = new ArrayList<>();
        Stack<Action> plan = new Stack<>();

        State state = this.etatInitial;
        closed.add(state);

        while (!state.satisfies(this.etatFinal.mapState)) {
            has_been_changed = false;
            for (Action act : this.tabActions) {
                if (is_applicable(act, state)) {
                    state = act.apply(state);
                    if (!seTrouveDans(state, closed)) {
                        plan.push(act);
                        closed.add(state);
                        has_been_changed = true;
                        break;
                    }
                }
            }
            if (!has_been_changed) {
                plan.pop();
            }
        }
        return plan;
    }

    /**
     *
     * Algorithme de recherche en largeur, qui permet de trouver un plan
     * d'action pour passer de l'état début à l'état final.
     *
     * @return null
     */
    public Stack<Action> bfs() {
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        ArrayList<State> closed = new ArrayList<>();
        Queue<State> open = new LinkedList<>();
        open.add(this.etatInitial);
        father.put(this.etatInitial, null);

        while (!open.isEmpty()) {
            State state = open.remove();
            closed.add(state);
            for (Action act : this.tabActions) {
                if (is_applicable(act, state)) {
                    State next = act.apply(state);
                    if ((!seTrouveDans(next, closed)) && (!seTrouveDans(next, open))) {
                        father.put(next, state);
                        plan.put(next, act);
                        if (next.satisfies(this.etatFinal.mapState)) {
                            return getPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * Méthode permettant d'obtenir le plan (à l'endroit) d'un algorithme, à
     * l'aide des ancêtres (état précedent) de l'état final et des action à
     * effectué sur les ancêtres.
     *
     * @param father liste d'ancêtre de chaque état
     * @param action liste d'action de chaque état pour obtenir son ancêtre
     * @param goal etat final
     * @return le plan qui permet d'atteindre la solution
     */
    protected Stack<Action> getPlan(Map<State, State> father, Map<State, Action> action, State goal) {
        Stack<Action> plan = new Stack<>();

        while (goal != null) {
            if (action.get(goal) != null) {
                plan.push(action.get(goal));
            }
            goal = father.get(goal);
        }
        return reverse(plan);
    }

    /**
     *
     * Permet d'inverser un plan, (Thank you :
     * https://www.geeksforgeeks.org/reverse-a-stack-using-recursion/ ).
     *
     * @param plan permet de stocker des actions afin de créer un plan qui mène
     * à l'état final
     * @return un plan inversé
     */
    /*Source d'inspiration : https://www.geeksforgeeks.org/reverse-a-stack-using-recursion/*/
    private Stack<Action> reverse(Stack<Action> plan) {
        if (plan.size() > 0) {
            Action x = plan.peek();
            plan.pop();
            plan = reverse(plan);
            plan = insert_at_bottom(plan, x);
        }
        return plan;
    }

    /**
     *
     * Permet d'inserer une action à la fin d'un plan, (Thank you :
     * https://www.geeksforgeeks.org/reverse-a-stack-using-recursion/ ).
     *
     * @param plan permet de stocker des actions afin de créer un plan qui mène
     * à l'état final
     * @return un plan inversé
     */
    /*Source d'inspiration : https://www.geeksforgeeks.org/reverse-a-stack-using-recursion/*/
    static Stack<Action> insert_at_bottom(Stack<Action> plan, Action x) {
        if (plan.isEmpty()) {
            plan.push(x);
        } else {
            Action a = plan.peek();
            plan.pop();
            plan = insert_at_bottom(plan, x);
            plan.push(a);
        }
        return plan;
    }

    /**
     *
     * Methode permettant de savoir une action est compatible avec un état
     *
     * @param action permet de modifier un état
     * @param state etat actuel
     * @return une valeur booléenne correspondant à la compatibilité d'une
     * action sur un état
     */
    protected static Boolean is_applicable(Action action, State state) {
        for (Rule rule : action.rules) {
            if (state.satisfies(rule.premices)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Méthode permmettant de savoir si un etat se trouve dans une liste d'état
     *
     * @param state etat à tester
     * @param listState liste d'état validé stocker dans cette liste
     * @return une valeur booléenne correspondant à un état étant dans la liste
     * d'état
     */
    protected Boolean seTrouveDans(State state, Collection<State> listState) {
        for (State e : listState) {
            if (State.equalsMap(e.mapState, state.mapState)) {
                return true;
            }
        }
        return false;
    }
}
