package Parametres;

import java.util.Random;

/**
 * Classe de gestion des fonctions générales
 */

public class FonctionCommune {

    /**
     * Génère un nombre aléatoire
     * @param min Nombre minimum inclus
     * @param max Nombre maximum inclus
     * @return Un nombre entre min et max inclus
     */

    public static int generateRandom(int min, int max){
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

}
