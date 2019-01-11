package Test;

import Controleur.Controleur;
import Interface.Interface;
import Plateau.Plateau;

/**
 * Classe de test du jeu
 */

public class Test {

    public static void main(String[] args) {
        Plateau plateau = new Plateau();

        Controleur controleur = new Controleur(plateau);

        Interface vue = new Interface(controleur);

        plateau.ajoutEcouteur(vue);
    }

}
