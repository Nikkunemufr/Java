package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import representations.Variable;
import datamining.AssociationRuleMiner;
import datamining.BooleanDatabase;
import datamining.Executable;
import datamining.FrequentItemsetMiner;
import java.util.Scanner;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestDatamining {

    public static void main(String args[]) {

        //Test sur base de données fournie
        if (args.length == 0) {
            System.out.println("Programme sur les tests du cours");
            //Test sur exemple du cours
            HashSet<String> bool = new HashSet<>();

            bool.add("1");
            bool.add("0");

            // Liste des variables
            Variable A = new Variable("A", bool);
            Variable B = new Variable("B", bool);
            Variable C = new Variable("C", bool);
            Variable D = new Variable("D", bool);
            Variable E = new Variable("E", bool);

            List<Variable> listVar = new ArrayList<>();
            listVar.add(A);
            listVar.add(B);
            listVar.add(C);
            listVar.add(D);
            listVar.add(E);

            // Liste des transactions
            Map<Variable, String> TID1 = new HashMap<>();
            TID1.put(A, "1");
            TID1.put(B, "1");
            TID1.put(C, "1");
            TID1.put(D, "1");
            TID1.put(E, "1");

            Map<Variable, String> TID2 = new HashMap<>();
            TID2.put(A, "1");
            TID2.put(B, "0");
            TID2.put(C, "1");
            TID2.put(D, "0");
            TID2.put(E, "0");

            Map<Variable, String> TID3 = new HashMap<>();
            TID3.put(A, "1");
            TID3.put(B, "1");
            TID3.put(C, "1");
            TID3.put(D, "1");
            TID3.put(E, "0");

            Map<Variable, String> TID4 = new HashMap<>();
            TID4.put(A, "0");
            TID4.put(B, "1");
            TID4.put(C, "1");
            TID4.put(D, "0");
            TID4.put(E, "0");

            Map<Variable, String> TID5 = new HashMap<>();
            TID5.put(A, "1");
            TID5.put(B, "1");
            TID5.put(C, "1");
            TID5.put(D, "0");
            TID5.put(E, "0");

            Map<Variable, String> TID6 = new HashMap<>();
            TID6.put(A, "0");
            TID6.put(B, "0");
            TID6.put(C, "0");
            TID6.put(D, "0");
            TID6.put(E, "1");

            List<Map<Variable, String>> listTrans = new ArrayList<>();
            listTrans.add(TID1);
            listTrans.add(TID2);
            listTrans.add(TID3);
            listTrans.add(TID4);
            listTrans.add(TID5);
            listTrans.add(TID6);

            BooleanDatabase db = new BooleanDatabase(listVar, listTrans);

            //Frequent Itemset
            FrequentItemsetMiner FIM = new FrequentItemsetMiner(db);
            Map<Set<Variable>, Integer> frequentFIM = FIM.frequentItemsets(3);
            System.out.println(FIM.toString());

            //Association Rules
            AssociationRuleMiner ARM = new AssociationRuleMiner(frequentFIM);
            Map<Set<Variable>, Set<Variable>> itemsetARM = ARM.itemsetAssociationRules(0.67);
            System.out.println(ARM.toString());

        } else if (args.length > 0) {
            System.out.println("Veuillez entrer une frequence minimal");
            Scanner scanFrequence = new Scanner(System.in);
            int frequence = scanFrequence.nextInt();

            System.out.println("Veuillez entrer une confiance minimal (entre 0 et 1)");
            Scanner scanConfiance = new Scanner(System.in);
            double confiance = scanConfiance.nextDouble();

            Executable exec = new Executable(args[0], frequence, confiance);
        }
    }
}
