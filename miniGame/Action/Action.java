package Action;

/**
 * Classe abstraite pour toutes les actions
 *
 * Toutes les actions sont caractérisées par :
 * - Un cout en énergie
 */

public abstract class Action {

    public int cout;

    /**
     * Constructeur de la classe Action
     * @param cout  Cout en énergie de l'action
     */

    public Action(int cout){
        this.cout = cout;
    }
}
