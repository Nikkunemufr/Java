package Plateau;

import Action.Bombe;
import Action.Explosif;
import Joueur.Joueur;

/**
 * Classe de gestion des Cases
 *
 * Une case est caractérisée par :
 * - Une position x par rapport au plateau de jeu
 * - Une position y par rapport au plateau de jeu
 * - Une position x1 par rapport a l'interface graphique
 * - Une position x2 par rapport a l'interface graphique
 * - Une position y1 par rapport a l'interface graphique
 * - Une position y2 par rapport a l'interface graphique
 * - La présence d'un joueur
 * - La présence d'une mine
 * - La présence d'une bombe
 * - La présence d'un mur
 */

public class Case {

    public int x;
    public int y;

    public int positionGraphicX1;
    public int positionGraphicX2;
    public int positionGraphicY1;
    public int positionGraphicY2;

    public Joueur containJoueur;
    public Explosif containMine;
    public Bombe containBombe;
    public Mur containMur;

    /**
     * Constructeur de la classe Case
     * @param x position x par rapport au plateau de jeu
     * @param y position y par rapport au plateau de jeu
     */

    public Case(int x, int y){
        this.x = x;
        this.y = y;
        this.containJoueur = null;
        this.containMine = null;
        this.containBombe = null;
        this.containMur = null;

        this.positionGraphicX1 = 0;
        this.positionGraphicX2 = 0;
        this.positionGraphicY1 = 0;
        this.positionGraphicY2 = 0;
    }

    /**
     * Retire le joueur de la case
     */

    public void retirerJoueur(){
        this.containJoueur = null;
    }

    /**
     * Ajoute le joueur a la case
     * @param joueur le joueur qui veut rejoindre la case
     * @return true si le joueur a pu rejoindre la case
     */

    public Boolean addJoueur(Joueur joueur){
        if(this.containJoueur == null && this.containMur == null){
            this.containJoueur = joueur;
            joueur.position = this;
            return true;
        }
        return false;
    }

    /**
     * Retire la bombe de la case
     */

    public void retirerBombe(){
        this.containBombe = null;
    }

    /**
     * Ajoute une bombe a la case
     * @param bombe la bombe qui doit être poser
     * @return true si la pose de la bombe s'est bien passé
     */

    public Boolean addBombe(Bombe bombe){
        if(this.containBombe == null && this.containJoueur == null && this.containMur == null){
            this.containBombe = bombe;
            bombe.position = this;
            return true;
        }
        return false;
    }

    /**
     * Retire la mine de la case
     */

    public void retirerMine(){
        this.containMine = null;
    }

    /**
     * Ajoute une mine a la case
     * @param mine La mine qui doit être poser
     * @return true si la pose de la mine s'est bien passé
     */

    public Boolean addMine(Explosif mine){
        if(this.containMine == null && this.containJoueur == null && this.containMur == null){
            this.containMine = mine;
            mine.position = this;
            return true;
        }
        return false;
    }

    /**
     * Ajoute un mur a la case
     * @return true si la pose du mur s'est bien passé
     */

    public Boolean addMur(){
        if(this.containMine == null && this.containJoueur == null && this.containBombe == null){
            this.containMur = new Mur();
            return true;
        }
        return false;
    }
}
