package representations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Rule implements Constraint {

    public Map<Variable, String> premices;
    public Map<Variable, String> conclusion;
    protected Set<Variable> scope;

    /**
     *
     * Créer une class implémentant l'interface Constraint, cette contrainte
     * permet de vérifier si les règles d'introduction sont toute valide avec au moins une Variable
     * se trouvant dans la conclusion.
     *
     * @param premices règle d'introduction
     * @param conclusion règle de conclusion
     */
    public Rule(Map<Variable, String> premices, Map<Variable, String> conclusion) {
        this.premices = premices;
        this.conclusion = conclusion;
        Set<Variable> scopeInit = new HashSet<Variable>();
        scopeInit.addAll(this.premices.keySet());
        scopeInit.addAll(conclusion.keySet());
        scope = scopeInit;
    }

    /**
     *
     * Méthode permettant d'obtenir les règles de conclusion.
     *
     * @return
     */
    public Map<Variable, String> getConclusion() {
        return this.conclusion;
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

        Set<Map.Entry<Variable, String>> setpremices = this.premices.entrySet();
        Set<Map.Entry<Variable, String>> setconclusion = this.conclusion.entrySet();
        Iterator<Map.Entry<Variable, String>> itpremices = setpremices.iterator();
        Iterator<Map.Entry<Variable, String>> itconclusion = setconclusion.iterator();
        Boolean verifConclusion = false;

        while (itpremices.hasNext()) {
            Map.Entry<Variable, String> pre = itpremices.next();
            if (!(pre.getValue() == assidment.get(pre.getKey()))) {
                return true;
            }
        }

        while (itconclusion.hasNext()) {
            Map.Entry<Variable, String> con = itconclusion.next();
            verifConclusion = con.getValue() == assidment.get(con.getKey()) || verifConclusion;
        }

        if (verifConclusion) {
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
        int comptPremice = 0;
        int comptConclusion = 0;
        int comptConclusionValide = 0;
        int lenPremice = this.premices.size();
        int lenConclusion = this.conclusion.size();

        for (Variable e : getScope()) {
            if (this.premices.containsKey(e) && assignation.containsKey(e) && assignation.get(e) == this.premices.get(e)) {
                comptPremice++;
            } else if (assignation.containsKey(e)) {
                if (assignation.get(e) == this.conclusion.get(e)) {
                    comptConclusionValide++;
                } else {
                    comptConclusion++;
                }
            }
        }
        if (comptPremice == lenPremice) {
            if (comptConclusion == lenConclusion - 1) {
                for (Map.Entry<Variable, String> entry : conclusion.entrySet()) {
                    if (!assignation.containsKey(entry.getKey()) && variables.get(entry.getKey()).size() > 1) {
                        HashSet<String> newDomain = new HashSet<>();
                        newDomain.add(entry.getValue());
                        variables.put(entry.getKey(), newDomain);
                        return true;
                    }
                }
            }
        }
        /*else if(comptConclusion == lenConclusion - 1){
		for (Map.Entry<Variable, String> entry : premices.entrySet()){
			if(!assignation.containsKey(entry.getKey())){
				if(variables.get(entry.getKey()).size() == 1 && variables.get(entry.getKey()).contains(entry.getValue())){
					for (Map.Entry<Variable, String> var : conclusion.entrySet()){
						if(!assignation.containsKey(var.getKey())){
							HashSet<String> newDomain = new HashSet<>();
							newDomain.add(var.getValue());
							variables.put(var.getKey(), newDomain);
							break;
						}
					}
				}
			}
		}
	}*/
        return false;
    }
}
