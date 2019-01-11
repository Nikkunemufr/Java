package Joueur;

import Plateau.Case;
import Plateau.Plateau;

import java.awt.*;

import static Parametres.Parametres.energieDeDepart;
import static Parametres.Parametres.energieDebutTour;

/**
 * Classe abstraite de gestion des joueurs
 *
 * Un joueur est caractérisé par :
 * - Un montant d'énergie
 * - Un numéro
 * - La présence d'un bouclier
 * - La case où il se trouve
 * - Une couleur
 * - Le fait qu'il soit vivant ou non
 */

public abstract class Joueur {

    public int energie;
    public int id;
    public Boolean bouclier;
    public Case position;
    public Color color;
    public Boolean estMort;

    /**
     * Constructeur de la classe Joueur
     * @param id Le numéro du joueur
     * @param color La couleur du joueur
     */

    public Joueur(int id, Color color){
        this.energie = energieDeDepart;
        this.id = id;
        this.bouclier = false;
        this.position = null;
        this.color = color;
        this.estMort = false;
    }

    /**
     * Les actions par défaut qui doivent être faites quand un joueur commence son tour
     * @param plateau Les données du modèle
     */

    public void actionDebutTour(Plateau plateau){
        this.energie += energieDebutTour;
        this.bouclier = false;
    }
}
