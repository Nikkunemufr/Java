package planning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import representations.Variable;

/**
 * @author Vincent DEMENEZES, Alexis MORTELIER, Alexandre LELOUTRE
 */
public class State implements Cloneable {

    public Map<Variable, String> mapState;

    /**
     *
     * Creer un etat avec une map de Variable et d'une valeur d'un domaine.
     *
     * @param mapState liste des valeurs de chaques variables de l'état
     */
    public State(Map<Variable, String> mapState) {
        this.mapState = mapState;
    }

    /**
     *
     * Permet d'obtenir la map des valeurs des variables d'un état.
     *
     * @return
     */
    public Map<Variable, String> getMapState() {
        return this.mapState;
    }

    /**
     *
     * Méthode permettant de vérifier si cette état est identique avec un état
     * fournis.
     *
     * @param partial_state état fournis
     * @return une valeur boolèene vérifiant si la contenance est identique d'un
     * état avec un autre état.
     */
    public Boolean satisfies(Map<Variable, String> partial_state) {
        for (Map.Entry<Variable, String> entry : partial_state.entrySet()) {
            if (!this.mapState.containsKey(entry.getKey()) || !this.mapState.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Méthode permettant de récuperer un état afin de pouvoir le modifier sans
     * modifier l'état original.
     *
     * @return un nouvel état
     */
    public State clone() {
        State o = null;
        try {
            o = (State) super.clone();
            o.mapState = new HashMap<Variable, String>(this.mapState);
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        return o;
    }

    /**
     *
     * Méthode permet d'obtenir la map d'un etat sous forme de String.
     *
     * @return une string correspondant à un état
     */
    @Override
    public String toString() {
        String text = "";
        Set<Map.Entry<Variable, String>> set = this.mapState.entrySet();
        Iterator<Map.Entry<Variable, String>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<Variable, String> e = it.next();
            text = text + e.getKey().nom + " -> " + e.getValue() + "\n";
        }
        return text;
    }

    /**
     *
     * Méthode de comparaison entre deux maps
     *
     * @param <K> correspond à la clé sans type particulier
     * @param <V> correspond à la valeur sans type particulier
     * @param map1 premiere map
     * @param map2 deuxieme map
     * @return une valeur booléenne correpondant à la vérification de la
     * comparaison entre deux maps
     */
    public static <K, V> boolean equalsMap(Map<K, V> map1, Map<K, V> map2) {
        for (K key : map1.keySet()) {
            if (map2.containsKey(key)) {
                if (!map1.get(key).equals(map2.get(key))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return map1.size() == map2.size();
    }
}
