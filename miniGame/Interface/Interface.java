package Interface;

import Controleur.Controleur;
import Plateau.Plateau;

import javax.swing.*;

import static Parametres.Parametres.sizeEcran;

/**
 * Classe de gestion de la vue
 *
 * La classe hérite de JFrame et implémente EcouteurModele
 *
 * La vue est caractérisée par :
 * - Un accès au controleur
 */

public class Interface extends JFrame implements EcouteurModele{

    private Controleur controleur;

    /**
     * Constructeur de la classe Interface
     * @param controleur Le controleur
     */

    public Interface(Controleur controleur){
        this.controleur = controleur;
        this.setSize(sizeEcran, sizeEcran);
        this.setTitle("Jeu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        initMenu();

        this.setVisible(true);
    }

    /**
     * Création et affichage du menu
     */

    private void initMenu(){
        this.setContentPane(new Menu(this.controleur));
    }

    /**
     * Création et affichage du jeu
     * @param plateau
     */

    private void paintPlateau(Plateau plateau){
        this.setContentPane(new AffichagePlateau(plateau,this.controleur));
        this.validate();
        this.repaint();
    }

    /**
     * Met a jour la vue
     * @param plateau Les données du modèle
     */

    @Override
    public void modeleMisAJour(Plateau plateau) {
        paintPlateau(plateau);
    }
}