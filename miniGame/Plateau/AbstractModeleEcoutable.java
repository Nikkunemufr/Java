package Plateau;

import Interface.EcouteurModele;

import java.util.ArrayList;

/**
 * Classe abstraite pour les ajouts et retraits des écouteurs du modèle
 *
 * La classe implémente l'interface ModeleEcoutable
 *
 * La classe est caractérisée par :
 * - Une liste comprenant tout les écouteurs du modèle
 */

public abstract class AbstractModeleEcoutable implements ModeleEcoutable{

    ArrayList<EcouteurModele> ecouteurs =  new ArrayList<EcouteurModele>();

    /**
     * Ajoute un écouteur de la liste
     * @param e Un écouteur du modèle
     */

    public void ajoutEcouteur(EcouteurModele e){
        this.ecouteurs.add(e);
    }

    /**
     * Retire un écouteur de la liste
     * @param e Un écouteur du modèle
     */

    public void retraitEcouteur(EcouteurModele e){
        this.ecouteurs = new ArrayList<EcouteurModele>();
    }

}