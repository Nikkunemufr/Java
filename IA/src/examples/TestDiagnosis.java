package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import representations.AllEqualConstraint;
import representations.Constraint;
import representations.IncompatibilityConstraint;
import representations.Variable;
import diagnosis.Diagnoser;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class TestDiagnosis {

    public static void main(String[] args) {

        Set<String> bool = new HashSet<String>();

        bool.add("1");
        bool.add("0");

        // Liste des variables
        Variable x1 = new Variable("x1", bool);
        Variable x2 = new Variable("x2", bool);
        Variable x3 = new Variable("x3", bool);
        Variable x4 = new Variable("x4", bool);

        ArrayList<Variable> listVar = new ArrayList<Variable>();
        listVar.add(x1);
        listVar.add(x2);
        listVar.add(x3);
        listVar.add(x4);

        // Liste des contraintes
        Map<Variable, String> incompatibilityConstraint = new HashMap<>();

        incompatibilityConstraint.put(x3, "0");
        incompatibilityConstraint.put(x4, "0");

        Set<Variable> allEqualConstraint = new HashSet<>();

        allEqualConstraint.add(x1);
        allEqualConstraint.add(x2);
        allEqualConstraint.add(x3);

        Constraint c1 = new IncompatibilityConstraint(incompatibilityConstraint);
        Constraint c2 = new AllEqualConstraint(allEqualConstraint);

        Constraint[] constraints = {c1, c2};

        // Test
        Diagnoser diag = new Diagnoser(listVar, constraints);

        diag.add(x1, "1");
        System.out.println("1");
        for (Variable var : diag.getExplanation(x1, "0").keySet()) {
            System.out.println(var.getNom());
        }
        diag.add(x2, "0");
        System.out.println("2");
        for (Variable var : diag.getExplanation(x2, "1").keySet()) {
            System.out.println(var.getNom());
        }
        diag.add(x3, "0");
        System.out.println("3");
        for (Variable var : diag.getExplanation(x3, "0").keySet()) {
            System.out.println(var.getNom());
        }
        diag.add(x4, "0");
        System.out.println("4");
        for (Variable var : diag.getExplanation(x4, "0").keySet()) {
            System.out.println(var.getNom());
        }

        //System.out.println(diag.getDiagnosis(x1, "0"));
    }
}
