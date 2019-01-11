package Interface;

import Controleur.Controleur;
import Plateau.*;
import Joueur.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Parametres.Parametres.*;
import static Plateau.Plateau.nbLigne;

/**
 * Classe de gestion de l'affichage du jeu
 *
 * La classe hérite de JPanel et implémente ActionListener et MouseListener
 *
 * Cette classe est caractérisée par :
 * - Les données du modèle
 * - Un accès au controleur
 */

public class AffichagePlateau extends JPanel implements ActionListener,MouseListener {

    private Plateau plateau;
    private Controleur controleur;

    private Joueur joueurCourant;
    private Case caseCourante;
    private String actionEnCours;

    /**
     * Constructeur de la classe AffichagePlateau
     * @param plateau Les données du modèle
     * @param controleur L'accès au controleur
     */

    public AffichagePlateau(Plateau plateau,Controleur controleur){
        this.plateau = plateau;
        this.controleur = controleur;
        this.joueurCourant = this.plateau.joueurQuiDoitJouer;
        this.caseCourante = this.joueurCourant.position;
        this.actionEnCours = "";

        this.addMouseListener(this);

        if(this.plateau.winner == null) // On vérifie si la partie n'est pas encore gagnée
            afficheInterfaceJoueur();
    }

    /**
     * Dessine les composants graphiques
     * @param g Objet graphique
     */

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int tailleCase = (sizeEcran-(sizeEcran/3))/nbLigne;  // On calcule la taille de la case

        /* Affichage de la portée du tir */

        if(actionEnCours == actionTir){ // On regarde si le joueur a appuyé sur le bouton de tir
            g2.setColor(colorSurbrillanceCase);
            for (int porte=1;porte<=porteDeTir;porte++){
                int x = joueurCourant.position.x + porte;
                int y = joueurCourant.position.y;
                if(x>=0 && x<nbLigne){
                    if(this.plateau.plateauDeJeu[x][y].containMur != null)
                        break;
                    g2.fillRect(((sizeEcran / 3) / 2 + x * tailleCase), (sizeEcran / 3) / 2 + y * tailleCase,
                            tailleCase, tailleCase);
                }
            }
            for (int porte=1;porte<=porteDeTir;porte++){
                int x = joueurCourant.position.x - porte;
                int y = joueurCourant.position.y;
                if(x>=0 && x<nbLigne){
                    if(this.plateau.plateauDeJeu[x][y].containMur != null)
                        break;
                    g2.fillRect(((sizeEcran / 3) / 2 + x * tailleCase), (sizeEcran / 3) / 2 + y * tailleCase,
                            tailleCase, tailleCase);
                }

            }
            for (int porte=1;porte<=porteDeTir;porte++){
                int x = joueurCourant.position.x;
                int y = joueurCourant.position.y + porte;
                if(y>=0 && y<nbLigne){
                    if(this.plateau.plateauDeJeu[x][y].containMur != null)
                        break;
                    g2.fillRect(((sizeEcran / 3) / 2 + x * tailleCase), (sizeEcran / 3) / 2 + y * tailleCase,
                            tailleCase, tailleCase);
                }
            }
            for (int porte=1;porte<=porteDeTir;porte++){
                int x = joueurCourant.position.x;
                int y = joueurCourant.position.y - porte;
                if(y>=0 && y<nbLigne){
                    if(this.plateau.plateauDeJeu[x][y].containMur != null)
                        break;
                    g2.fillRect(((sizeEcran / 3) / 2 + x * tailleCase), (sizeEcran / 3) / 2 + y * tailleCase,
                            tailleCase, tailleCase);
                }
            }
        }

        /* Dessiner les joueurs, les mines, les bombes, les boucliers et les cases en surbrillances */

        for (Case[] tabCase:this.plateau.plateauDeJeu) {
            for (Case c:tabCase) {  // On parcours les cases du plateau

                if(c.containMur != null){ // Si y'a un mur on l'affiche
                    g2.setColor(colorMur);
                    g2.fillRect(((sizeEcran / 3) / 2 + c.x * tailleCase), (sizeEcran / 3) / 2 + c.y * tailleCase,
                            tailleCase, tailleCase);
                } else {                  // Si il n'y a pas de mur alors on regarde si il y a autre chose
                    switch (this.actionEnCours){ // On regarde si il y a une action en cours
                        case actionDeplacement:
                            if(c.containJoueur == null){
                                int diffX = this.joueurCourant.position.x - c.x;
                                int diffY = this.joueurCourant.position.y - c.y;
                                if(( diffX <= 1 && diffX >= -1 && diffY == 0)||( diffY <= 1 && diffY >= -1 && diffX == 0)){
                                    g2.setColor(colorSurbrillanceCase);
                                    g2.fillRect(((sizeEcran / 3) / 2 + c.x * tailleCase), (sizeEcran / 3) / 2 + c.y * tailleCase,
                                            tailleCase, tailleCase);
                                }
                            }
                            break;
                        case actionBombe:
                        case actionMine:
                            if(c.containJoueur == null){
                                int diffX = this.joueurCourant.position.x - c.x;
                                int diffY = this.joueurCourant.position.y - c.y;
                                if(( diffX <= 1 && diffX >= -1 )&&( diffY <= 1 && diffY >= -1)){
                                    g2.setColor(colorSurbrillanceCase);
                                    g2.fillRect(((sizeEcran / 3) / 2 + c.x * tailleCase), (sizeEcran / 3) / 2 + c.y * tailleCase,
                                            tailleCase, tailleCase);
                                }
                            }
                            break;
                    }

                    int positionX = (sizeEcran / 3) / 2 + c.x * tailleCase + tailleCase / 4;
                    int positionY = (sizeEcran / 3) / 2 + c.y * tailleCase + tailleCase / 4;

                    if(c.containJoueur != null){ // On regarde si la case contient un joueur
                        if(c.containJoueur.equals(this.joueurCourant)){
                            g2.setColor(Color.LIGHT_GRAY);
                            g2.fillRect((sizeEcran/3)/2+c.x*tailleCase,(sizeEcran/3)/2+c.y*tailleCase,
                                    tailleCase,tailleCase);
                        }
                        g2.setColor(c.containJoueur.color);
                        g2.fillOval(positionX, positionY, tailleCase / 2, tailleCase / 2);
                        g2.setColor(Color.WHITE);
                        if(c.containJoueur.id > 9){
                            g2.drawString(c.containJoueur.id+"",positionX+tailleCase/10,positionY+tailleCase/3);
                        } else {
                            g2.drawString(c.containJoueur.id+"",positionX+tailleCase/5,positionY+tailleCase/3);
                        }


                        if(c.containJoueur.bouclier){ // On regarde si le joueur a un bouclier
                            g2.setColor(Color.BLACK);
                            g2.drawRect(positionX, positionY, tailleCase / 2, tailleCase / 2);
                        }

                        g2.setColor(Color.BLACK);
                        g2.drawString(c.containJoueur.energie+"",positionX,positionY);
                    }

                    if(c.containBombe != null){ // On regarde si la case contient une bombe
                        g2.setColor(Color.BLACK);
                        g2.fillOval(positionX, positionY, tailleCase / 2, tailleCase / 2);
                        g2.drawString(c.containBombe.tourRestant+"",positionX,positionY);
                    }

                    if(c.containMine != null){ // On regarde si la case contient une mine
                        if(c.containMine.CestMaMine(this.joueurCourant)){
                            g2.setColor(Color.BLACK);
                            g2.fillRect(positionX, positionY, tailleCase / 2, tailleCase / 2);
                        }

                    }
                }
            }
        }

        /* Dessiner les traits noirs du plateau */

        for (Case[] tabCase:this.plateau.plateauDeJeu) {
            for (Case c:tabCase) {
                c.positionGraphicX1 = (sizeEcran/3)/2+c.x*tailleCase;
                c.positionGraphicX2 = (sizeEcran/3)/2+c.x*tailleCase+tailleCase;
                c.positionGraphicY1 = (sizeEcran/3)/2+c.y*tailleCase;
                c.positionGraphicY2 = (sizeEcran/3)/2+c.y*tailleCase+tailleCase;

                g2.setColor(Color.BLACK);
                g2.drawRect(c.positionGraphicX1,c.positionGraphicY1,tailleCase,tailleCase);
            }
        }

        /* Dessiner le texte d'information pour le joueur */

        String info = "";
        if(this.plateau.winner != null){
            info = "Le joueur " + this.plateau.winner.id + " a gagné la partie !";
        } else {
            switch (this.actionEnCours) {
                case "":
                    info = "Joueur " + this.joueurCourant.id + " à toi de jouer";
                    break;
                case actionDeplacement:
                    info = "Choisissez votre case de déplacement - Cout : " + coutDeplacement;
                    break;
                case actionTir:
                    info = "Choisissez votre direction de tir - Cout : " + coutTir + " Degats : " + degatsTir;
                    break;
                case actionBombe:
                    info = "Posez votre bombe sur une case vide - Cout : " + coutBombe + " Degats : " + degatsBombe;
                    break;
                case actionMine:
                    info = "Posez votre mine sur une case vide - Cout : " + coutMine + " Degats : " + degatsMine;
                    break;
            }
        }

        g2.setColor(Color.BLACK);
        g2.drawString(info,positionXInformation,positionYInformation);
    }

    /**
     * Crée et affiche les boutons pour le joueurs sur l'interface
     */

    public void afficheInterfaceJoueur(){
        JButton buttonDeplacement = new JButton(actionDeplacement);
        JButton buttonTir = new JButton(actionTir);
        JButton buttonBombe = new JButton(actionBombe);
        JButton buttonMine = new JButton(actionMine);
        JButton buttonBouclier = new JButton(actionBouclier);
        JButton buttonNext = new JButton(actionNext);

        buttonDeplacement.addActionListener(this);
        buttonTir.addActionListener(this);
        buttonBombe.addActionListener(this);
        buttonMine.addActionListener(this);
        buttonBouclier.addActionListener(this);
        buttonNext.addActionListener(this);

        this.add(buttonDeplacement);
        this.add(buttonTir);
        this.add(buttonBombe);
        this.add(buttonMine);
        this.add(buttonBouclier);
        this.add(buttonNext);
    }

    /**
     * Effectue des actions suivant l'ActionEvent
     * @param e L'ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case actionDeplacement:
                this.actionEnCours = e.getActionCommand();
                this.repaint();
                break;
            case actionTir:
                this.actionEnCours = e.getActionCommand();
                this.repaint();
                break;
            case actionBombe:
                this.actionEnCours = e.getActionCommand();
                this.repaint();
                break;
            case actionMine:
                this.actionEnCours = e.getActionCommand();
                this.repaint();
                break;
            case actionBouclier:
                this.controleur.bouclier(this.joueurCourant);
                break;
            case actionNext:
                this.controleur.next();
                break;
        }
    }

    /**
     * Effectue des actions au moment d'un clic de souris
     * @param e L'objet MouseEvent
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (this.actionEnCours){ // On effectue des actions suivant l'action en cours
            case actionDeplacement:
                Case caseFinal = quelCase(e.getX(),e.getY());
                if(caseFinal != null){
                    this.controleur.deplacement(this.joueurCourant,this.caseCourante,caseFinal);
                }
                break;
            case actionTir:
                Case caseCliquer = quelCase(e.getX(),e.getY());
                if(caseCliquer != null){
                    this.controleur.tir(this.joueurCourant,this.caseCourante,caseCliquer);
                }
                break;
            case actionBombe:
                Case caseBombe = quelCase(e.getX(),e.getY());
                if(caseBombe != null){
                    this.controleur.bombe(caseBombe,this.joueurCourant);
                }
                break;
            case actionMine:
                Case caseMine = quelCase(e.getX(),e.getY());
                if(caseMine != null){
                    this.controleur.mine(caseMine,this.joueurCourant);
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Trouve la case par rapport au x et y
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @return La case qui contient le point(x,y)
     */

    public Case quelCase(int x,int y){
        for (Case[] tabCase:this.plateau.plateauDeJeu) {
            for (Case c:tabCase) {
                if(x > c.positionGraphicX1 && x < c.positionGraphicX2)
                    if(y > c.positionGraphicY1 && y < c.positionGraphicY2)
                        return c;
            }
        }
        return null;
    }
}
