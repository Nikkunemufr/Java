package Action;

import Joueur.Joueur;

/**
 * Classe abstraite pour toutes les armes
 *
 * La classe hérite de de la classe Action
 *
 * Toutes les armes sont caractérisées par :
 * - Un cout en énergie
 * - Un montant de dégats
 */

public abstract class Armes extends Action {

    public int degats;

    /**
     * Constructeur de la classe Armes
     * @param cout    Cout en énergie de l'action
     * @param degats  Dégats de l'arme
     */

    public Armes(int cout,int degats){
        super(cout);
        this.degats = degats;
    }

    /**
     * Applique les dégats d'une arme a un joueur
     * @param j Joueur qui reçoit les dégats
     */

    public void infligeDegats(Joueur j){
        if(!j.bouclier){  // Si le joueur n'a pas de bouclier
            j.energie -= this.degats;  // On applique les dégats
        }
        if(j.energie <= 0){  // Si le joueur n'a plus d'énergie
            j.estMort = true;  // On le considère comme mort
            j.position.retirerJoueur();  // Et on le retire de la case du plateau
        }
    }
}
