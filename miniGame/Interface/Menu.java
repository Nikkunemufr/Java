package Interface;

import Controleur.Controleur;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Classe d'affichage du menu
 *
 * La classe hérite de JPanel
 */

public class Menu extends JPanel {

    /**
     * Constructeur de la classe Menu
     * @param controleur Le controleur
     */

    public Menu(Controleur controleur){

        /* Panel pour le nombre de joueur */

        JPanel b1 = new JPanel();
        b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
        JLabel labelNbHumain = new JLabel("Nombre de joueurs : ");
        JTextField nbHumain = new JTextField("1",10);

        b1.add(labelNbHumain);
        b1.add(nbHumain);

        /* Panel pour le nombre de robot */

        JPanel b2 = new JPanel();
        b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
        JLabel labelNbRobot = new JLabel("Nombre de robots : ");
        JTextField nbRobot = new JTextField("1",10);

        b2.add(labelNbRobot);
        b2.add(nbRobot);

        /* Panel pour la difficulté des robots */

        JPanel b3 = new JPanel();
        b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));

        JPanel panelChoixIA = new JPanel(new GridLayout(0,1));

        Border borderIA = BorderFactory.createTitledBorder("Difficulté des robots");
        panelChoixIA.setBorder(borderIA);

        String[] choixIA = new String[]{"Facile","Normal","Difficile"};

        JComboBox listeIA = new JComboBox(choixIA);


        panelChoixIA.add(listeIA);

        b3.add(panelChoixIA);

        /* Panel pour le choix de la map */

        JPanel b4 = new JPanel();
        b4.setLayout(new BoxLayout(b4, BoxLayout.LINE_AXIS));

        JPanel panelChoixMap = new JPanel(new GridLayout(0,1));

        Border borderMap = BorderFactory.createTitledBorder("Choix de la map");
        panelChoixMap.setBorder(borderMap);

        String[] choixMap = new String[]{"Sans mur","Avec mur"};
        JComboBox listeMap = new JComboBox(choixMap);

        panelChoixMap.add(listeMap);

        b4.add(panelChoixMap);

        /* Panel pour le bouton jouer */

        JPanel b5 = new JPanel();
        b5.setLayout(new BoxLayout(b5, BoxLayout.LINE_AXIS));

        JButton buttonPlay = new JButton("Jouer");
        buttonPlay.addActionListener(controleur);

        b5.add(buttonPlay);

        /* Panel pour mettre tous les autres panel les un en dessous des autres */

        JPanel bAll = new JPanel();
        bAll.setLayout(new BoxLayout(bAll, BoxLayout.PAGE_AXIS));
        bAll.add(b1);
        bAll.add(b2);
        bAll.add(b3);
        bAll.add(b4);
        bAll.add(b5);

        this.add(bAll);

        controleur.setParam(nbHumain,nbRobot,listeIA,listeMap);
    }
}
