package Plateau;

import static Plateau.Plateau.nbLigne;

/**
 * Gestion de la map Plateau sans mur
 *
 * La classe implémente l'interface StrategyPlateau
 */

public class PlateauSansMur implements StrategyPlateau {

    /**
     * Initialise les cases du plateau de jeu sans mur
     * @return Un plateau de jeu
     */

    @Override
    public Case[][] initPlateau() {
        Case[][] plateau = new Case[nbLigne][nbLigne];
        for(int y=0;y<nbLigne;y++){
            for(int x=0;x<nbLigne;x++){
                plateau[x][y] = new Case(x,y);
            }
        }
        return plateau;
    }
}
