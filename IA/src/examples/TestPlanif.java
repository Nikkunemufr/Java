package examples;

import planning.Action;
import planning.PlanningProblem;
import planning.State;
import representations.Rule;
import representations.Variable;

import java.util.*;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestPlanif {

    public static void main(String[] args) {

        AssemblyLine assemblyLine = new AssemblyLine();

        ArrayList<Action> tabActions = new ArrayList<>();

        Map<Variable,String> map_hasChassis = new HashMap<>();
        map_hasChassis.put(assemblyLine.HAS_CHASSIS,"TRUE");

        HashSet<String> ALL_COLORS = new HashSet<>();
        //ALL_COLORS.add("GRAY");
        ALL_COLORS.add("BLACK");
        ALL_COLORS.add("WHITE");
        ALL_COLORS.add("RED");
        ALL_COLORS.add("GREEN");
        ALL_COLORS.add("BLUE");
        ALL_COLORS.add("ORANGE");
        ALL_COLORS.add("YELLOW");

        /* ----------------------------- Action pièce unique ------------------------------- */

        /*INSTALL CHASSIS*/

        Map<Variable,String> pre_install_Chassis = new HashMap<>();
        pre_install_Chassis.put(assemblyLine.HAS_CHASSIS,"FALSE");
        Map<Variable,String> con_install_chassis = new HashMap<>();
        con_install_chassis.put(assemblyLine.HAS_CHASSIS,"TRUE");
        tabActions.add(new Action("INSTALL_CHASSIS",new Rule[]{new Rule(pre_install_Chassis,con_install_chassis)}));

        /*INSTALL FRONT LEFT WHEEL*/

        Map<Variable,String> pre_install_front_left_wheel = new HashMap<>();
        pre_install_front_left_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_install_front_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"FALSE");
        Map<Variable,String> con_install_front_left_wheel = new HashMap<>();
        con_install_front_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_FRONT_LEFT_WHEEL",new Rule[]{new Rule(
                pre_install_front_left_wheel,con_install_front_left_wheel)}));

        /*INSTALL FRONT RIGHT WHEEL*/

        Map<Variable,String> pre_install_front_right_wheel = new HashMap<>();
        pre_install_front_right_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_install_front_right_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"FALSE");
        Map<Variable,String> con_install_front_right_wheel = new HashMap<>();
        con_install_front_right_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_FRONT_RIGHT_WHEEL",new Rule[]{new Rule(
                pre_install_front_right_wheel,con_install_front_right_wheel)}));

        /*INSTALL REAR LEFT WHEEL*/

        Map<Variable,String> pre_install_rear_left_wheel = new HashMap<>();
        pre_install_rear_left_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_install_rear_left_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"FALSE");
        Map<Variable,String> con_install_rear_left_wheel = new HashMap<>();
        con_install_rear_left_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_REAR_LEFT_WHEEL",new Rule[]{new Rule(
                pre_install_rear_left_wheel,con_install_rear_left_wheel)}));

        /*INSTALL REAR RIGHT WHEEL*/

        Map<Variable,String> pre_install_rear_right_wheel = new HashMap<>();
        pre_install_rear_right_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_install_rear_right_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"FALSE");
        Map<Variable,String> con_install_rear_right_wheel = new HashMap<>();
        con_install_rear_right_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_REAR_RIGHT_WHEEL",new Rule[]{new Rule(
                pre_install_rear_right_wheel,con_install_rear_right_wheel)}));

        /* INSTALL BODY*/

        Map<Variable,String> pre_install_body = new HashMap<>();
        pre_install_body.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_install_body.put(assemblyLine.HAS_BODY,"FALSE");
        Map<Variable,String> con_install_body = new HashMap<>();
        con_install_body.put(assemblyLine.HAS_BODY,"TRUE");
        tabActions.add(new Action("INSTALL_BODY",new Rule[]{new Rule(
                pre_install_body,con_install_body)}));

        /* ----------------------------------- Action installation parallèle -------------------------------- */

        /* ACTION INSTALLATION 2 ROUES DE GAUCHES */

        Map<Variable,String> pre_rule_install_2_left_wheel = new HashMap<>();
        pre_rule_install_2_left_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_rule_install_2_left_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"FALSE");
        pre_rule_install_2_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"FALSE");
        Map<Variable,String> con_rule_install_2_left_wheel = new HashMap<>();
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"TRUE");
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_2_LEFT_WHEEL",new Rule[]{new Rule(
                pre_rule_install_2_left_wheel,con_rule_install_2_left_wheel)}));

        /* ACTION INSTALLATION 2 ROUES DE DROITES */

        Map<Variable,String> pre_rule_install_2_right_wheel = new HashMap<>();
        pre_rule_install_2_right_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_rule_install_2_right_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"FALSE");
        pre_rule_install_2_right_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"FALSE");
        Map<Variable,String> con_rule_install_2_right_wheel = new HashMap<>();
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"TRUE");
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_2_RIGHT_WHEEL",new Rule[]{new Rule(
                pre_rule_install_2_right_wheel,con_rule_install_2_right_wheel)}));

        /* ACTION INSTALLATION 2 ROUES ARRIERES */

        Map<Variable,String> pre_rule_install_2_rear_wheel = new HashMap<>();
        pre_rule_install_2_rear_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_rule_install_2_rear_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"FALSE");
        pre_rule_install_2_rear_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"FALSE");
        Map<Variable,String> con_rule_install_2_rear_wheel = new HashMap<>();
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_REAR_RIGHT_WHEEL,"TRUE");
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_REAR_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_2_REAR_WHEEL",new Rule[]{new Rule(
                pre_rule_install_2_rear_wheel,con_rule_install_2_rear_wheel)}));

        /* ACTION INSTALLATION 2 ROUES AVANT */

        Map<Variable,String> pre_rule_install_2_front_wheel = new HashMap<>();
        pre_rule_install_2_front_wheel.put(assemblyLine.HAS_CHASSIS,"TRUE");
        pre_rule_install_2_front_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"FALSE");
        pre_rule_install_2_front_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"FALSE");
        Map<Variable,String> con_rule_install_2_front_wheel = new HashMap<>();
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        con_rule_install_2_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"TRUE");
        tabActions.add(new Action("INSTALL_2_FRONT_WHEEL",new Rule[]{new Rule(
                pre_rule_install_2_front_wheel,con_rule_install_2_front_wheel)}));

        /* --------------------------------------- Action peinture précise --------------------------------- */

        /*FRONT_LEFT_WHEEL_COLOR;*/

        Map<Variable,String> pre_paint_front_left_wheel = new HashMap<>();
        pre_paint_front_left_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"TRUE");
        pre_paint_front_left_wheel.put(assemblyLine.FRONT_LEFT_WHEEL_COLOR,"GRAY");
        Map<Variable,String> con_paint_front_left_wheel;
        for (String color:ALL_COLORS) {
            con_paint_front_left_wheel = new HashMap<>();
            con_paint_front_left_wheel.put(assemblyLine.FRONT_LEFT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_LEFT_WHEEL",new Rule[]{new Rule(pre_paint_front_left_wheel,con_paint_front_left_wheel)}));
        }

        /*FRONT_RIGHT_WHEEL_COLOR;*/

        Map<Variable,String> pre_paint_front_right_wheel = new HashMap<>();
        pre_paint_front_right_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        pre_paint_front_right_wheel.put(assemblyLine.FRONT_RIGHT_WHEEL_COLOR,"GRAY");
        Map<Variable,String> con_paint_front_right_wheel;
        for (String color:ALL_COLORS) {
            con_paint_front_right_wheel = new HashMap<>();
            con_paint_front_right_wheel.put(assemblyLine.FRONT_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT_RIGHT_WHEEL",new Rule[]{new Rule(pre_paint_front_right_wheel,con_paint_front_right_wheel)}));
        }

        /*public Variable REAR_LEFT_WHEEL_COLOR;*/

        Map<Variable,String> pre_paint_left_rear_wheel = new HashMap<>();
        pre_paint_left_rear_wheel.put(assemblyLine.HAS_FRONT_LEFT_WHEEL,"TRUE");
        pre_paint_left_rear_wheel.put(assemblyLine.REAR_LEFT_WHEEL_COLOR,"GRAY");
        Map<Variable,String> con_paint_left_rear_wheel;
        for (String color:ALL_COLORS) {
            con_paint_left_rear_wheel = new HashMap<>();
            con_paint_left_rear_wheel.put(assemblyLine.FRONT_LEFT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_REAR_LEFT_WHEEL",new Rule[]{new Rule(pre_paint_left_rear_wheel,con_paint_left_rear_wheel)}));
        }

        /*public Variable REAR_RIGHT_WHEEL_COLOR;*/

        Map<Variable,String> pre_paint_right_rear_wheel = new HashMap<>();
        pre_paint_right_rear_wheel.put(assemblyLine.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        pre_paint_right_rear_wheel.put(assemblyLine.REAR_RIGHT_WHEEL_COLOR,"GRAY");
        Map<Variable,String> con_paint_right_rear_wheel;
        for (String color:ALL_COLORS) {
            con_paint_right_rear_wheel = new HashMap<>();
            con_paint_right_rear_wheel.put(assemblyLine.FRONT_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_REAR_RIGHT_WHEEL",new Rule[]{new Rule(pre_paint_right_rear_wheel,con_paint_right_rear_wheel)}));
        }

        /*public Variable FRONT_COLOR;*/

        Map<Variable,String> pre_paint_front = new HashMap<>();
        pre_paint_front.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_front.put(assemblyLine.FRONT_COLOR,"GRAY");
        Map<Variable,String> con_paint_front;
        for (String color:ALL_COLORS) {
            con_paint_front = new HashMap<>();
            con_paint_front.put(assemblyLine.FRONT_COLOR,color);
            tabActions.add(new Action("PAINT_FRONT",new Rule[]{new Rule(pre_paint_front,con_paint_front)}));
        }

        /*public Variable LEFT_COLOR;*/

        Map<Variable,String> pre_paint_left = new HashMap<>();
        pre_paint_left.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_left.put(assemblyLine.LEFT_COLOR,"GRAY");
        Map<Variable,String> con_paint_left;
        for (String color:ALL_COLORS) {
            con_paint_left = new HashMap<>();
            con_paint_left.put(assemblyLine.LEFT_COLOR,color);
            tabActions.add(new Action("PAINT_LEFT",new Rule[]{new Rule(pre_paint_left,con_paint_left)}));
        }

        /*public Variable REAR_COLOR;*/

        Map<Variable,String> pre_paint_rear = new HashMap<>();
        pre_paint_rear.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_rear.put(assemblyLine.REAR_COLOR,"GRAY");
        Map<Variable,String> con_paint_rear;
        for (String color:ALL_COLORS) {
            con_paint_rear = new HashMap<>();
            con_paint_rear.put(assemblyLine.REAR_COLOR,color);
            tabActions.add(new Action("PAINT_REAR",new Rule[]{new Rule(pre_paint_rear,con_paint_rear)}));
        }

        /*public Variable RIGHT_COLOR;*/

        Map<Variable,String> pre_paint_right = new HashMap<>();
        pre_paint_right.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_right.put(assemblyLine.RIGHT_COLOR,"GRAY");
        Map<Variable,String> con_paint_right;
        for (String color:ALL_COLORS) {
            con_paint_right = new HashMap<>();
            con_paint_right.put(assemblyLine.RIGHT_COLOR,color);
            tabActions.add(new Action("PAINT_RIGHT",new Rule[]{new Rule(pre_paint_right,con_paint_right)}));
        }

        /*public Variable ROOF_COLOR;*/

        Map<Variable,String> pre_paint_roof = new HashMap<>();
        pre_paint_roof.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_roof.put(assemblyLine.ROOF_COLOR,"GRAY");
        Map<Variable,String> con_paint_roof;
        for (String color:ALL_COLORS) {
            con_paint_roof = new HashMap<>();
            con_paint_roof.put(assemblyLine.ROOF_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF",new Rule[]{new Rule(pre_paint_roof,con_paint_roof)}));
        }

        /* Action peinture large effet */

        /* PAINT FRONT AND ROOF */

        Map<Variable,String> pre_paint_front_and_roof = new HashMap<>();
        pre_paint_front_and_roof.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_front_and_roof.put(assemblyLine.ROOF_COLOR,"GRAY");
        pre_paint_front_and_roof.put(assemblyLine.FRONT_COLOR,"GRAY");
        Map<Variable,String> con_paint_front_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_front_and_roof = new HashMap<>();
            con_paint_front_and_roof.put(assemblyLine.ROOF_COLOR,color);
            con_paint_front_and_roof.put(assemblyLine.FRONT_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF",new Rule[]{new Rule(pre_paint_front_and_roof,con_paint_front_and_roof)}));
        }

        /* PAINT REAR AND ROOF */

        Map<Variable,String> pre_paint_rear_and_roof = new HashMap<>();
        pre_paint_rear_and_roof.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_rear_and_roof.put(assemblyLine.REAR_COLOR,"GRAY");
        pre_paint_rear_and_roof.put(assemblyLine.ROOF_COLOR,"GRAY");
        Map<Variable,String> con_paint_rear_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_rear_and_roof = new HashMap<>();
            con_paint_rear_and_roof.put(assemblyLine.ROOF_COLOR,color);
            con_paint_rear_and_roof.put(assemblyLine.REAR_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF",new Rule[]{new Rule(pre_paint_rear_and_roof,con_paint_rear_and_roof)}));
        }

        /* PAINT ALL LEFT AND ROOF */

        Map<Variable,String> pre_paint_all_left_and_roof = new HashMap<>();
        pre_paint_all_left_and_roof.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_all_left_and_roof.put(assemblyLine.LEFT_COLOR,"GRAY");
        pre_paint_all_left_and_roof.put(assemblyLine.ROOF_COLOR,"GRAY");
        Map<Variable,String> con_paint_all_left_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_all_left_and_roof = new HashMap<>();
            con_paint_all_left_and_roof.put(assemblyLine.ROOF_COLOR,color);
            con_paint_all_left_and_roof.put(assemblyLine.LEFT_COLOR,color);
            con_paint_all_left_and_roof.put(assemblyLine.FRONT_LEFT_WHEEL_COLOR,color);
            con_paint_all_left_and_roof.put(assemblyLine.REAR_LEFT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF",new Rule[]{new Rule(pre_paint_all_left_and_roof,con_paint_all_left_and_roof)}));
        }

        /* PAINT ALL RIGHT AND ROOF */

        Map<Variable,String> pre_paint_all_right_and_roof = new HashMap<>();
        pre_paint_all_right_and_roof.put(assemblyLine.HAS_BODY,"TRUE");
        pre_paint_all_right_and_roof.put(assemblyLine.RIGHT_COLOR,"GRAY");
        pre_paint_all_right_and_roof.put(assemblyLine.ROOF_COLOR,"GRAY");
        Map<Variable,String> con_paint_all_right_and_roof;
        for (String color:ALL_COLORS) {
            con_paint_all_right_and_roof = new HashMap<>();
            con_paint_all_right_and_roof.put(assemblyLine.ROOF_COLOR,color);
            con_paint_all_right_and_roof.put(assemblyLine.RIGHT_COLOR,color);
            con_paint_all_right_and_roof.put(assemblyLine.FRONT_RIGHT_WHEEL_COLOR,color);
            con_paint_all_right_and_roof.put(assemblyLine.REAR_RIGHT_WHEEL_COLOR,color);
            tabActions.add(new Action("PAINT_ROOF",new Rule[]{new Rule(pre_paint_all_right_and_roof,con_paint_all_right_and_roof)}));
        }



        //System.out.println(stateInit.satisfies(assemblyLine.initState().mapState));

        Action actionInstallChassis = new Action("INSTALL_CHASSIS",new Rule[]{new Rule(pre_install_Chassis,map_hasChassis)});

        //System.out.println(planningProblem.is_applicable(actionInstallChassis,stateInit));

        //System.out.println(actionInstallChassis.apply(stateInit));

        //System.out.println(stateInit.toString());


        /*System.out.println(state.toString());
        System.out.println(state.satisfies(state3.mapState));*/

        State stateInit = assemblyLine.initState();

        State stateFinal = assemblyLine.generateCar();

        PlanningProblem planningProblem = new PlanningProblem(stateInit,stateFinal,tabActions);

        /*for(int i = 0;i<10000;i++){
            stateFinal = assemblyLine.generateCar();
            planningProblem = new PlanningProblem(stateInit,stateFinal,tabActions);
            Stack<Action> e = planningProblem.dfs(stateInit,new Stack<>(),new ArrayList<>());
            if(e != null){
                for (Action a:e) {
                    System.out.println(a.name);
                }
                break;
            }
        }*/

        Stack<Action> e = planningProblem.dfs(stateInit,new Stack<>(),new ArrayList<>());
        if(e != null){
            for (Action a:e) {
                System.out.println(a.name);
            }
        }

        //planningProblem.bfs();



    }

}