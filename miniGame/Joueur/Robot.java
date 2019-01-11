package Joueur;

import Plateau.Plateau;

import java.awt.*;

import static Parametres.Parametres.energieDebutTour;

/**
 * Classe de gestion des joueurs robots
 *
 * La classe hérite de la classe Joueur
 *
 * Un joueur robot est caractérisé par :
 * - Une ia qui décidera de ses actions
 */

public class Robot extends Joueur {

    public StrategyJoueur ia;

    /**
     * Constructeur de la classe Robot
     * @param id Numéro de joueur
     * @param color Couleur du joueur
     * @param ia Le choix de l'ia
     */

    public Robot(int id, Color color, StrategyJoueur ia) {
        super(id,color);
        this.ia = ia;
    }

    /**
     * Les actions par défaut qui doivent être faites quand un joueur commence son tour
     * @param plateau Les données du modèle
     */

    @Override
    public void actionDebutTour(Plateau plateau){
        this.energie += energieDebutTour;
        this.bouclier = false;
        this.ia.executeIA(plateau,this);
    }
}
