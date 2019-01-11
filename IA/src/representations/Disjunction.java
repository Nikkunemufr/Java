package representations;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Disjunction extends Rule {

    /**
     *
     * Créer une class héritant de la class Rule, cette contrainte permet de
     * vérifier si les règles de conclusion sont toute valide avec au moins une Variable
     * se trouvant dans l'introduction.
     *
     * @param premices règle d'introduction
     * @param conclusion règle de conclusion
     */
    public Disjunction(Map<Variable, String> premices, Map<Variable, String> conclusion) {
        super(premices, conclusion);
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
        Set<Map.Entry<Variable, String>> setrule = this.premices.entrySet();
        Iterator<Map.Entry<Variable, String>> itrule = setrule.iterator();

        Boolean verifRule = false;

        while (itrule.hasNext()) {
            Map.Entry<Variable, String> rul = itrule.next();
            if (assidment.get(rul.getKey()) != null) {
                verifRule = rul.getValue() == assidment.get(rul.getKey()) || verifRule;
            }
        }

        if (verifRule) {
            return true;
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
        //System.out.println("Anna je suis une disjunction");
        int comptPremice = 0;
        int comptConclusion = 0;
        int comptPremiceValide = 0;
        int lenPremice = this.premices.size();
        int lenConclusion = this.conclusion.size();

        for (Variable e : getScope()) {
            if (this.conclusion.containsKey(e) && assignation.containsKey(e) && assignation.get(e) == this.conclusion.get(e)) {
                comptConclusion++;
            } else if (assignation.containsKey(e)) {
                if (assignation.get(e) == this.premices.get(e)) {
                    comptPremiceValide++;
                } else {
                    comptPremice++;
                }
            }
        }
        System.out.println("len Pre : " + lenPremice + " compt Pre : " + comptPremice + " len Con : " + lenConclusion + " compt Con : " + comptConclusion + " valide : " + comptPremiceValide);

        if (comptPremiceValide >= 1) {
            for (Map.Entry<Variable, String> entry : conclusion.entrySet()) {
                if (!assignation.containsKey(entry.getKey())) {
                    HashSet<String> newDomain = new HashSet<>();
                    newDomain.add(entry.getValue());
                    variables.put(entry.getKey(), newDomain);
                }
            }
        }
        return true;
    }
}
