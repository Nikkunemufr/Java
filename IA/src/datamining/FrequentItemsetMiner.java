package datamining;

import representations.Variable;

import java.util.*;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class FrequentItemsetMiner {

    private BooleanDatabase booleanDatabase;
    private Map<Set<Variable>, Integer> frequentItemsets;

    public FrequentItemsetMiner(BooleanDatabase booleanDatabase) {
        this.booleanDatabase = booleanDatabase;
    }

    /**
     * Retourne la frequence associé a une transaction qui apparait plus que la frequence minimale donnée
     *
     * @param frequence frequence minimal demandé
     * @return retourne map de transaction associé à sa frequence
     */
    public Map<Set<Variable>, Integer> frequentItemsets(int frequence) {

        frequentItemsets = new HashMap<>();
        List<Set<Variable>> listSet = new ArrayList<>();

        for (Map<Variable, String> transactions : booleanDatabase.getListTransactions()) {
            List<Variable> listVariable = new ArrayList<>();
            for (Map.Entry mapEntryTransactions : transactions.entrySet()) {
                //Identifier la clef et la valeur
                Variable key = (Variable) mapEntryTransactions.getKey();
                String value = (String) mapEntryTransactions.getValue();

                //Si valeur est vrai
                if (value == "1")
                    //Ajout au set temporaire
                    listVariable.add(key);
            }

            //Generation de toute les combinaisons
            int size = listVariable.size();
            // Explication d'une generation optimisé de toute les combinaisons possible via decalage des bits :
            // https://theproductiveprogrammer.blog/GeneratingCombinations.java.php
            for (int i = 0; i < (1 << size); i++) {
                Set<Variable> set = new HashSet<>();
                for (int j = 0; j < size; j++) {
                    if ((i & (1 << j)) != 0)
                        set.add(listVariable.get(j));
                }
                listSet.add(set);
            }
        }

        Map<Set<Variable>, Integer> mapTemp = new HashMap<>();
        // Calcul des frequences par parcours en largeur
        for (Set<Variable> set : listSet) {
            if (!mapTemp.containsKey(set))
                mapTemp.put(set, 0);
            mapTemp.put(set, mapTemp.get(set) + 1);
        }

        // Ajout des frequentItemSet >= à la frequence minimal
        for (Set<Variable> key : mapTemp.keySet()) {
            if (mapTemp.get(key) >= frequence)
                frequentItemsets.put(key, mapTemp.get(key));
        }

        return frequentItemsets;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append("\nFrequence ItemSets \n \n");
        for (Map.Entry mapEntry : frequentItemsets.entrySet()) {
            Set<Variable> key = (Set<Variable>) mapEntry.getKey();
            Iterator<Variable> iterator = key.iterator();
            while (iterator.hasNext()) {
                Variable myCurrentElement = iterator.next();
                res.append(myCurrentElement.getNom() + " ");
            }
            res.append(" valeur = " + mapEntry.getValue());
            res.append("\n");

        }
        return res.toString();
    }
}


