package ppc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import representations.Variable;
import representations.Constraint;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class Backtracking {

    private final Map<Variable, Set<String>> tabVariable;
    private final Constraint[] tabConstraint;
    private final Iterator<String>[] tabIt;
    private final Variable[] vartab;                                    //Tableau de Variable avec des valeurs original
    private final Map<Variable, String> solution = new HashMap<>();
    private final Set<Map<Variable, String>> open = new HashSet<>();
    private int i = 0;                                                  //index de déplacement dans le tableau d'iterator
    public int compteur = 0;                                            // Entier qui permet de compter le nombre de fois que l'on passe dans un noeud de l'algorithme Backtrack

    /**
     *
     * Creer un objet permettant de trouver une solution en fonction d'une
     * attribution de valeur et de plusieurs conditions.
     *
     * @param mapVariable map contenant totues les valeurs d'une Variable
     * @param tabConstraint tableau de condition
     */
    public Backtracking(Map<Variable, Set<String>> mapVariable, Constraint[] tabConstraint) {
        this.tabVariable = mapVariable;
        this.tabConstraint = tabConstraint;
        tabIt = new Iterator[this.tabVariable.size() + 1];
        vartab = new Variable[this.tabVariable.size()];
        initialisation_Iterator();
    }

    /**
     *
     * Méthode permettant d'initialiser un tableau d'iterateur, qui permettent
     * de se déplacer dans le domaine de valeur d'une Variable.
     *
     */
    private void initialisation_Iterator() {
        int index = 0;

        for (Map.Entry<Variable, Set<String>> entry : tabVariable.entrySet()) {
            tabIt[index] = entry.getValue().iterator();
            vartab[index] = entry.getKey();
            index++;
        }
    }

    /**
     *
     * Algorithme de recherche, qui permet de trouver une solution en fonction
     * d'une ou plusieurs condition.
     *
     * @return une solution trouvé
     */
    public Map<Variable, String> backtrack() {
        compteur++;
        if (0 <= i && i < tabVariable.size()) {
            while (tabIt[i].hasNext()) {
                solution.put(vartab[i], tabIt[i].next());
                if (verification(tabConstraint, solution)) {
                    tabIt[++i] = (i < tabVariable.size()) ? tabVariable.get(vartab[i]).iterator() : null;
                    return backtrack();
                }
            }
            solution.remove(vartab[i--]);
            return backtrack();
        }
        if (i-- == tabVariable.size()) {
            return solution;
        }
        return new HashMap<>();
    }

    /**
     *
     * Amélioration de la méthode backtrack, celle-ci possède une méthode de
     * filtrage afin d'affiner la recherche dans les branches de l'arbre.
     *
     * @return une solution trouvé
     */
    public Map<Variable, String> backtrackWithFilter() {
        compteur++;
        if (i < tabVariable.size() && 0 <= i) {
            while (tabIt[i].hasNext()) {
                solution.put(vartab[i], tabIt[i].next());
                if (verification(tabConstraint, solution) && verificationFilter(tabConstraint, solution)) {
                    nextValue();
                    tabIt[i] = (i < tabVariable.size()) ? tabVariable.get(vartab[i]).iterator() : null;
                    return backtrackWithFilter();
                }
            }
            solution.remove(vartab[i]);
            previousValue();
            return backtrackWithFilter();
        }
        if (i == tabVariable.size()) {
            if (setrouve()) {
                previousValue();
                Map<Variable, String> string = new HashMap<>();
                for (Map.Entry<Variable, String> entry : solution.entrySet()) {
                    string.put(entry.getKey(), entry.getValue());
                }
                open.add(string);
                return solution;
            } else {
                previousValue();
                return backtrackWithFilter();
            }
        }
        return new HashMap<>();
    }

    /**
     *
     * Méthode permettant de vérifier si une assignation est dans la liste des
     * assignations valide.
     *
     * @return une valeur boolèene vérifiant que la solution est dans la liste
     */
    private boolean setrouve() {
        Iterator<Map<Variable, String>> ittest = open.iterator();
        while (ittest.hasNext()) {
            Map<Variable, String> map = ittest.next();
            if (map.equals(solution)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Méthode permettant d'identifier la prochaine Variable a attribué.
     *
     */
    private void nextValue() {
        int index = 0;
        while (index < tabVariable.size() && solution.containsKey(vartab[index])) {
            index++;
        }
        i = index;
    }

    /**
     *
     * Méthode permettant d'identifier la précedente Variable qui est attribué.
     *
     */
    private void previousValue() {
        int index = tabVariable.size() - 1;
        while (index >= 0 && !solution.containsKey(vartab[index])) {
            index--;
        }
        i = index;
    }

    /* NEW HEURISTIC*/
    /**
     *
     * Méthode permettant de changer d'heuristique de recherche pour le
     * backtrack.
     *
     */
    private void nextLittleValue() {
        int index = 0;
        int tampon = 10000;
        int new_i = -1;
        while (index < tabVariable.size()) {
            if (tampon > tabVariable.get(vartab[index]).size() && !solution.containsKey(vartab[index])) {
                tampon = tabVariable.get(vartab[index]).size();
                new_i = index;
            }
            System.out.println(tampon + " " + tabVariable.get(vartab[index]).size());
            index++;
        }
        if (new_i != -1) {
            i = new_i;
        } else {
            i = 0;
        }
    }

    /**
     *
     * Méthode de vérification permettant de tester toute les contraintes en
     * fonction de l'assignation.
     *
     * @param tabConstraint le tableau de contrainte
     * @param solution une assignation
     * @return une valeur booléene correspondant au test des conditions
     */
    private boolean verification(Constraint[] tabConstraint, Map<Variable, String> solution) {
        Set<Variable> scope;
        boolean test = true;
        boolean verif;

        for (Constraint r : tabConstraint) {
            scope = r.getScope();
            verif = false;

            for (Variable e : scope) {
                if (!(solution.containsKey(e))) {
                    verif = true;
                    break;
                }
            }

            verif = verif == true ? verif : r.isSatisfiedBy(solution);
            test &= verif;
        }
        return test;
    }

    /**
     *
     * Méthode vérifiant si le domaine des valeurs peuvent être réduit afin
     * d'améliorer la recherche du backtracking.
     *
     * @param tabConstraint le tableau des contraintes
     * @param solution une assignation
     * @return la validation d'une assignation
     */
    private boolean verificationFilter(Constraint[] tabConstraint, Map<Variable, String> solution) {
        boolean filter = true;
        int index = 0;
        ArrayList<Variable> listVariable = new ArrayList<>();

        while (index < vartab.length) {
            tabVariable.put(vartab[index], vartab[index].getDomaine());
            index++;
        }

        while (filter && i < vartab.length - 1) {
            filter = false;

            for (Constraint r : tabConstraint) {
                filter |= r.filter(solution, tabVariable);
                if (filter) {
                    for (Variable e : r.getScope()) {
                        if (tabVariable.get(e).size() == 1) {
                            Iterator<String> it = tabVariable.get(e).iterator();
                            String eDomaine = it.next();
                            solution.put(e, eDomaine);
                            listVariable.add(e);
                            if (!verification(tabConstraint, solution)) {
                                tabVariable.put(vartab[i], new HashSet<>(vartab[i].getDomaine()));
                                Iterator<Variable> itList = listVariable.iterator();
                                while (itList.hasNext()) {
                                    Variable var = itList.next();
                                    tabVariable.put(var, new HashSet<>(var.getDomaine()));
                                    solution.remove(var);

                                }
                                return false;
                            }
                        } else if (tabVariable.get(e).size() == 0) {
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }
}
