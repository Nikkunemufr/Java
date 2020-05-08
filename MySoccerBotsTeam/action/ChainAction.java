package action;

import EDU.gatech.cc.is.abstractrobot.*;

public class ChainAction {

    protected AbstractAction action;
    protected ChainAction next;

    public ChainAction(AbstractAction action, ChainAction next) {
        this.action = action;
        this.next = next;
    }

    public boolean hasNext() {
        return (next != null);
    }

    /**
     * Retourne la meilleure action à prendre selon la stratégie
     */
    public void bestAction() {
        if (action.isActivable()) {
            action.action();
        } else {
            if (hasNext()) {
                next.bestAction();
            }
        }
    }
}