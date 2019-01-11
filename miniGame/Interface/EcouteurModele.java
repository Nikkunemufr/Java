package Interface;

import Plateau.Plateau;

/**
 * Interface pour les écouteurs du modèle
 */

public interface EcouteurModele {

    /**
     * Met a jour les écouteurs du modèle
     * @param plateau Les données du modèle
     */

    public void modeleMisAJour(Plateau plateau);

}
