package Plateau;

/**
 * Interface de gestion des StrategyPlateau
 *
 * Cette interface sert à l'implémentation du pattern Strategy
 */

public interface StrategyPlateau {

    /**
     * Initialise les cases d'un plateau de jeu
     * @return Le plateau de jeu
     */

    public Case[][] initPlateau();

}
