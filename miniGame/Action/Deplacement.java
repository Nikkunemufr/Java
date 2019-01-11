package Action;

import Joueur.Joueur;
import Plateau.Case;

import static Parametres.Parametres.coutDeplacement;

/**
 * Classe de gestion des Déplacements
 *
 * La classe hérite de la classe Action
 *
 * Un déplacement est caractérisé par :
 * - Le joueur qui se déplace
 * - La case de départ
 * - La case où le joueur doit arriver
 */

public class Deplacement extends Action{

    public Joueur joueur;
    public Case caseDepart;
    public Case caseFinal;

    /**
     * Constructeur de la classe Deplacement
     * @param joueur Joueur qui se déplace
     * @param caseDepart La case de départ
     * @param caseFinal La case d'arrivée
     */

    public Deplacement(Joueur joueur, Case caseDepart, Case caseFinal){
        super(coutDeplacement);
        this.joueur = joueur;
        this.caseDepart = caseDepart;
        this.caseFinal = caseFinal;
    }

    /**
     * Effectue le déplacement
     * @param plateauDeJeu Le plateau de jeu
     * @return true si le déplacement à été effectué
     */

    public Boolean deplaceJoueur(Case[][] plateauDeJeu){
        // On vérifie si le déplacement s'effectue sur une case adjacente
        int diffX = caseDepart.x - caseFinal.x;
        int diffY = caseDepart.y - caseFinal.y;
        if((diffX <= 1 && diffX >= -1 && diffY == 0) || (diffY <= 1 && diffY >= -1 && diffX == 0)){
            if(this.caseFinal.addJoueur(this.joueur)){ // Si l'ajout du joueur sur la case d'arrivé se passe bien
                verificationMineEtBombe(plateauDeJeu); // On vérifie si il y a une bombe
                this.caseDepart.retirerJoueur(); // On retire le joueur de sa case de départ
                this.joueur.energie -= this.cout; // Et on retire le cout du deplacement à l'énergie du joueur
                return true;
            }
        }
        return false;
    }

    /**
     * Verifie si il y a une mine ou une bombe sur la case d'arrivée, auquel cas la mine/bombe explose
     * @param plateauDeJeu Le plateau de jeu
     */

    public void verificationMineEtBombe(Case[][] plateauDeJeu){
        if(this.caseFinal.containBombe != null)
            this.caseFinal.containBombe.explosion(plateauDeJeu);
        if(this.caseFinal.containMine != null)
            this.caseFinal.containMine.explosion();
    }


}
