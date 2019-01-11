/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import planning.PlanningProblemWithCost;
import planning.State;

import representations.Rule;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestAstar {
    
    public static void main(String[] args) {
        
        ArrayList<Action> tabActions = new ArrayList<>();

        /* -----------------------------    Creation des domaines --------------------------- */
        
        HashSet<String> ALL_COLORS = new HashSet<>();
        ALL_COLORS.add("GRAY");
        ALL_COLORS.add("BLACK");
        ALL_COLORS.add("WHITE");
        ALL_COLORS.add("RED");
        ALL_COLORS.add("GREEN");
        ALL_COLORS.add("BLUE");
        ALL_COLORS.add("ORANGE");
        ALL_COLORS.add("YELLOW");
        
        
        HashSet<String> BOOL = new HashSet<>();
        BOOL.add("TRUE");
        BOOL.add("FALSE");
          
        /* -----------------------------    Creation pièce   ------------------------------- */
        
        Variable HAS_CHASSIS = new Variable("HAS_CHASSIS", BOOL);                               
        Variable HAS_FRONT_LEFT_WHEEL = new Variable("HAS_FRONT_LEFT_WHEEL", BOOL);             
        Variable HAS_FRONT_RIGHT_WHEEL = new Variable("HAS_FRONT_RIGHT_WHEEL", BOOL);          
        Variable HAS_REAR_LEFT_WHEEL = new Variable("HAS_REAR_LEFT_WHEEL", BOOL);               
        Variable HAS_REAR_RIGHT_WHEEL = new Variable("HAS_REAR_RIGHT_WHEEL", BOOL);             
        Variable HAS_BODY = new Variable("HAS_BODY", BOOL);                                     


        Variable FRONT_LEFT_WHEEL_COLOR = new Variable("FRONT_LEFT_WHEEL_COLOR", ALL_COLORS);   
        Variable FRONT_RIGHT_WHEEL_COLOR = new Variable("FRONT_RIGHT_WHEEL_COLOR", ALL_COLORS); 
        Variable REAR_LEFT_WHEEL_COLOR = new Variable("REAR_LEFT_WHEEL_COLOR", ALL_COLORS);     
        Variable REAR_RIGHT_WHEEL_COLOR = new Variable("REAR_RIGHT_WHEEL_COLOR", ALL_COLORS);  

        Variable FRONT_COLOR = new Variable("FRONT_COLOR", ALL_COLORS);                         
        Variable LEFT_COLOR = new Variable("LEFT_COLOR", ALL_COLORS);                           
        Variable REAR_COLOR = new Variable("REAR_COLOR", ALL_COLORS);                           
        Variable RIGHT_COLOR = new Variable("RIGHT_COLOR", ALL_COLORS);                         
        Variable ROOF_COLOR = new Variable("ROOF_COLOR", ALL_COLORS);                           
        
        
        /* Action peinture little effet (cost 5) */
        
        /* ----------------------------- Action pièce unique ------------------------------- */

        /*INSTALL CHASSIS*/

        Map<Variable,String> pre_install_chassis = new HashMap<>();
        pre_install_chassis.put(HAS_CHASSIS,"FALSE");
        Map<Variable,String> con_install_chassis = new HashMap<>();
        con_install_chassis.put(HAS_CHASSIS,"TRUE");
        tabActions.add(new Action("INSTALL_CHASSIS",new Rule[]{new Rule(pre_install_chassis,con_install_chassis)}));

        /*INSTALL WHEELS*/

        Map<Variable,String> pre_install_front_left_wheel = new HashMap<>();
        pre_install_front_left_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_front_left_wheel = new HashMap<>();
        con_install_front_left_wheel.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_FRONT_LEFT_WHEEL",new Rule[]{new Rule(
        pre_install_front_left_wheel,con_install_front_left_wheel)}));
        
        Map<Variable,String> pre_install_front_right_wheel = new HashMap<>();
        pre_install_front_right_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_front_right_wheel = new HashMap<>();
        con_install_front_right_wheel.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_FRONT_RIGHT_WHEEL",new Rule[]{new Rule(
        pre_install_front_right_wheel,con_install_front_right_wheel)}));
        
        Map<Variable,String> pre_install_rear_left_wheel = new HashMap<>();
        pre_install_rear_left_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_rear_left_wheel = new HashMap<>();
        con_install_rear_left_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_REAR_LEFT_WHEEL",new Rule[]{new Rule(
        pre_install_rear_left_wheel,con_install_rear_left_wheel)}));
        
        Map<Variable,String> pre_install_rear_right_wheel = new HashMap<>();
        pre_install_rear_right_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_rear_right_wheel = new HashMap<>();
        con_install_rear_right_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_REAR_RIGHT_WHEEL",new Rule[]{new Rule(
        pre_install_rear_right_wheel,con_install_rear_right_wheel)}));
        
        /* INSTALL BODY*/

        Map<Variable,String> pre_install_body = new HashMap<>();
        pre_install_body.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_body = new HashMap<>();
        con_install_body.put(HAS_BODY,"TRUE");
        tabActions.add(new Action("INSTALL_BODY",new Rule[]{new Rule(
        pre_install_body,con_install_body)}));
        
        /* --------------------------------------- Action pièce parallel ----------------------------------- */
        
        /* INSTALL FRONT WHEELS */

        Map<Variable,String> pre_install_front_wheel = new HashMap<>();
        pre_install_front_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_front_wheel = new HashMap<>();
        con_install_front_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        con_install_front_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_FRONT_WHEEL",new Rule[]{new Rule(
        pre_install_front_wheel,con_install_front_wheel)}));
        
        /* INSTALL REAR WHEELS */     
        
        Map<Variable,String> pre_install_rear_wheel = new HashMap<>();
        pre_install_rear_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_rear_wheel = new HashMap<>();
        con_install_rear_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        con_install_rear_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_REAR_WHEEL",new Rule[]{new Rule(
        pre_install_rear_wheel,con_install_rear_wheel)}));
        
        /* INSTALL RIGHT WHEELS */ 
        
        Map<Variable,String> pre_install_right_wheel = new HashMap<>();
        pre_install_right_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_right_wheel = new HashMap<>();
        con_install_right_wheel.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        con_install_right_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_RIGHT_WHEEL",new Rule[]{new Rule(
        pre_install_right_wheel,con_install_right_wheel)}));
        
        /* INSTALL LEFT WHEELS */ 
        
        Map<Variable,String> pre_install_left_wheel = new HashMap<>();
        pre_install_left_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_left_wheel = new HashMap<>();
        con_install_left_wheel.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        con_install_left_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_LEFT_WHEEL",new Rule[]{new Rule(
        pre_install_left_wheel,con_install_left_wheel)}));
        
        /* INSTALL ALL WHEELS */ 
        
        Map<Variable,String> pre_install_all_wheel = new HashMap<>();
        pre_install_all_wheel.put(HAS_CHASSIS,"TRUE");
        Map<Variable,String> con_install_all_wheel = new HashMap<>();
        con_install_all_wheel.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        con_install_all_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        con_install_all_wheel.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        con_install_all_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_ALL_WHEEL",new Rule[]{new Rule(
        pre_install_left_wheel,con_install_left_wheel)}));
        
        /* --------------------------------------- Action peinture précise --------------------------------- */

        /*FRONT_COLOR*/

        Map<Variable,String> pre_paint_front = new HashMap<>();
        pre_paint_front.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_front;
        for (String color:ALL_COLORS) {
            con_paint_front = new HashMap<>();
            con_paint_front.put(FRONT_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT " + color.toString(),new Rule[]{new Rule(pre_paint_front,con_paint_front)}));
        }

        /*REAR_COLOR*/

        Map<Variable,String> pre_paint_rear = new HashMap<>();
        pre_paint_rear.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_rear;
        for (String color:ALL_COLORS) {
            con_paint_rear = new HashMap<>();
            con_paint_rear.put(REAR_COLOR,color);
            tabActions.add(new Action("PAINT_REAR " + color.toString(),new Rule[]{new Rule(pre_paint_rear,con_paint_rear)}));
        }

        /*ROOF_COLOR*/

        Map<Variable,String> pre_paint_roof = new HashMap<>();
        pre_paint_roof.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_roof;
        for (String color:ALL_COLORS) {
            con_paint_roof = new HashMap<>();
            con_paint_roof.put(ROOF_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF " + color.toString(),new Rule[]{new Rule(pre_paint_roof,con_paint_roof)}));
        }

        /*LEFT_COLOR*/

        Map<Variable,String> pre_paint_left = new HashMap<>();
        pre_paint_left.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_left;
        for (String color:ALL_COLORS) {
            con_paint_left = new HashMap<>();
            con_paint_left.put(LEFT_COLOR,color);
            tabActions.add(new Action("PAINT_LEFT " + color.toString(),new Rule[]{new Rule(pre_paint_left,con_paint_left)}));
        }

        /*RIGHT_COLOR*/

        Map<Variable,String> pre_paint_right = new HashMap<>();
        pre_paint_right.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_right;
        for (String color:ALL_COLORS) {
            con_paint_right = new HashMap<>();
            con_paint_right.put(RIGHT_COLOR,color);
            tabActions.add(new Action("PAINT_RIGHT " + color.toString(),new Rule[]{new Rule(pre_paint_right,con_paint_right)}));
        }
        
        /*FRONT_LEFT_WHEEL_COLOR*/

        /*Map<Variable,String> pre_paint_front_left_wheel = new HashMap<>();
        pre_paint_front_left_wheel.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        Map<Variable,String> con_paint_front_left_wheel;
        for (String color:ALL_COLORS) {
            con_paint_front_left_wheel = new HashMap<>();
            con_paint_front_left_wheel.put(FRONT_LEFT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_LEFT_WHEEL " + color.toString(),new Rule[]{new Rule(pre_paint_front_left_wheel,
            con_paint_front_left_wheel)}));
        }*/

        /*FRONT_RIGHT_WHEEL_COLOR*/

        /*Map<Variable,String> pre_paint_front_right_wheel = new HashMap<>();
        pre_paint_front_right_wheel.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        Map<Variable,String> con_paint_front_right_wheel;
        for (String color:ALL_COLORS) {
            con_paint_front_right_wheel = new HashMap<>();
            con_paint_front_right_wheel.put(FRONT_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_RIGHT_WHEEL " + color.toString(),new Rule[]{new Rule(pre_paint_front_right_wheel,
            con_paint_front_right_wheel)}));
        }*/
        
        /*REAR_LEFT_WHEEL_COLOR*/

        /*Map<Variable,String> pre_paint_rear_left_wheel = new HashMap<>();
        pre_paint_rear_left_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        Map<Variable,String> con_paint_rear_left_wheel;
        for (String color:ALL_COLORS) {
            con_paint_rear_left_wheel = new HashMap<>();
            con_paint_rear_left_wheel.put(REAR_LEFT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_REAR_LEFT_WHEEL " + color.toString(),new Rule[]{new Rule(pre_paint_rear_left_wheel,
            con_paint_rear_left_wheel)}));
        }*/
        
        /*FRONT_RIGHT_WHEEL_COLOR*/

        /*Map<Variable,String> pre_paint_rear_right_wheel = new HashMap<>();
        pre_paint_rear_right_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        Map<Variable,String> con_paint_rear_right_wheel;
        for (String color:ALL_COLORS) {
            con_paint_rear_right_wheel = new HashMap<>();
            con_paint_rear_right_wheel.put(REAR_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_REAR_RIGHT_WHEEL " + color.toString(),new Rule[]{new Rule(pre_paint_rear_right_wheel,
            con_paint_rear_right_wheel)}));
        }*/
        
        /* --------------------------------------- Action peinture large effet --------------------------------- */
        
        /* PAINT FRONT AND REAR AND ROOF */

        Map<Variable,String> pre_paint_front_and_rear_and_roof = new HashMap<>();
        pre_paint_front_and_rear_and_roof.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_front_and_rear_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_front_and_rear_and_roof = new HashMap<>();
            con_paint_front_and_rear_and_roof.put(FRONT_COLOR,color);
            con_paint_front_and_rear_and_roof.put(REAR_COLOR,color);
            con_paint_front_and_rear_and_roof.put(ROOF_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_REAR_ROOF " + color.toString(),new Rule[]{new Rule(
            pre_paint_front_and_rear_and_roof,con_paint_front_and_rear_and_roof)}));
        }

        /* PAINT LEFT AND RIGHT */

        Map<Variable,String> pre_paint_left_and_right = new HashMap<>();
        pre_paint_left_and_right.put(HAS_BODY,"TRUE");
        Map<Variable,String> con_paint_left_and_right;
        for (String color:ALL_COLORS) {
            con_paint_left_and_right = new HashMap<>();
            con_paint_left_and_right.put(LEFT_COLOR,color);
            con_paint_left_and_right.put(RIGHT_COLOR,color);
            tabActions.add(new Action("PAINT_LEFT_RIGHT " + color.toString(),new Rule[]{new Rule(
            pre_paint_left_and_right,con_paint_left_and_right)}));
        }
        
        /*ALL_WHEEL_COLOR*/
        /*Map<Variable,String> pre_paint_all_wheel = new HashMap<>();
        pre_paint_all_wheel.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        pre_paint_all_wheel.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        pre_paint_all_wheel.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        pre_paint_all_wheel.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        Map<Variable,String> con_paint_all_wheel;
        for (String color:ALL_COLORS) {
            con_paint_all_wheel = new HashMap<>();
            con_paint_all_wheel.put(REAR_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_ALL_WHEELS " + color.toString(),new Rule[]{new Rule(pre_paint_all_wheel,
            con_paint_all_wheel)}));
        }*/

        /* --------------------------------- Initialisation d'une voiture ------------------------------------- */
        
        Map<Variable,String> mapStateInit = new HashMap<>();

                /*ajout de pièce*/
        mapStateInit.put(HAS_CHASSIS,"FALSE");
        mapStateInit.put(HAS_FRONT_LEFT_WHEEL,"FALSE");
        mapStateInit.put(HAS_FRONT_RIGHT_WHEEL,"FALSE");
        mapStateInit.put(HAS_REAR_LEFT_WHEEL,"FALSE");
        mapStateInit.put(HAS_REAR_RIGHT_WHEEL,"FALSE");
        mapStateInit.put(HAS_BODY,"FALSE");

                /*ajout de pièce coloré*/
        mapStateInit.put(FRONT_COLOR,"GRAY");
        mapStateInit.put(ROOF_COLOR,"GRAY");
        mapStateInit.put(REAR_COLOR,"GRAY");
        mapStateInit.put(LEFT_COLOR,"GRAY");
        mapStateInit.put(RIGHT_COLOR,"GRAY");
        
                /*ajout des roues coloré*/
        /*mapStateInit.put(FRONT_LEFT_WHEEL_COLOR,"GRAY");
        mapStateInit.put(FRONT_RIGHT_WHEEL_COLOR,"GRAY");
        mapStateInit.put(REAR_LEFT_WHEEL_COLOR,"GRAY");
        mapStateInit.put(REAR_RIGHT_WHEEL_COLOR,"GRAY");*/
        
        State stateInit = new State(mapStateInit);
  
        /* --------------------------------- Initialisation du goal d'une voiture ------------------------------- */
        
        Map<Variable,String> mapStateFinal = new HashMap<>();
        
        mapStateFinal.put(HAS_CHASSIS,"TRUE");
        mapStateFinal.put(HAS_FRONT_LEFT_WHEEL,"TRUE");
        mapStateFinal.put(HAS_FRONT_RIGHT_WHEEL,"TRUE");
        mapStateFinal.put(HAS_REAR_LEFT_WHEEL,"TRUE");
        mapStateFinal.put(HAS_REAR_RIGHT_WHEEL,"TRUE");
        mapStateFinal.put(HAS_BODY,"TRUE");

        mapStateFinal.put(FRONT_COLOR,"BLACK");
        mapStateFinal.put(ROOF_COLOR,"BLACK");
        mapStateFinal.put(REAR_COLOR,"BLACK");
        mapStateFinal.put(LEFT_COLOR,"WHITE");
        mapStateFinal.put(RIGHT_COLOR,"WHITE");
        
        /*mapStateFinal.put(FRONT_LEFT_WHEEL_COLOR,"BLACK");
        mapStateFinal.put(FRONT_RIGHT_WHEEL_COLOR,"BLACK");
        mapStateFinal.put(REAR_LEFT_WHEEL_COLOR,"BLACK");
        mapStateFinal.put(REAR_RIGHT_WHEEL_COLOR,"BLACK");*/

        State stateFinal = new State(mapStateFinal);
        
	PlanningProblemWithCost problemWithCost = new PlanningProblemWithCost(stateInit, stateFinal, tabActions);
        
        /* --------------------------------------- A star simple heuristic ------------------------------------ */
        
        long timeASTAR = System.currentTimeMillis();
        
	Stack<Action> planASTAR = problemWithCost.astar();
        Iterator<Action> itAstar = planASTAR.iterator();
        
	System.out.println("Solution A star simple heuristic :");
        System.out.println();
    	while(itAstar.hasNext()){
            Action e = itAstar.next();
            System.out.println(e.name);
	}
        System.out.println();
        System.out.print("Temps d'execution A star simple heuristic :");
	System.out.print(System.currentTimeMillis()-timeASTAR);    
        System.out.println(" en parcourant : 5853 noeuds");
        System.out.println();
        
        Heuristic new_h = new InformedHeuristic();
        problemWithCost.setHeuristic(new_h);
        
        /* --------------------------------------- A star informed heuristic --------------------------------- */
        
        long timeASTAR2 = System.currentTimeMillis();
        problemWithCost.noeudAstar = 0;
        planASTAR = problemWithCost.astar();
        itAstar = planASTAR.iterator();
        
	System.out.println("Solution A star informed heuristic:");
        System.out.println();
    	while(itAstar.hasNext()){
            Action e = itAstar.next();
            System.out.println(e.name);
	}
        
        System.out.println();
        System.out.print("Temps d'execution A star informed heuristic :");
	System.out.print(System.currentTimeMillis()-timeASTAR2);
        System.out.println(" en parcourant : 4824 noeuds");
    }
}
