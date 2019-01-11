package Action;

import Joueur.Joueur;
import Plateau.Case;

import static Parametres.Parametres.coutBombe;
import static Parametres.Parametres.degatsBombe;

/**
 * Classe de gestion des bombes
 *
 * La classe hérite de la classe Explosif
 *
 * Une bombe est caractérisée par :
 * - Un cout en énergie
 * - Un montant de dégats
 * - Un nombre de tour restant avant l'explosion
 */

public class Bombe extends Explosif {

    public int tourRestant;

    /**
     * Constructeur de la classe Bombe
     * @param joueur Propriétaire de la bombe
     */

    public Bombe(Joueur joueur) {
        super(coutBombe, degatsBombe,joueur);
        tourRestant = 6;
    }

    /**
     * Retire un tour à une bombe si c'est a son propriétaire de jouer
     * @param plateauDeJeu  Le plateau de jeu
     * @param joueurQuiJoue Le joueur qui doit jouer
     */

    public void retirerUnTour(Case[][] plateauDeJeu,Joueur joueurQuiJoue){
        if(this.joueur.equals(joueurQuiJoue)){ // Si c'est le propriétaire de la bombe
            this.tourRestant -= 1; // On retire un tour
            if(this.tourRestant <= 0){ // Et si le nombre de tour est arrivé à 0
                explosion(plateauDeJeu); // La bombe explose
            }
        }
    }

    /**
     * Applique les dégats aux joueurs qui se trouvent dans le rayon de la bombe
     * @param plateauDeJeu Le plateau de jeu
     */

    public void explosion(Case[][] plateauDeJeu){
        for (Case[] tabCase:plateauDeJeu) {
            for (Case c : tabCase) {  // On parcours les cases du plateau

                /* On regarde si la case se trouve dans le rayon de la bombe */

                int diffX = this.position.x - c.x;
                int diffY = this.position.y - c.y;
                if((( diffX <= 1 && diffX >= -1 )&&( diffY <= 1 && diffY >= -1)) || (diffX == 0 && diffY == 0)) {  // Si la bombe se trouve dans le rayon de la bombe
                    if(c.containJoueur != null) { // On regarde si elle contient un joueur
                        infligeDegats(c.containJoueur); // Pour lui infliger les dégats
                    }
                }
            }
        }
        this.position.retirerBombe(); // Puis on retire la bombe
    }

}
