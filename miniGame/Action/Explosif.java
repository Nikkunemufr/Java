package Action;

import Joueur.Joueur;
import Plateau.Case;

/**
 * Classe de gestion des explosifs
 *
 * La classe hérite de la classe Armes
 *
 * Un explosif est caractérisé par :
 * - Un cout en énergie
 * - Un montant de dégats
 * - Une position sur le plateau de jeu
 * - Un propriétaire
 */

public abstract class Explosif extends Armes{

    public Case position;
    public Joueur joueur;

    /**
     * Constructeur de la classe Explosif
     * @param cout Cout en énergie
     * @param degats Un montant de dégats
     * @param joueur Le propriétaire de la bombe
     */

    public Explosif(int cout, int degats, Joueur joueur) {
        super(cout, degats);
        this.position = null;
        this.joueur = joueur;
    }

    /**
     * Gestion de la propriété de la mine
     * @param joueur Joueur qui doit jouer
     * @return true si le joueur est le propriétaire
     */

    public Boolean CestMaMine(Joueur joueur){
        return true;
    }

    /**
     * Comportement d'explosion de base pour un explosif
     * C'est a dire un explosif explose et inflige des dégats a la personne qui marche dessus
     */

    public void explosion(){
        if(this.position.containJoueur != null) {
            infligeDegats(this.position.containJoueur);
        }
        this.position.retirerMine();
    }

}
