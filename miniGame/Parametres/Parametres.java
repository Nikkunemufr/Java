package Parametres;

import java.awt.*;

/**
 * Classe de gestion des paramètres du jeu
 */

public class Parametres {

    /* Paramètres du modèle */

    public final static int energieDebutTour = 2;
    public final static int energieDeDepart = 5;

    public static final int coutMine = 1;
    public static final int degatsMine = 5;

    public static final int coutTir = 2;
    public static final int degatsTir = 3;

    public static final int coutBombe = 1;
    public static final int degatsBombe = 5;

    public static final int coutBouclier = 2;

    public static final int coutDeplacement = 2;

    public final static int porteDeTir = 3;

    /* Paramètres graphiques */

    public final static int sizeEcran = 600;

    public static final Color colorSurbrillanceCase = Color.GRAY;
    public static final Color colorMur = Color.BLACK;

    public static final Color colorRobot = Color.GREEN;
    public static final Color colorHumain = Color.RED;

    public static final String actionDeplacement = "Déplacement";
    public static final String actionTir = "Tir";
    public static final String actionBombe = "Bombe";
    public static final String actionMine = "Mine";
    public static final String actionBouclier = "Bouclier";
    public static final String actionNext = "Next";

    public static final int positionXInformation = 150;
    public static final int positionYInformation = 60;

}
