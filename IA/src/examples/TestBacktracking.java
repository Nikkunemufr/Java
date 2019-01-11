package examples;

import representations.AllEqualConstraint;
import representations.IncompatibilityConstraint;
import representations.Rule;
import representations.Variable;
import representations.Constraint;
import representations.Disjunction;
import ppc.Backtracking;

import java.util.*;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestBacktracking {
    
    public static void main(String[] args) {
        
        /* Attribution des valeurs de domaine du style couleur */
        
        HashSet<String> couleur = new HashSet<>();
        couleur.add("noir");
        couleur.add("rouge");
        couleur.add("bleu");
        //TEST pour un probleme un peu plus grand
        /*couleur.add("b");
        couleur.add("beu");
        couleur.add("blu");
        couleur.add("ble");
        couleur.add("bl");
        couleur.add("bu");
        couleur.add("be");
        couleur.add("vert");
        couleur.add("ver");
        couleur.add("vet");
        couleur.add("vrt");
        couleur.add("ert");*/
        
        
        /* Attribution des valeurs de domaine du style binaire */
        
        HashSet<String> bool = new HashSet<>();
        bool.add("true");
        bool.add("false");
        
        /* Création des variables */
        
        Variable couleur_toit = new Variable("couleur_toit",couleur);
        Variable couleur_capot = new Variable("couleur_capot",couleur);
        Variable couleur_hayon = new Variable("couleur_hayon",couleur);
        Variable couleur_droite = new Variable("couleur_droite",couleur);
        Variable couleur_gauche = new Variable("couleur_gauche",couleur);
        Variable toit_ouvrant = new Variable("toit_ouvrant",bool);
        Variable sono = new Variable("sono",bool);
        
        
        /* Création des règles */
        
        Set<Variable> varAllEqual = new HashSet<Variable>();
        varAllEqual.add(couleur_toit);
        varAllEqual.add(couleur_hayon);
        varAllEqual.add(couleur_capot);
        
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
        
        Map<Variable, String> ic2 = new HashMap<>();
        ic2.put(toit_ouvrant,"true");
        ic2.put(sono,"true");

        AllEqualConstraint r0 = new AllEqualConstraint(varAllEqual);
        Rule r1 = new Rule(pr1,co1);
        Rule r2 = new Rule(pr2,co2);
        Rule r3 = new Rule(pr3,co3);
        IncompatibilityConstraint r4 = new IncompatibilityConstraint(ic1);
        IncompatibilityConstraint r5 = new IncompatibilityConstraint(ic2);


        Variable[] tabVariable = {couleur_toit,couleur_capot,couleur_hayon, couleur_gauche, couleur_droite, sono, toit_ouvrant};
        Constraint[] tabRule = {r0, r1, r2, r3, r4, r5};
	HashMap<Variable, Set<String>> mapVariable = new HashMap<>();
	mapVariable.put(couleur_toit, couleur);
	mapVariable.put(couleur_gauche, couleur);
	mapVariable.put(couleur_hayon, couleur);
	mapVariable.put(couleur_capot, couleur);
	mapVariable.put(couleur_droite, couleur);
	mapVariable.put(sono, bool);
	mapVariable.put(toit_ouvrant, bool);
        
        /*
            Regle pour trouver aucune solution
        
        Map<Variable, String> ic3 = new HashMap<>();
        ic3.put(couleur_toit, "bleu");
        ic3.put(couleur_capot, "bleu");
        ic3.put(couleur_hayon, "bleu");
        
        Map<Variable, String> ic4 = new HashMap<>();
        ic4.put(couleur_toit, "rouge");
        ic4.put(couleur_capot, "rouge");
        ic4.put(couleur_hayon, "rouge");
        
        Map<Variable, String> ic5 = new HashMap<>();
        ic5.put(couleur_toit, "noir");
        ic5.put(couleur_capot, "noir");
        ic5.put(couleur_hayon, "noir");
         
        IncompatibilityConstraint r6 = new IncompatibilityConstraint(ic3);
        IncompatibilityConstraint r7 = new IncompatibilityConstraint(ic4);
        IncompatibilityConstraint r8 = new IncompatibilityConstraint(ic5);
        
        Constraint[] tabRule = {r0, r1, r2, r3, r4, r5, r6, r7, r8};
        */
        /*
            TEST DEBUG
        
        
        Map<Variable, String> pr4 = new HashMap<>();
        pr4.put(couleur_gauche, "noir");
        Map<Variable, String> co4 = new HashMap<>();
        co4.put(couleur_droite, "rouge");
        co4.put(couleur_droite, "bleu");
        
        Map<Variable, String> voiture4 = new HashMap<>();
        voiture4.put(couleur_toit, "bleu");
        voiture4.put(couleur_capot, "bleu");
        voiture4.put(couleur_hayon, "noir");
        
        Backtracking testverif= new Backtracking(tabVariable,tabRule);
        System.out.println(testverif.verification(tabRule[2], voiture4));
        
        */
        
        /*  ----    TEST : BACKTRACKING    ----    */
        
        Backtracking algobacktrack = new Backtracking(mapVariable,tabRule);
        Map<Variable,String> solutionalgobacktrack = new HashMap<>();
        
        /*System.out.println("Test : Backtracking 1");
        solutionalgobacktrack = algobacktrack.backtrack();
        
        Set<Map.Entry<Variable, String>> set_back1 = solutionalgobacktrack.entrySet();
        Iterator<Map.Entry<Variable, String>> it_back1 = set_back1.iterator();
        while(it_back1.hasNext()){
            Map.Entry<Variable, String> e = it_back1.next();
            System.out.println(e.getKey().nom + " -> " + e.getValue());
        }
        
        System.out.println("Test : Backtracking 2");
        solutionalgobacktrack = algobacktrack.backtrack();
        
        Set<Map.Entry<Variable, String>> set_back2 = solutionalgobacktrack.entrySet();
        Iterator<Map.Entry<Variable, String>> it_back2 = set_back2.iterator();
        while(it_back2.hasNext()){
            Map.Entry<Variable, String> e = it_back2.next();
            System.out.println(e.getKey().nom + " -> " + e.getValue());
        }
        
        System.out.println("Test : Backtracking 3");
        solutionalgobacktrack = algobacktrack.backtrack();
        
        Set<Map.Entry<Variable, String>> set_back3 = solutionalgobacktrack.entrySet();
        Iterator<Map.Entry<Variable, String>> it_back3 = set_back3.iterator();
        while(it_back3.hasNext()){
            Map.Entry<Variable, String> e = it_back3.next();
            System.out.println(e.getKey().nom + " -> " + e.getValue());
        }
        
        System.out.println("Test : Backtracking 4");
        solutionalgobacktrack = algobacktrack.backtrack();
        
        Set<Map.Entry<Variable, String>> set_back4 = solutionalgobacktrack.entrySet();
        Iterator<Map.Entry<Variable, String>> it_back4 = set_back4.iterator();
        while(it_back4.hasNext()){
            Map.Entry<Variable, String> e = it_back4.next();
            System.out.println(e.getKey().nom + " -> " + e.getValue());
        }*/
	long debut = System.currentTimeMillis();
        

        int index_solution = 0;
        
        while (index_solution == 0 || !(solutionalgobacktrack.isEmpty())){
            System.out.println();
            System.out.println("Solution : " + ++index_solution);
            System.out.println();
            /* BACKTRACKING SANS FILTRAGE */
            
            //solutionalgobacktrack = algobacktrack.backtrack();
            
            /* BACKTRACKING AVEC FILTRAGE */
            
            solutionalgobacktrack = algobacktrack.backtrackWithFilter();
            
            Set<Map.Entry<Variable, String>> set_back = solutionalgobacktrack.entrySet();
            Iterator<Map.Entry<Variable, String>> it_back = set_back.iterator();
            while(it_back.hasNext()){
                Map.Entry<Variable, String> e = it_back.next();
                System.out.println(e.getKey().nom + " -> " + e.getValue());
            }
        }
   	System.out.print("Temps d'execution : ");
	System.out.print(System.currentTimeMillis()-debut);
        System.out.println(" avec un parcour de : " + algobacktrack.compteur + " noeuds");
        
	Variable radio = new Variable("radio",bool);
        
	Map<Variable, String> ic3 = new HashMap<>();
	ic3.put(sono, "true");
	ic3.put(toit_ouvrant, "true");
	ic3.put(radio, "false");
        IncompatibilityConstraint r6 = new IncompatibilityConstraint(ic3);
	
	Map<Variable, String> pr8 = new HashMap<>();
	pr8.put(couleur_toit, "rouge");
	Map<Variable, String> cl8 = new HashMap<>();
	cl8.put(sono, "true");
	cl8.put(couleur_droite, "noir");
        Rule r8 = new Rule(pr8,cl8);

	Map<Variable, String> pr9 = new HashMap<>();
	pr9.put(couleur_droite, "bleu");
	pr9.put(couleur_gauche, "bleu");
	Map<Variable, String> co9 = new HashMap<>();
	co9.put(couleur_toit, "bleu");
	co9.put(toit_ouvrant, "true");
        Disjunction r9 = new Disjunction(pr9,co9);
	
	HashMap<Variable, Set<String>> variables = new HashMap<>();
	variables.put(couleur_capot, couleur_capot.domaine);
	variables.put(couleur_hayon, couleur_hayon.domaine);
	variables.put(couleur_toit, couleur_toit.domaine);
	variables.put(couleur_droite, couleur_droite.domaine);
	variables.put(couleur_gauche, couleur_gauche.domaine);
	variables.put(sono, sono.domaine);
	variables.put(toit_ouvrant, toit_ouvrant.domaine);
	variables.put(radio, radio.domaine);
        /*HashSet<String> coul = new HashSet<>(variables[0].domaine);
	coul.add("false");*/
	Map<Variable, String> voiture4 = new HashMap<>();
        //voiture4.put(couleur_toit, "bleu");
        voiture4.put(couleur_toit, "rouge");
        //voiture4.put(couleur_capot, "bleu");
	voiture4.put(couleur_droite, "noir");
	voiture4.put(sono, "false");
        //voiture4.put(sono, "true");
	voiture4.put(toit_ouvrant, "true");
	boolean filterR0 = false;

	filterR0 = r0.filter(voiture4, variables);
        //System.out.println(filterR0);
	//filterR0 = r8.filter(voiture4, variables);
        //System.out.println(filterR0);
	filterR0 = r4.filter(voiture4, variables);
        System.out.println(filterR0);
        filterR0 = r5.filter(voiture4, variables);
        System.out.println(filterR0);
        filterR0 = r4.filter(voiture4, variables);
        System.out.println(filterR0);
        filterR0 = r5.filter(voiture4, variables);
        System.out.println(filterR0);
	//filterR0 = r6.filter(voiture4, variables);
        //System.out.println(filterR0);
        //filterR0 = r6.filter(voiture4, variables);
        //System.out.println(filterR0);
        //filterR0 = r8.filter(voiture4, variables);
        //System.out.println(filterR0);
        //filterR0 = r0.filter(voiture4, variables);
        //System.out.println(filterR0);
	//filterR0 = r9.filter(voiture4, variables);
        //System.out.println(filterR0);
	/*voiture4.put(couleur_capot, "rouge");
	r0.filter(voiture4, variables);
	r3.filter(voiture4, variables);
	r4.filter(voiture4, variables);
	r6.filter(voiture4, variables);*/
	/*variables.put(couleur_hayon, couleur_hayon.domaine);*/
        
       	for (Map.Entry<Variable, Set<String>> entry : variables.entrySet()){
		System.out.println("Variables :  " + entry.getKey().nom + ", Domaine : " + entry.getValue());
	}
    }
    
}