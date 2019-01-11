package datamining;

import representations.Variable;

import java.util.*;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class AssociationRuleMiner {

    private Map<Set<Variable>, Integer> frequentItemsets;
    private Map<Set<Variable>, Set<Variable>> associationRules;

    /**
     * Constructeur initialisant les ensemble d'items associés a leur fréquence
     *
     * @param frequentItemsets ensemble d'items associés a leur fréquence
     */
    public AssociationRuleMiner(Map<Set<Variable>, Integer> frequentItemsets) {
        this.frequentItemsets = frequentItemsets;
    }

    /**
     * Methode retournant les regles d'association dont la confiance des regles est supérieur a la confiance minimal
     *
     * @param confiance confiance des regles d'association à donner entre 0 et 1
     * @return regles d'association
     */
    public Map<Set<Variable>, Set<Variable>> itemsetAssociationRules(double confiance) {

        associationRules = new HashMap<>();
        // Pour chaque element de mes frequenItemsets
        for (Set<Variable> keyX : frequentItemsets.keySet()) {
            // Je verifie si l'element est vide
            if (keyX.size() > 0) {
                // Pour chaque element de mes frequenItemsets afin de voir toute les possibilités
                for (Set<Variable> keyY : frequentItemsets.keySet()) {
                    // Je verifie si l'element est vide
                    if (keyY.size() > 0) {
                        // Pour chaque ensemble d'element
                        Set<Variable> keyXY = new HashSet<>();

                        keyXY.addAll(keyX);
                        keyXY.addAll(keyY);

                        // Si le couple XY appartient à mes frequentItemsets
                        if (frequentItemsets.containsKey(keyXY)) {
                            Set<Variable> X = new HashSet<>(keyXY);
                            Set<Variable> Y = new HashSet<>(keyXY);

                            // Supprime l'ensemble doublon pour éviter par exemple le A -> A
                            X.removeAll(keyY);
                            Y.removeAll(keyX);

                            // Si conf(X -> Y) = Frequence(XY) / Fequence(X) >= confiance minimal
                            if (((double) frequentItemsets.get(keyXY) / (double) frequentItemsets.get(keyX)) >= (confiance)) {
                                // N'ajoute que les regles pertinentes ( ex : pas de AB -> AC ou de vide -> vide )
                                if (Y.size() != 0) {
                                    associationRules.put(keyX, Y);
                                }
                            }
                            // Si conf(Y -> X) = Frequence(XY) / Fequence(Y) >= confiance minimal
                            if (((double) frequentItemsets.get(keyXY) / (double) frequentItemsets.get(keyY)) >= (confiance)) {
                                // N'ajoute que les regles pertinentes ( ex : pas de CA -> BA ou de vide -> vide )
                                if (X.size() != 0) {
                                    associationRules.put(keyY, X);
                                }
                            }
                        }
                    }
                }
            }
        }
        return associationRules;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Associations Rules\n \n");
        for (Map.Entry mapEntry : associationRules.entrySet()) {
            Set<Variable> key = (Set<Variable>) mapEntry.getKey();
            Iterator<Variable> iterator = key.iterator();
            while (iterator.hasNext()) {
                Variable myCurrentElement = iterator.next();
                res.append(myCurrentElement.getNom() + " ");
                if (iterator.hasNext())
                    res.append("&& ");
            }
            res.append("-> ");
            Set<Variable> value = (Set<Variable>) mapEntry.getValue();
            Iterator<Variable> iteratorVal = value.iterator();
            while (iteratorVal.hasNext()) {
                Variable myCurrentElement = iteratorVal.next();
                res.append(myCurrentElement.getNom() + " ");
                if (iteratorVal.hasNext())
                    res.append("|| ");
            }
            res.append("\n");
        }
        return res.toString();
    }

}
