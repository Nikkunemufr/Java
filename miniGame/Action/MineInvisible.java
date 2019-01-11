package Action;

import Joueur.Joueur;

/**
 * Classe permettant l'implémentation du pattern Proxy
 * pour controler l'accès a la vision de la mine
 *
 * La classe hérite de la classe Explosif
 */

public class MineInvisible extends Explosif{

    /**
     * Constructeur de la classe MineInvisible
     * @param mine La mine qui doit être contrôlée
     */

    public MineInvisible(Mine mine) {
        super(mine.cout,mine.degats,mine.joueur);
    }

    /**
     * Gestion de la propriété de la mine
     * @param joueur Joueur qui doit jouer
     * @return true si le joueur est le propriétaire
     */

    @Override
    public Boolean CestMaMine(Joueur joueur) {
        if(joueur.equals(this.joueur)){
            return true;
        }
        return false;
    }
}
