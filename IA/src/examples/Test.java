package examples;

import ppc.GenerateAndTest;
import representations.AllEqualConstraint;
import representations.IncompatibilityConstraint;
import representations.Rule;
import representations.Variable;

import java.util.*;
import ppc.Backtracking;
import representations.Constraint;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Test {

    public static void main(String[] args) {

        /*TEST CLASSE ALLEQUALCONSTRAINT*/

        HashSet<String> couleur = new HashSet<>();
        couleur.add("noir");
        couleur.add("rouge");
        couleur.add("bleu");
        
        HashSet<String> bool = new HashSet<>();
        bool.add("true");
        bool.add("false");

        Variable couleur_toit = new Variable("couleur_toit",couleur);
        Variable couleur_capot = new Variable("couleur_capot",couleur);
        Variable couleur_hayon = new Variable("couleur_hayon",couleur);
        Variable couleur_droite = new Variable("couleur_droite",couleur);
        Variable couleur_gauche = new Variable("couleur_gauche",couleur);
        Variable toit_ouvrant = new Variable("toit_ouvrant",bool);
        Variable sono = new Variable("sono",bool);

        Map<Variable, String> c1true = new HashMap<>();
        c1true.put(couleur_toit, "noir");
        c1true.put(couleur_capot, "noir");
        c1true.put(couleur_hayon, "noir");
        Map<Variable, String> c1false = new HashMap<>();
        c1false.put(couleur_capot, "noir");
        c1false.put(couleur_capot, "bleu");
        c1false.put(couleur_hayon, "rouge");

        /*System.out.println(new AllEqualConstraint().isSatisfiedBy(c1true));
        System.out.println(new AllEqualConstraint().isSatisfiedBy(c1false));*/

        /*TEST CLASSE RULE*/

        Map<Variable, String> pr1 = new HashMap<>();
        pr1.put(couleur_toit, "noir");
        Map<Variable, String> co1 = new HashMap<>();
        co1.put(couleur_gauche, "noir");
        co1.put(couleur_droite, "noir");

        Map<Variable, String> pr2 = new HashMap<>();
        pr2.put(couleur_toit, "rouge");
        Map<Variable, String> co2 = new HashMap<>();
        co2.put(couleur_gauche, "rouge");
        co2.put(couleur_droite, "rouge");

        Map<Variable, String> pr3 = new HashMap<>();
        pr3.put(couleur_toit, "bleu");
        Map<Variable, String> co3 = new HashMap<>();
        co3.put(couleur_droite, "bleu");
        co3.put(couleur_gauche, "bleu");
        
        Map<Variable, String> ic1 = new HashMap<>();
        ic1.put(couleur_droite, "noir");
        ic1.put(couleur_gauche, "noir");
        
        
        IncompatibilityConstraint r4 = new IncompatibilityConstraint(ic1);
        
        Map<Variable, String> pr4 = new HashMap<>();
        pr4.put(couleur_gauche, "noir");
        Map<Variable, String> co4 = new HashMap<>();
        co4.put(couleur_droite, "rouge");
        co4.put(couleur_droite, "bleu");

        Rule r1 = new Rule(pr1,co1);
        Rule r2 = new Rule(pr2,co2);
        Rule r3 = new Rule(pr3,co3);
        Rule rtest = new Rule(pr4,co4);

        Map<Variable, String> voiture1 = new HashMap<>();
        voiture1.put(couleur_toit, "noir");
        voiture1.put(couleur_capot, "noir");
        voiture1.put(couleur_hayon, "rouge");
        Map<Variable, String> voiture2 = new HashMap<>();
        voiture2.put(couleur_toit, "noir");
        voiture2.put(couleur_capot, "bleu");
        voiture2.put(couleur_hayon, "rouge");


        System.out.println(r1.isSatisfiedBy(voiture1) && r2.isSatisfiedBy(voiture1) && r3.isSatisfiedBy(voiture1));

        System.out.println(r1.isSatisfiedBy(voiture2) && r2.isSatisfiedBy(voiture2) && r3.isSatisfiedBy(voiture2));
        
        /* TEST INCOMPATIBILITY CONSTRAINT */

        voiture1.put(toit_ouvrant,"true");
        voiture1.put(sono,"false");

        voiture2.put(toit_ouvrant,"true");
        voiture2.put(sono,"true");

        Map<Variable, String> rule1 = new HashMap<>();
        rule1.put(toit_ouvrant,"true");
        rule1.put(sono,"true");

        IncompatibilityConstraint r5 = new IncompatibilityConstraint(rule1);

        /*System.out.println(ic1.isSatisfiedBy(voiture1));
        System.out.println(ic1.isSatisfiedBy(voiture2));*/
        
        /* TEST GENERATE AND TEST */
        
        Set<Variable> varAllEqual = new HashSet<Variable>();
        varAllEqual.add(couleur_toit);
        varAllEqual.add(couleur_hayon);
        varAllEqual.add(couleur_capot);
        AllEqualConstraint r0 = new AllEqualConstraint(varAllEqual);

        Variable[] tabVariable = {couleur_toit,couleur_capot,couleur_hayon, couleur_gauche, couleur_droite, sono, toit_ouvrant};

        Constraint[] tabRule = {r0, r1, r2, r3, r4, r5};
        
                
        Map<Variable, String> voiture3 = new HashMap<>();
        voiture3.put(couleur_toit, "rouge");
        voiture3.put(couleur_capot, "rouge");
        voiture3.put(couleur_hayon, "rouge");
        voiture3.put(couleur_droite, "rouge");
        voiture3.put(couleur_gauche, "bleu");
        voiture3.put(sono, "false");
        voiture3.put(toit_ouvrant, "false");
        
        System.out.print("Ma voiture3 : ");
        System.out.println(r0.isSatisfiedBy(voiture3) && r1.isSatisfiedBy(voiture3) && r2.isSatisfiedBy(voiture3) && r3.isSatisfiedBy(voiture3) &&
        r4.isSatisfiedBy(voiture3) && r5.isSatisfiedBy(voiture3));

        /*  ----    TEST : GENERATE AND TEST    ----    */
        
        GenerateAndTest generateAndTest = new GenerateAndTest();
        Map<Variable,String> solutionGenerateAndTest = generateAndTest.generate(tabVariable, tabRule);

        Set<Map.Entry<Variable, String>> set = solutionGenerateAndTest.entrySet();
        Iterator<Map.Entry<Variable, String>> it = set.iterator();
        while(it.hasNext()){
            Map.Entry<Variable, String> e = it.next();
            System.out.println(e.getKey().nom + " " + e.getValue());
        }
    }

}
