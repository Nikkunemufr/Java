package representations;

import java.util.Set;
import java.util.Map;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public interface Constraint {

    /**
     *
     * Méthode permettant d'obtenir les Variables concerné par cette contrainte.
     *
     * @return une liste de Variable
     */
    public Set<Variable> getScope();

    /**
     *
     * Méthode vérifiant si les variables correspondent aux règles de cette
     * contrainte.
     *
     * @param assidment une assignation
     * @return une valeur booléenne correspondant au test des règles de la
     * contrainte sur l'assignation
     */
    public Boolean isSatisfiedBy(Map<Variable, String> assidment);

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
    public Boolean filter(Map<Variable, String> assignation, Map<Variable, Set<String>> variables);

}
