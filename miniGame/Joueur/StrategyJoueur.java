package Joueur;

import Plateau.Plateau;

/**
 * Interface de gestion des StrategyJoueur
 *
 * Cette interface sert à l'implémentation du pattern Strategy
 */

public interface StrategyJoueur {

    /**
     * Décide de la prochaine action du robot
     * @param plateau Les données du modèle
     * @param joueur Le joueur robot
     */

    public void executeIA(Plateau plateau,Joueur joueur);

}
