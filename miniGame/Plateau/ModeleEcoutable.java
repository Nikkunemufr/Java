package Plateau;

import Interface.EcouteurModele;

/**
 * Interface pour les ajouts et retraits des écouteurs du modèle
 */

public interface ModeleEcoutable {

    /**
     * Ajoute un écouteur de la liste
     * @param e Un écouteur du modèle
     */

    public void ajoutEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur de la liste
     * @param e Un écouteur du modèle
     */

    public void retraitEcouteur(EcouteurModele e);

}

