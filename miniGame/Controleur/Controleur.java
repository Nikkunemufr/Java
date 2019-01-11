package Controleur;

import Plateau.*;
import Joueur.Joueur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe de gestion du controleur
 *
 * La classe implémente ActionListener
 *
 * Le controleur est caractérisé par :
 * - Un accès au modèle, ici le plateau
 */

public class Controleur implements ActionListener{

    private Plateau plateau;

    public JTextField nbHumain;
    public JTextField nbRobot;
    public JComboBox choixIA;
    public JComboBox choixMap;

    /**
     * Constructeur de la classe Controleur
     * @param plateau Accès au modèle
     */

    public Controleur(Plateau plateau){
        this.plateau = plateau;
    }

    /**
     * Initialise l'accès aux paramètres de la vue
     * @param nbHumain Nombre de joueurs humain
     * @param nbRobot  Nombre de joueurs robot
     * @param choixIA  Choix de la difficulté des robots
     * @param choixMap Choix de la map
     */

    public void setParam(JTextField nbHumain, JTextField nbRobot, JComboBox choixIA, JComboBox choixMap){
        this.nbHumain = nbHumain;
        this.nbRobot = nbRobot;
        this.choixIA = choixIA;
        this.choixMap = choixMap;
    }

    /**
     * Effectue des actions suivant l'ActionEvent
     * @param e L'ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Jouer": // Si le bouton jouer à été cliquer
                int nbHumain = Integer.parseInt(this.nbHumain.getText()); // On récupère le nombre de joueur Humain
                int nbRobot = Integer.parseInt(this.nbRobot.getText()); // On récupère le nombre de joueur robot
                if(nbRobot+nbHumain > 1){   // On peut pas commencer une partie avec moins de 2 joueurs
                    String choixIA = (String)this.choixIA.getItemAt(this.choixIA.getSelectedIndex()); // On récupère le choix de l'IA
                    String choixMap = (String)this.choixMap.getItemAt(this.choixMap.getSelectedIndex()); // On récupère le choix de la map

                    this.plateau.initPlateau(nbHumain,nbRobot,choixIA,choixMap); // On envoie tout au modèle
                }
                break;
        }
    }

    /**
     * Envoie une demande de déplacement au modèle
     * @param joueur Joueur qui se déplace
     * @param caseDepart Case de départ
     * @param caseFinal Case d'arrivée
     */

    public void deplacement(Joueur joueur,Case caseDepart,Case caseFinal){
        this.plateau.deplacement(joueur,caseDepart,caseFinal);
    }

    /**
     * Envoie une demande de tir au modèle
     * @param joueurQuiTire Joueur qui tire
     * @param caseJoueur Case du joueur qui tire
     * @param caseCliquer Case choisi par le joueur
     */

    public void tir(Joueur joueurQuiTire,Case caseJoueur, Case caseCliquer){
        this.plateau.tir(joueurQuiTire,caseJoueur,caseCliquer);
    }

    /**
     * Envoie une demande de pose de bombe au modèle
     * @param casePose Case ou le joueur veut poser la bombe
     * @param joueur Joueur qui veut poser la bombe
     */

    public void bombe(Case casePose,Joueur joueur){
        this.plateau.bombe(casePose,joueur);
    }

    /**
     * Envoie une demande de pose de mine au modèle
     * @param casePose Case ou le joueur veut poser une mine
     * @param joueur Joueur qui veut poser une mine
     */

    public void mine(Case casePose,Joueur joueur){
        this.plateau.mine(casePose,joueur);
    }

    /**
     * Envoie une demande de pose de bouclier au modèle
     * @param joueur Joueur qui doit recevoir le bouclier
     */

    public void bouclier(Joueur joueur){
        this.plateau.bouclier(joueur);
    }

    /**
     * Envoie une demande pour passer son tour au modèle
     */

    public void next(){
        this.plateau.next();
    }
}

