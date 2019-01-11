package Plateau;

import Action.*;
import Interface.EcouteurModele;
import Joueur.*;
import Joueur.Robot;

import java.util.ArrayList;

import static Parametres.FonctionCommune.generateRandom;
import static Parametres.Parametres.*;

/**
 * Classe de gestion du modèle
 *
 * La classe hérite de la classe AbstractModeleEcoutable
 *
 * Un plateau est caractérisé par :
 * - Un plateau de jeu composé de cases
 * - Un tableau des joueurs humains
 * - Un tableau des joueurs robots
 * - Un tableau avec tout les joueurs qui représente l'ordre des tours de jeu
 * - Un index pour savoir ou l'on est dans le tableau des tours de jeu
 * - Le joueur qui joue en ce moment
 * - Le nombre de ligne du plateau de jeu
 * - Le choix de la map
 * - Le choix de l'IA des robots
 * - Un gagnant si le jeu est fini
 */

public class Plateau extends AbstractModeleEcoutable{

    public Case[][] plateauDeJeu;
    public ArrayList<Humain> tabJoueursHumain;
    public ArrayList<Robot> tabJoueursRobot;
    public ArrayList<Joueur> tabTourDeJeu;
    public int indexJoueurs;
    public Joueur joueurQuiDoitJouer;

    public static int nbLigne;

    public Tir tir;

    private StrategyPlateau strategyPlateau;
    private StrategyJoueur strategyJoueur;

    public Joueur winner;

    /**
     * Constructeur de la classe Plateau
     */

    public Plateau(){
        this.tabJoueursHumain = new ArrayList<Humain>();
        this.tabJoueursRobot = new ArrayList<Robot>();
        this.tabTourDeJeu = new ArrayList<Joueur>();
        this.indexJoueurs = -1;

        this.winner = null;

        this.tir = new Tir();
    }

    /**
     * Envoie aux écouteurs la mise a jour du modèle
     */

    public void notifyObserver(){
        verificationVictoire();
        nouveauTour();
        for (EcouteurModele ecouteur : ecouteurs) {
            ecouteur.modeleMisAJour(this);
        }
    }

    /**
     * Reçois les paramètres du jeu pour ensuite initialiser le plateau, les joueurs et commencer la partie
     * @param nbHumain Le nombre de joueur humain
     * @param nbRobot  Le nombre de joueur robot
     * @param choixIA  Le choix de l'IA des robots
     * @param choixMap Le choix de la map
     */

    public void initPlateau(int nbHumain, int nbRobot, String choixIA, String choixMap){

        /* Choix du nombre de ligne */
        this.nbLigne = nbHumain+nbRobot < 10 ? 10 : nbHumain+nbRobot;

        /* Choix de l'IA */
        switch (choixIA){
            case "Facile":
                this.strategyJoueur = new IAFacile();
                break;
            case "Normal":
                this.strategyJoueur = new IANormal();
                break;
            case "Difficile":
                this.strategyJoueur = new IADifficile();
                break;
        }

        /* Choix de la map */
        switch (choixMap){
            case "Sans mur":
                this.strategyPlateau = new PlateauSansMur();
                break;
            case "Avec mur":
                this.strategyPlateau = new PlateauAvecMur();
                break;
        }

        this.plateauDeJeu = this.strategyPlateau.initPlateau();

        initJoueur(nbHumain,nbRobot);
        initTourDeJeu();
        makeJoueurIntoPlateau();
        notifyObserver();
    }

    /**
     * Création des joueurs
     * @param nbHumain Nombre de joueurs humains
     * @param nbRobot Nombre de joueurs robots
     */

    public void initJoueur(int nbHumain, int nbRobot){
        for(int i=0;i<nbHumain;i++){
            this.tabJoueursHumain.add(new Humain((i+1), colorHumain));
        }
        for(int i=0;i<nbRobot;i++){
            this.tabJoueursRobot.add(new Robot((i+1+tabJoueursHumain.size()),colorRobot,new IAFacile()));
        }
    }

    /**
     * Initialisation du tableau des tours de jeu
     */

    public void initTourDeJeu(){
        for (Joueur j:this.tabJoueursHumain) {
            this.tabTourDeJeu.add(j);
        }
        for (Joueur j:this.tabJoueursRobot) {
            this.tabTourDeJeu.add(j);
        }
    }

    /**
     * Place les joueurs au hasard sur le plateau
     */

    public void makeJoueurIntoPlateau(){
        for (Joueur j:this.tabJoueursHumain) {
            while(!this.plateauDeJeu[generateRandom(0,nbLigne-1)][generateRandom(0,nbLigne-1)].addJoueur(j)){}
        }
        for (Joueur j:this.tabJoueursRobot) {
            while(!this.plateauDeJeu[generateRandom(0,nbLigne-1)][generateRandom(0,nbLigne-1)].addJoueur(j)){}
        }
    }

    /**
     * Donne le tour au prochain joueur
     */

    public void nouveauTour(){
        this.indexJoueurs++;
        if(this.indexJoueurs >= this.tabTourDeJeu.size()){
            this.indexJoueurs = 0;
        }
        this.joueurQuiDoitJouer = this.tabTourDeJeu.get(this.indexJoueurs);

        reductionDelaiBombe(this.joueurQuiDoitJouer);

        if(this.joueurQuiDoitJouer.estMort){
            nouveauTour();
        } else {
            this.joueurQuiDoitJouer.actionDebutTour(this);
        }
    }

    /**
     * Vérifie si la partie est finie
     */

    public void verificationVictoire(){
        ArrayList<Joueur> tabDeJoueurVivant = new ArrayList<>();
        for (Joueur j:this.tabTourDeJeu) {
            if(!j.estMort)
                tabDeJoueurVivant.add(j);
        }
        if(tabDeJoueurVivant.size() == 1){
            this.winner = tabDeJoueurVivant.get(0);
        }
    }

    /**
     * Check toutes les bombes pour réduire leur délai en fonction du joueur qui joue
     * @param joueurQuiJoue Joueur qui doit jouer
     */

    public void reductionDelaiBombe(Joueur joueurQuiJoue){
        for (Case[] tabCase:this.plateauDeJeu) {
            for (Case c:tabCase) {
                if(c.containBombe != null){
                    c.containBombe.retirerUnTour(this.plateauDeJeu,joueurQuiJoue);
                }
            }
        }
    }

    /**
     * Création et gestion d'un déplacement
     * @param joueur Joueur qui doit se déplacer
     * @param caseDepart Case de départ
     * @param caseFinal Case d'arrivée
     * @return true si le déplacement à été effectué
     */

    public boolean deplacement(Joueur joueur,Case caseDepart,Case caseFinal){
        Deplacement deplacement = new Deplacement(joueur,caseDepart,caseFinal);
        if(deplacement.deplaceJoueur(this.plateauDeJeu)){
            notifyObserver();
            return true;
        }
        return false;
    }

    /**
     * Gestion d'un tir
     * @param joueurQuiTire Joueur qui effectue le tir
     * @param caseJoueur Case du joueur qui tir
     * @param caseCliquer Case choisi par le joueur
     * @return true si le tir à été effectué
     */

    public boolean tir(Joueur joueurQuiTire,Case caseJoueur,Case caseCliquer){
        if(this.tir.executeTir(joueurQuiTire,caseJoueur,caseCliquer,this.plateauDeJeu)){
            notifyObserver();
            return true;
        }
        return false;
    }

    /**
     * Création et pose d'une bombe
     * @param casePose Case où la bombe doit être posé
     * @param joueur Propriétaire de la bombe
     * @return true si la bombe à été posé
     */

    public boolean bombe(Case casePose,Joueur joueur){
        Bombe bombe = new Bombe(joueur);
        int diffX = joueur.position.x - casePose.x;
        int diffY = joueur.position.y - casePose.y;
        if(( diffX <= 1 && diffX >= -1 )&&( diffY <= 1 && diffY >= -1)){
            if(this.plateauDeJeu[casePose.x][casePose.y].addBombe(bombe)){
                joueur.energie -= bombe.cout;
                notifyObserver();
                return true;
            }
        }
        return false;
    }

    /**
     * Création et pose d'une mine
     * @param casePose Case où la mine doit être posé
     * @param joueur Propriétaire de la mine
     * @return true si la mine à été posé
     */

    public boolean mine(Case casePose,Joueur joueur){
        Mine mine = new Mine(joueur);
        MineInvisible mineInvisible = new MineInvisible(mine);
        int diffX = joueur.position.x - casePose.x;
        int diffY = joueur.position.y - casePose.y;
        if(( diffX <= 1 && diffX >= -1 )&&( diffY <= 1 && diffY >= -1)){
            if(this.plateauDeJeu[casePose.x][casePose.y].addMine(mineInvisible)){
                joueur.energie -= mine.cout;
                notifyObserver();
                return true;
            }
        }
        return false;
    }

    /**
     * Création d'un bouclier
     * @param joueur Joueur qui reçoit le bouclier
     * @return true si la pose du bouclier s'est bien passé
     */

    public boolean bouclier(Joueur joueur){
        Bouclier bouclier = new Bouclier();
        bouclier.mettreBouclier(joueur);
        notifyObserver();
        return true;
    }

    public boolean next(){
        notifyObserver();
        return true;
    }
}
