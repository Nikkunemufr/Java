package Plateau;

import static Parametres.FonctionCommune.generateRandom;
import static Plateau.Plateau.nbLigne;

/**
 * Gestion de la map Plateau avec mur
 *
 * La classe implémente l'interface StrategyPlateau
 */

public class PlateauAvecMur implements StrategyPlateau {

    /**
     * Initialise les cases du plateau de jeu en plaçant au hasard un nombre de murs en rapport avec la grandeur du plateau de jeu
     * @return Un plateau de jeu
     */

    @Override
    public Case[][] initPlateau() {
        int nbDeMur = generateRandom(nbLigne,nbLigne*2);
        Case[][] plateau = new Case[nbLigne][nbLigne];
        for(int y=0;y<nbLigne;y++){
            for(int x=0;x<nbLigne;x++){
                plateau[x][y] = new Case(x,y);
            }
        }
        for (int i=0;i<nbDeMur;i++){
            plateau[generateRandom(0,nbLigne-1)][generateRandom(0,nbLigne-1)].addMur();
        }
        return plateau;
    }
}