package diagnosis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ppc.Backtracking;

import java.util.Map;
import java.util.Set;

import representations.Constraint;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Diagnoser {

    private Map<Variable, String> userChoices;
    private ArrayList<Variable> listVar;
    private Constraint[] tabConstraint;

    public Diagnoser(ArrayList<Variable> listVar, Constraint[] tabConstraint) {
        this.listVar = listVar;
        this.tabConstraint = tabConstraint;
        this.userChoices = new HashMap<>();
    }

    public void add(Variable var, String valeur) {
        this.userChoices.put(var, valeur);
    }

    public void del(Variable var) {
        this.userChoices.remove(var);
    }

    /**
     * Indique s'il s'agit oui ou non d'une explication en modifiant une copie de la map comportant les variables
     * associé à leur domaine par un ensemble restreint du couple Variable/Valeur associé ainsi que par les ensembles
     * restreints du sous ensemble des choix utilisateurs
     *
     * @param varInterdite   variable interdite
     * @param valInterdit    valeur interdite associée
     * @param subUserChoices sous ensemble des choix utilisateurs au domaine
     *                       restreint
     * @return indique si oui ou non il s'agit d'une explication
     */
    private boolean isExplaination(Variable varInterdite, String valInterdit, Map<Variable, String> subUserChoices) {
        Map<Variable, Set<String>> res = new HashMap();

        Set<String> setInterdit = new HashSet<>();
        setInterdit.add(valInterdit);

        res.put(varInterdite, setInterdit);

        for (Variable varChoice : subUserChoices.keySet()) {
            Set<String> set = new HashSet<>();
            set.add(subUserChoices.get(varChoice));
            res.put(varChoice, set);
        }

        Backtracking backtracking = new Backtracking(res, tabConstraint);
        // Est-ce que c'est une explication ?
        return !backtracking.backtrack().isEmpty();
    }


    /**
     * Retourne une explication du non respect des contraintes pour le couple variable/valeur indiqué
     *
     * @param var    variable utilisateur
     * @param valeur valeur utilisateur
     * @return une explication du non respect des contraintes
     */
    public Map<Variable, String> getExplanation(Variable var, String valeur) {
        Map<Variable, String> diagnosis = new HashMap<>(this.userChoices);
        Map<Variable, String> unexploredUserChoices = new HashMap<>(this.userChoices);
        Map<Variable, String> tampDiagnosis = new HashMap<>();

        for (Map.Entry mapEntry : unexploredUserChoices.entrySet()) {

            tampDiagnosis = new HashMap<>(diagnosis);
            if (tampDiagnosis.size() > 1) {
                tampDiagnosis.remove(mapEntry.getKey());

                if (isExplaination(var, valeur, tampDiagnosis)) {
                    diagnosis = new HashMap<>(tampDiagnosis);
                    return diagnosis;
                }
            }
        }
        return tampDiagnosis;
    }

}
