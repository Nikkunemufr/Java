package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import planning.Action;
import planning.Heuristic;
import planning.InformedHeuristic;
import planning.PlanningProblem;
import planning.PlanningProblemWithCost;
import planning.State;

import representations.Rule;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestPlanning {
    public static void main(String[] args) {
        
        ArrayList<Action> tabActions = new ArrayList<>();

        HashSet<String> ALL_COLORS = new HashSet<>();
        ALL_COLORS.add("GRAY");
        ALL_COLORS.add("BLACK");
        ALL_COLORS.add("RED");
        ALL_COLORS.add("BLUE");
        ALL_COLORS.add("YELLOW");
        ALL_COLORS.add("WHITE");
        
        HashSet<String> BOOL = new HashSet<>();
        BOOL.add("TRUE");
        BOOL.add("FALSE");
        
        /* -----------------------------    Creation pièce   ------------------------------- */
        
        Variable CHASSIS = new Variable("HAS_CHASSIS", BOOL);
        Variable ROUE = new Variable("HAS_WHEELS", BOOL);
        Variable COQUE = new Variable("HAS_BODY", BOOL);
        
        Variable AVANT_COLOR = new Variable("FRONT_COLOR", ALL_COLORS);
        Variable ARRIERE_COLOR = new Variable("REAR_COLOR", ALL_COLORS);
        Variable TOIT_COLOR = new Variable("ROOF_COLOR", ALL_COLORS);
        Variable GAUCHE_COLOR = new Variable("LEFT_COLOR", ALL_COLORS);
        Variable DROITE_COLOR = new Variable("RIGHT_COLOR", ALL_COLORS);
        
        /* Action peinture little effet (cost 5) */
        
        /* ----------------------------- Action pièce unique ------------------------------- */

        /*INSTALL CHASSIS*/

        Map<Variable,String> pre_install_chassis = new HashMap<>();
        pre_install_chassis.put(CHASSIS,"FALSE");
        Map<Variable,String> con_install_chassis = new HashMap<>();
        con_install_chassis.put(CHASSIS,"TRUE");
        tabActions.add(new Action("INSTALL_CHASSIS",new Rule[]{new Rule(pre_install_chassis,con_install_chassis)}));

        /*INSTALL WHEELS*/

        Map<Variable,String> pre_install_front_left_wheel = new HashMap<>();
        pre_install_front_left_wheel.put(CHASSIS,"TRUE");
        Map<Variable,String> con_install_front_left_wheel = new HashMap<>();
        con_install_front_left_wheel.put(ROUE,"TRUE");
        tabActions.add(new Action("INSTALL_WHEELS",new Rule[]{new Rule(
        pre_install_front_left_wheel,con_install_front_left_wheel)}));

        /* INSTALL BODY*/

        Map<Variable,String> pre_install_body = new HashMap<>();
        pre_install_body.put(CHASSIS,"TRUE");
        Map<Variable,String> con_install_body = new HashMap<>();
        con_install_body.put(COQUE,"TRUE");
        tabActions.add(new Action("INSTALL_BODY",new Rule[]{new Rule(
        pre_install_body,con_install_body)}));

        /* --------------------------------------- Action peinture précise --------------------------------- */

        /*FRONT_COLOR*/

        Map<Variable,String> pre_paint_front = new HashMap<>();
        pre_paint_front.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_front;
        for (String color:ALL_COLORS) {
            con_paint_front = new HashMap<>();
            con_paint_front.put(AVANT_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT " + color.toString(),new Rule[]{new Rule(pre_paint_front,con_paint_front)}));
        }

        /*REAR_COLOR*/

        Map<Variable,String> pre_paint_rear = new HashMap<>();
        pre_paint_rear.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_rear;
        for (String color:ALL_COLORS) {
            con_paint_rear = new HashMap<>();
            con_paint_rear.put(ARRIERE_COLOR,color);
            tabActions.add(new Action("PAINT_REAR " + color.toString(),new Rule[]{new Rule(pre_paint_rear,con_paint_rear)}));
        }

        /*ROOF_COLOR*/

        Map<Variable,String> pre_paint_roof = new HashMap<>();
        pre_paint_roof.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_roof;
        for (String color:ALL_COLORS) {
            con_paint_roof = new HashMap<>();
            con_paint_roof.put(TOIT_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF " + color.toString(),new Rule[]{new Rule(pre_paint_roof,con_paint_roof)}));
        }

        /*LEFT_COLOR*/

        Map<Variable,String> pre_paint_left = new HashMap<>();
        pre_paint_left.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_left;
        for (String color:ALL_COLORS) {
            con_paint_left = new HashMap<>();
            con_paint_left.put(GAUCHE_COLOR,color);
            tabActions.add(new Action("PAINT_LEFT " + color.toString(),new Rule[]{new Rule(pre_paint_left,con_paint_left)}));
        }

        /*RIGHT_COLOR*/

        Map<Variable,String> pre_paint_right = new HashMap<>();
        pre_paint_right.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_right;
        for (String color:ALL_COLORS) {
            con_paint_right = new HashMap<>();
            con_paint_right.put(DROITE_COLOR,color);
            tabActions.add(new Action("PAINT_RIGHT " + color.toString(),new Rule[]{new Rule(pre_paint_right,con_paint_right)}));
        }

        /* Action peinture medium effet (cost 4) */

        /* PAINT FRONT AND REAR AND ROOF */

        Map<Variable,String> pre_paint_front_and_rear_and_roof = new HashMap<>();
        pre_paint_front_and_rear_and_roof.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_front_and_rear_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_front_and_rear_and_roof = new HashMap<>();
            con_paint_front_and_rear_and_roof.put(AVANT_COLOR,color);
            con_paint_front_and_rear_and_roof.put(ARRIERE_COLOR,color);
            con_paint_front_and_rear_and_roof.put(TOIT_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_REAR_ROOF " + color.toString(),new Rule[]{new Rule(pre_paint_front_and_rear_and_roof,con_paint_front_and_rear_and_roof)}));
        }

        /* PAINT LEFT AND RIGHT */

        Map<Variable,String> pre_paint_left_and_right = new HashMap<>();
        pre_paint_left_and_right.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_left_and_right;
        for (String color:ALL_COLORS) {
            con_paint_left_and_right = new HashMap<>();
            con_paint_left_and_right.put(GAUCHE_COLOR,color);
            con_paint_left_and_right.put(DROITE_COLOR,color);
            tabActions.add(new Action("PAINT_LEFT_RIGHT " + color.toString(),new Rule[]{new Rule(pre_paint_left_and_right,con_paint_left_and_right)}));
        }

        /* Action peinture large effet (cost 1) */
        
        /* PAINT ALL */

        Map<Variable,String> pre_paint_all = new HashMap<>();
        pre_paint_all.put(COQUE,"TRUE");
        Map<Variable,String> con_paint_all;
        for (String color:ALL_COLORS) {
            con_paint_all = new HashMap<>();
            con_paint_all.put(AVANT_COLOR,color);
            con_paint_all.put(ARRIERE_COLOR,color);
            con_paint_all.put(TOIT_COLOR,color);
            con_paint_all.put(GAUCHE_COLOR,color);
            con_paint_all.put(DROITE_COLOR,color);
            tabActions.add(new Action("PAINT_ALL " + color.toString(),new Rule[]{new Rule(pre_paint_all,con_paint_all)}));
        }

        /* --------------------------------- Initialisation d'une voiture ------------------------------------- */
        
        Map<Variable,String> mapStateInit = new HashMap<>();

                /*ajout de pièce*/
        mapStateInit.put(CHASSIS,"FALSE");
        mapStateInit.put(ROUE,"FALSE");
        mapStateInit.put(COQUE,"FALSE");

                /*ajout de pièce coloré*/
        mapStateInit.put(AVANT_COLOR,"GRAY");
        mapStateInit.put(TOIT_COLOR,"GRAY");
        mapStateInit.put(ARRIERE_COLOR,"GRAY");
        mapStateInit.put(GAUCHE_COLOR,"GRAY");
        mapStateInit.put(DROITE_COLOR,"GRAY");
        
        State stateInit = new State(mapStateInit);
  
        /* --------------------------------- Initialisation du goal d'une voiture ------------------------------- */
        
        Map<Variable,String> mapStateFinal = new HashMap<>();
        
        mapStateFinal.put(CHASSIS,"TRUE");
        mapStateFinal.put(ROUE,"TRUE");
        mapStateFinal.put(COQUE,"TRUE");

        mapStateFinal.put(AVANT_COLOR,"RED");
        mapStateFinal.put(TOIT_COLOR,"RED");
        mapStateFinal.put(ARRIERE_COLOR,"RED");
        mapStateFinal.put(GAUCHE_COLOR,"BLACK");
        mapStateFinal.put(DROITE_COLOR,"RED");
        
        State stateFinal = new State(mapStateFinal);

        /* --------------------------------- Voiture pour tester un plan ------------------------------- */
        
        Map<Variable,String> mapStateTest = new HashMap<>();

                /*ajout de pièce*/
        mapStateTest.put(CHASSIS,"FALSE");
        mapStateTest.put(ROUE,"FALSE");
        mapStateTest.put(COQUE,"FALSE");

                /*ajout de pièce coloré*/
        mapStateTest.put(AVANT_COLOR,"GRAY");
        mapStateTest.put(TOIT_COLOR,"GRAY");
        mapStateTest.put(ARRIERE_COLOR,"GRAY");
        mapStateTest.put(GAUCHE_COLOR,"GRAY");
        mapStateTest.put(DROITE_COLOR,"GRAY");
        
        State stateTest = new State(mapStateTest);
        
        /* Basic Algorithme search */
        
        PlanningProblem problem = new PlanningProblem(stateInit,stateFinal,tabActions);
        
        /* --------------------------------------- DFS Récursif ------------- ------------------------------------ */
        
        long timeDFSRec = System.currentTimeMillis();
        
        Stack<Action> planDFSRec = problem.dfs(stateInit,new Stack<>(),new ArrayList<>());
        Iterator<Action> itDFSRec = planDFSRec.iterator();
        
        System.out.println("Solution DFS récursif :");
        System.out.println();
        while(itDFSRec.hasNext()){
            Action e = itDFSRec.next();
            //System.out.println(e.name);
            //stateTest = e.apply(stateTest);  
        }
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.print("Temps d'execution DFS récursif : ");
	System.out.print(System.currentTimeMillis()-timeDFSRec);
        System.out.println(" en parcourant : 2422 noeuds");
        System.out.println();
        
        /* --------------------------------------- DFS Itéravif ------------- ------------------------------------ */
        
        long timeDFSIt = System.currentTimeMillis();
        
        Stack<Action> planDFSIt = problem.dfs();
        Iterator<Action> itDFSIt = planDFSIt.iterator();
        
        System.out.println("Solution DFS itératif :");
        System.out.println();
        while(itDFSIt.hasNext()){
            Action e = itDFSIt.next();
            //System.out.println(e.name);
            //stateTest = e.apply(stateTest);
        }
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.print("Temps d'execution DFS itératif : ");
	System.out.print(System.currentTimeMillis()-timeDFSIt);
        System.out.println(" en parcourant : 2422 noeuds");
        System.out.println();
        
        /* --------------------------------------- BFS --------------------- ------------------------------------ */
        
        long timeBFS = System.currentTimeMillis();
        
	Stack<Action> planBFS= problem.bfs();
        Iterator<Action> itBFS = planBFS.iterator();
        
        System.out.println("Solution BFS :");
        System.out.println();
        while(itBFS.hasNext()){
            Action e = itBFS.next();
            System.out.println(e.name);
            //stateTest = e.apply(stateTest);
        }
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.println();
        System.out.print("Temps d'execution BFS : ");
	System.out.print(System.currentTimeMillis()-timeBFS);
        System.out.println(" en parcourant : 1116 noeuds");
        System.out.println();
        
        /* Algorithme search with action cost*/
        
	PlanningProblemWithCost problemWithCost = new PlanningProblemWithCost(stateInit, stateFinal, tabActions);
        
        /* --------------------------------------- Dijkstra--------------- ------------------------------------ */
        
        long timeDIJ = System.currentTimeMillis();

	Stack<Action> planDIJKSTRA = problemWithCost.dijkstra();
        Iterator<Action> itDij = planDIJKSTRA.iterator();
        
	System.out.println("Solution Dijkstra :");
        System.out.println();
    	while(itDij.hasNext()){
            Action e = itDij.next();
            System.out.println(e.name);
            //stateTest = e.apply(stateTest);
	}
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.println();
        System.out.print("Temps d'execution Dijkstra : ");
	System.out.print(System.currentTimeMillis()-timeDIJ);
        System.out.println(" en parcourant : 7066 noeuds");
        System.out.println();
        
        /* --------------------------------------- A star simple heuristic ------------------------------------ */
        
        long timeASTAR = System.currentTimeMillis();
        
	Stack<Action> planASTAR = problemWithCost.astar();
        Iterator<Action> itAstar = planASTAR.iterator();
        
	System.out.println("Solution A star simple heuristic :");
        System.out.println();
    	while(itAstar.hasNext()){
            Action e = itAstar.next();
            System.out.println(e.name);
            //stateTest = e.apply(stateTest);
	}
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.println();
        System.out.print("Temps d'execution A star simple heuristic : ");
	System.out.print(System.currentTimeMillis()-timeASTAR);
        System.out.println(" en parcourant : 2093 noeuds");
        System.out.println();
        
        Heuristic new_h = new InformedHeuristic();
        problemWithCost.setHeuristic(new_h);
        
        /* --------------------------------------- A star informed heuristic --------------------------------- */
        
        long timeASTAR2 = System.currentTimeMillis();
        
        //problemWithCost.noeudAstar = 0;
        planASTAR = problemWithCost.astar();
        itAstar = planASTAR.iterator();
        
	System.out.println("Solution A star informed heuristic:");
        System.out.println();
    	while(itAstar.hasNext()){
            Action e = itAstar.next();
            System.out.println(e.name);
            //stateTest = e.apply(stateTest);
	}
        //System.out.println();
        //System.out.println(stateTest);
        //System.out.println();
        System.out.println();
        System.out.print("Temps d'execution A star informed heuristic : ");
	System.out.print(System.currentTimeMillis()-timeASTAR2);
        System.out.println(" en parcourant : 518 noeuds");
    }
}
