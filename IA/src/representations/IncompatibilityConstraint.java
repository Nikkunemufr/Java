package representations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class IncompatibilityConstraint extends Rule {

    /**
     *
     * Créer une class héritant de la class Rule, cette contrainte
     * permet de vérifier si la valeurs des variables ne sont pas égale au valeurs des variables de la conclusion.
     *
     * @param conclusion règle de conclusion
     */
    public IncompatibilityConstraint(Map<Variable, String> conclusion) {
        super(new HashMap<Variable, String>(), conclusion);
    }

    /**
     *
     * Méthode permettant d'obtenir les Variables concerné par cette contrainte.
     *
     * @return une liste de Variable
     */
    @Override
    public Set<Variable> getScope() {
        return scope;
    }

    /**
     *
     * Méthode vérifiant si les variables correspondent aux règles de cette
     * contrainte.
     *
     * @param assidment une assignation
     * @return une valeur booléenne correspondant au test des règles de la
     * contrainte sur l'assignation
     */
    @Override
    public Boolean isSatisfiedBy(Map<Variable, String> assidment) {
        Set<Map.Entry<Variable, String>> setrule = this.conclusion.entrySet();
        Iterator<Map.Entry<Variable, String>> itrule = setrule.iterator();

        Boolean verifRule = true;

        while (itrule.hasNext()) {
            Map.Entry<Variable, String> rul = itrule.next();
            if (assidment.get(rul.getKey()) != null) {
                if (!(rul.getValue() == assidment.get(rul.getKey()) && verifRule)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * Méthode de filtrage permettant de réduire la valeur des domaines en
     * fonction des règles de la contrainte.
     *
     * @param assignation une assignation
     * @param variables map des valeurs de Variable
     * @return une valeur booléenne permettant de savoir si un filtre a été
     * effectué
     */
    @Override
    public Boolean filter(Map<Variable, String> assignation, Map<Variable, Set<String>> variables) {
        int compteur = 1;
        boolean isModify = false;

        for (Variable e : getScope()) {
            if (assignation.containsKey(e) && assignation.get(e) == conclusion.get(e)) {
                compteur++;
            }
        }
        if (compteur == getScope().size()) {
            for (Variable var : getScope()) {
                if (!assignation.containsKey(var) && variables.get(var).contains(this.conclusion.get(var))) {
                    HashSet<String> newDomain = new HashSet<>(variables.get(var)); //(var.domaine)
                    newDomain.remove(conclusion.get(var));
                    variables.put(var, newDomain);
                    isModify = true;
                }
            }
        }
        return isModify;
    }
}
