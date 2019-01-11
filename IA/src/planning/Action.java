package planning;

import java.util.Map;

import representations.Rule;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Action {

    public String name;
    public Rule[] rules;

    /**
     * Creer une action vide.
     */
    public Action() {
        this.rules = new Rule[]{};
    }

    /**
     *
     * Creer une action avec un nom et des contraintes de type Rule.
     *
     * @param name nom de l'action
     * @param rules tableau de contrainte
     */
    public Action(String name, Rule[] rules) {
        this.name = name;
        this.rules = rules;
    }

    /**
     *
     * Permet d'obtenir le nom d'une action en String.
     *
     * @return le nom de l'action
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * Permet d'obtenir le tableau de Rule d'une action.
     *
     * @return le tableau de Rule
     */
    public Rule[] getRules() {
        return this.rules;
    }

    /**
     *
     * Applique une action sur un etat.
     * 
     * @param state état actuel de la solution
     * @return un nouvel état, à partir de l'état actuel ayant était modifier par l'action 
     */
    public State apply(State state) {
        State statePrime = state.clone();
        if (PlanningProblem.is_applicable(this, state)) {
            for (Rule rule : this.rules) {
                if (state.satisfies(rule.premices)) {
                    for (Map.Entry<Variable, String> entry : rule.conclusion.entrySet()) {
                        statePrime.mapState.remove(entry.getKey());
                        statePrime.mapState.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return statePrime;
    }
}
