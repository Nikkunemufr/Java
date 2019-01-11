package examples;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import planning.State;
import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class AssemblyLine {

    public Set<String> BOOL;

    public Variable HAS_CHASSIS;
    public Variable HAS_FRONT_LEFT_WHEEL;
    public Variable HAS_FRONT_RIGHT_WHEEL;
    public Variable HAS_REAR_LEFT_WHEEL;
    public Variable HAS_REAR_RIGHT_WHEEL;
    public Variable HAS_BODY;

    public Set<String> ALL_COLORS;

    public Variable FRONT_LEFT_WHEEL_COLOR;
    public Variable FRONT_RIGHT_WHEEL_COLOR;
    public Variable REAR_LEFT_WHEEL_COLOR;
    public Variable REAR_RIGHT_WHEEL_COLOR;

    public Variable FRONT_COLOR;
    public Variable LEFT_COLOR;
    public Variable REAR_COLOR;
    public Variable RIGHT_COLOR;
    public Variable ROOF_COLOR;

    public AssemblyLine(){
        this.BOOL = new HashSet<>();
        this.BOOL.add("TRUE");
        this.BOOL.add("FALSE");

        this.HAS_CHASSIS = new Variable("HAS_CHASSIS",this.BOOL);
        this.HAS_FRONT_LEFT_WHEEL = new Variable("HAS_FRONT_LEFT_WHEEL",this.BOOL);
        this.HAS_FRONT_RIGHT_WHEEL = new Variable("HAS_FRONT_RIGHT_WHEEL",this.BOOL);
        this.HAS_REAR_LEFT_WHEEL = new Variable("HAS_REAR_LEFT_WHEEL",this.BOOL);
        this.HAS_REAR_RIGHT_WHEEL = new Variable("HAS_REAR_RIGHT_WHEEL",this.BOOL);
        this.HAS_BODY = new Variable("HAS_BODY",this.BOOL);

        this.ALL_COLORS = new HashSet<>();
        this.ALL_COLORS.add("GRAY");
        this.ALL_COLORS.add("BLACK");
        this.ALL_COLORS.add("WHITE");
        this.ALL_COLORS.add("RED");
        this.ALL_COLORS.add("GREEN");
        this.ALL_COLORS.add("BLUE");
        this.ALL_COLORS.add("ORANGE");
        this.ALL_COLORS.add("YELLOW");

        this.FRONT_LEFT_WHEEL_COLOR = new Variable("FRONT_LEFT_WHEEL_COLOR",this.ALL_COLORS);
        this.FRONT_RIGHT_WHEEL_COLOR = new Variable("FRONT_RIGHT_WHEEL_COLOR",this.ALL_COLORS);
        this.REAR_LEFT_WHEEL_COLOR = new Variable("REAR_LEFT_WHEEL_COLOR",this.ALL_COLORS);
        this.REAR_RIGHT_WHEEL_COLOR = new Variable("REAR_RIGHT_WHEEL_COLOR",this.ALL_COLORS);

        this.FRONT_COLOR = new Variable("FRONT_COLOR",this.ALL_COLORS);
        this.LEFT_COLOR = new Variable("LEFT_COLOR",this.ALL_COLORS);
        this.REAR_COLOR = new Variable("REAR_COLOR",this.ALL_COLORS);
        this.RIGHT_COLOR = new Variable("RIGHT_COLOR",this.ALL_COLORS);
        this.ROOF_COLOR = new Variable("ROOF_COLOR",this.ALL_COLORS);
    }

    public State initState(){
        Map<Variable,String> mapState = new HashMap<>();

        mapState.put(this.HAS_CHASSIS,"FALSE");
        mapState.put(this.HAS_FRONT_LEFT_WHEEL,"FALSE");
        mapState.put(this.HAS_FRONT_RIGHT_WHEEL,"FALSE");
        mapState.put(this.HAS_REAR_LEFT_WHEEL,"FALSE");
        mapState.put(this.HAS_REAR_RIGHT_WHEEL,"FALSE");
        mapState.put(this.HAS_BODY,"FALSE");

        mapState.put(this.FRONT_LEFT_WHEEL_COLOR,"GRAY");
        mapState.put(this.FRONT_RIGHT_WHEEL_COLOR,"GRAY");
        mapState.put(this.REAR_LEFT_WHEEL_COLOR,"GRAY");
        mapState.put(this.REAR_RIGHT_WHEEL_COLOR,"GRAY");

        mapState.put(this.FRONT_COLOR,"GRAY");
        mapState.put(this.LEFT_COLOR,"GRAY");
        mapState.put(this.REAR_COLOR,"GRAY");
        mapState.put(this.RIGHT_COLOR,"GRAY");
        mapState.put(this.ROOF_COLOR,"GRAY");

        return new State(mapState);
    }

    public State generateCar(){

        Map<Variable,String> mapState = new HashMap<>();

        mapState.put(this.HAS_CHASSIS,"TRUE");
        mapState.put(this.HAS_FRONT_LEFT_WHEEL,"TRUE");
        mapState.put(this.HAS_FRONT_RIGHT_WHEEL,"TRUE");
        mapState.put(this.HAS_REAR_LEFT_WHEEL,"TRUE");
        mapState.put(this.HAS_REAR_RIGHT_WHEEL,"TRUE");
        mapState.put(this.HAS_BODY,"TRUE");

        mapState.put(this.FRONT_LEFT_WHEEL_COLOR,
                nthElement(this.FRONT_LEFT_WHEEL_COLOR.domaine,generateRandom(0,this.FRONT_LEFT_WHEEL_COLOR.domaine.size()-1)));
        mapState.put(this.FRONT_RIGHT_WHEEL_COLOR,
                nthElement(this.FRONT_RIGHT_WHEEL_COLOR.domaine,generateRandom(0,this.FRONT_RIGHT_WHEEL_COLOR.domaine.size()-1)));
        mapState.put(this.REAR_LEFT_WHEEL_COLOR,
                nthElement(this.REAR_LEFT_WHEEL_COLOR.domaine,generateRandom(0,this.REAR_LEFT_WHEEL_COLOR.domaine.size()-1)));
        mapState.put(this.REAR_RIGHT_WHEEL_COLOR,
                nthElement(this.REAR_RIGHT_WHEEL_COLOR.domaine,generateRandom(0,this.REAR_RIGHT_WHEEL_COLOR.domaine.size()-1)));

        mapState.put(this.FRONT_COLOR,
                nthElement(this.FRONT_COLOR.domaine,generateRandom(0,this.FRONT_COLOR.domaine.size()-1)));
        mapState.put(this.LEFT_COLOR,
                nthElement(this.LEFT_COLOR.domaine,generateRandom(0,this.LEFT_COLOR.domaine.size()-1)));
        mapState.put(this.REAR_COLOR,
                nthElement(this.REAR_COLOR.domaine,generateRandom(0,this.REAR_COLOR.domaine.size()-1)));
        mapState.put(this.RIGHT_COLOR,
                nthElement(this.RIGHT_COLOR.domaine,generateRandom(0,this.RIGHT_COLOR.domaine.size()-1)));
        mapState.put(this.ROOF_COLOR,
                nthElement(this.ROOF_COLOR.domaine,generateRandom(0,this.ROOF_COLOR.domaine.size()-1)));

        return new State(mapState);
    }

    public int generateRandom(int min, int max){
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static <T> T nthElement(Iterable<T> data, int n){
        int index = 0;
        for(T element : data){
            if(index == n){
                return element;
            }
            index++;
        }
        return null;
    }

}
