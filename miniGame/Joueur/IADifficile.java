package Joueur;

import Plateau.*;

/**
 * La classe de gestion de l'IA Difficile
 *
 * La classe implémente StrategyJoueur pour ainsi utiliser le pattern Strategy
 */

public class IADifficile implements StrategyJoueur{

    /**
     * Décide de la prochaine action du robot
     * @param plateau Les données du modèle
     * @param joueur Le joueur robot
     */

    @Override
    public void executeIA(Plateau plateau, Joueur joueur) {
        plateau.nouveauTour();
    }
}
