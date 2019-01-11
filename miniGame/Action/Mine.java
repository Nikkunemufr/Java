package Action;

import Joueur.Joueur;

import static Parametres.Parametres.coutMine;
import static Parametres.Parametres.degatsMine;

/**
 * Classe de gestion des Mines
 *
 * La classe hérite de la classe Explosif
 *
 * Une mine est caractérisée par :
 * - Un cout en énergie
 * - Un montant de dégats
 * - Un propriétaire
 */

public class Mine extends Explosif {

    /**
     * Constructeur de la classe Mine
     * @param joueur Propriétaire de la mine
     */

    public Mine(Joueur joueur) {
        super(coutMine, degatsMine, joueur);
    }

}
