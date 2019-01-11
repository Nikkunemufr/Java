package Action;

import Joueur.Joueur;

import static Parametres.Parametres.coutBouclier;

/**
 * Classe de gestion des boucliers
 *
 * La classe hérite de la classe Action
 *
 * Un bouclier est caractérisé par :
 * - Un cout en énergie
 */

public class Bouclier extends Action{

    /**
     * Constructeur de la classe Bouclier
     */

    public Bouclier() {
        super(coutBouclier);
    }

    /**
     * Pose du bouclier sur le joueur
     * @param joueur Joueur qui obtient le bouclier
     */

    public void mettreBouclier(Joueur joueur){
        joueur.bouclier = true;
        joueur.energie -= this.cout;
    }

}
