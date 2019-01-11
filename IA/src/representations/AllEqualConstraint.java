package representations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class AllEqualConstraint implements Constraint {

    public Set<Variable> scope;

    /**
     *
     * Créer une class implémentant l'interface Constraint, cette contrainte
     * permet de vérifier si les Variables qu'on lui a attribué sont de valeur
     * identique.
     *
     * @param scope
     */
    public AllEqualConstraint(Set<Variable> scope) {
        this.scope = scope;
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

        Set<Map.Entry<Variable, String>> set = assidment.entrySet();
        Iterator<Map.Entry<Variable, String>> it = set.iterator();
        String value = null;
        Map.Entry<Variable, String> lastValue = null;
        while (it.hasNext()) {
            Iterator<Variable> itScope = this.scope.iterator();
            lastValue = it.next();
            while (itScope.hasNext()) {
                if (itScope.next().getNom() == lastValue.getKey().getNom()) {
                    if (value != null && value != lastValue.getValue()) {
                        return false;
                    }
                    value = lastValue.getValue();
                }
            }
        }
        return true;
    }

    /**
     *
     * Méthode de filtrage permettant de réduire la valeur des domaines en
     * fonction des règles de la contrainte.
     *
     * @param assignation une assignation
     * @param variables map des valeurs de Variable
     * @return une valeur booléenne permettant de savoir si un filtre a été effectué
     */
    @Override
    public Boolean filter(Map<Variable, String> assignation, Map<Variable, Set<String>> variables) {
        boolean isModify = false;
        for (Variable e : getScope()) {
            if (assignation.containsKey(e)) {
                HashSet<String> valeur = new HashSet<>();
                valeur.add(assignation.get(e));
                for (Variable var : this.scope) {
                    if (var != e && variables.get(var).size() > 1) {
                        variables.put(var, valeur);
                        isModify = true;
                    }
                }
                return isModify;
            }
        }
        return false;
    }
}
