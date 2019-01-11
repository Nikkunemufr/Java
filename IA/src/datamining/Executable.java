package datamining;

import representations.Variable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Executable {

    public Executable(String filename, int frequence, double confiance) {
        HashSet<String> couleur = new HashSet<>();

        couleur.add("rouge");
        couleur.add("bleu");
        couleur.add("vert");
        couleur.add("noir");
        couleur.add("blanc");

        HashSet<String> bool = new HashSet<>();

        bool.add("1");
        bool.add("0");

        Variable couleur_gauche = new Variable("couleur_gauche", couleur);
        Variable couleur_toit = new Variable("couleur_toit", couleur);
        Variable couleur_capot = new Variable("couleur_capot", couleur);
        Variable couleur_hayon = new Variable("couleur_hayon", couleur);
        Variable couleur_droite = new Variable("couleur_droite", couleur);
        Variable toit_ouvrant = new Variable("toit_ouvrant", bool);
        Variable sono = new Variable("sono", bool);

        Set<Variable> varVoit = new HashSet<>();
        varVoit.add(couleur_gauche);
        varVoit.add(couleur_toit);
        varVoit.add(couleur_capot);
        varVoit.add(couleur_hayon);
        varVoit.add(couleur_droite);
        varVoit.add(toit_ouvrant);
        varVoit.add(sono);

        try {
            long timeStart = System.currentTimeMillis();

            BooleanDBReader dbReader = new BooleanDBReader(varVoit);
            BooleanDatabase dbProf = dbReader.importDB(filename);

            //Frequent Itemset
            FrequentItemsetMiner FIM = new FrequentItemsetMiner(dbProf);
            Map<Set<Variable>, Integer> frequentFIM = FIM.frequentItemsets(frequence);
            System.out.println(FIM.toString());

            //Association Rules
            AssociationRuleMiner ARM = new AssociationRuleMiner(frequentFIM);
            Map<Set<Variable>, Set<Variable>> itemsetARM = ARM.itemsetAssociationRules(confiance);

            System.out.println(ARM.toString());
            System.out.println("Il y a " + itemsetARM.size() + " regles d'association trouvé pour une fréquence de : " + frequence + " et une confiance de : " + confiance);

            System.out.println("Temps d'execution : " + (System.currentTimeMillis() - timeStart) + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
