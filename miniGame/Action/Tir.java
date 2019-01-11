package Action;

import Joueur.Joueur;
import Plateau.Case;

import static Plateau.Plateau.nbLigne;

import java.util.ArrayList;

import static Parametres.Parametres.*;

/**
 * Classe de gestion des tirs
 *
 * La classe hérite de la classe Armes
 *
 * Un tir est caractérisé par :
 * - Un cout en énergie
 * - Un montant de dégats
 * - Une portée de tir
 */

public class Tir extends Armes {

    /**
     * Constructeur de la classe Tir
     */

    public Tir() {
        super(coutTir,degatsTir);
    }

    /**
     * Gestion du tir, comprenant la vérification de la direction, la gestion des murs et l'application des dégats
     * @param joueurQuiTire Le joueur qui effectue le tir
     * @param caseJoueur    La case du joueur qui effectue le tir
     * @param caseCliquer   La case qui à été choisi par le joueur qui tir
     * @param plateauDeJeu  Le plateau de jeu
     * @return true si le tir à été possible
     */

    public boolean executeTir(Joueur joueurQuiTire,Case caseJoueur,Case caseCliquer,Case[][] plateauDeJeu){
        /* On vérifie d'abord si la case choisi par le joueur est dans la porté du tir */
        int directionX = caseJoueur.x - caseCliquer.x;
        int directionY = caseJoueur.y - caseCliquer.y;
        ArrayList<Joueur> joueurSurQuiTirer = new ArrayList<>();//On crée un tableau pour mettre les cibles

        if ((directionY != 0 && directionX == 0 && ((directionY <= porteDeTir && directionY > 0) || (directionY >= -porteDeTir && directionY < 0)))
        || (directionX != 0 &&directionY == 0 && ((directionX <= porteDeTir && directionX > 0) || (directionX >= -porteDeTir && directionX < 0)))) {
            if (directionY < 0) { // On regarde la direction
                for (int porte = 1; porte <= porteDeTir; porte++) {  // On part du joueur qui a tiré
                    int x = joueurQuiTire.position.x;
                    int y = joueurQuiTire.position.y + porte;
                    if (y >= 0 && y < nbLigne) {                     // On regarde si on est pas hors du plateau
                        if (plateauDeJeu[x][y].containMur != null) { // On regarde si il y a un mur
                            if (caseCliquer.y > y)                   // Si la case cliquer était sur le mur ou derrière le mur on renvoie false
                                return false;
                            break;                                   // Sinon on arrête simplement le tir
                        }
                        if (plateauDeJeu[x][y].containJoueur != null)// On regarde si il y a un joueur
                            joueurSurQuiTirer.add(plateauDeJeu[x][y].containJoueur);//On l'ajoute dans le tableau
                    }
                }
            } else if (directionY > 0) { // On regarde la direction
                for (int porte = 1; porte <= porteDeTir; porte++) {  // On part du joueur qui a tiré
                    int x = joueurQuiTire.position.x;
                    int y = joueurQuiTire.position.y - porte;
                    if (y >= 0 && y < nbLigne) {                     // On regarde si on est pas hors du plateau
                        if (plateauDeJeu[x][y].containMur != null){  // On regarde si il y a un mur
                            if (caseCliquer.y < y)                   // Si la case cliquer était sur le mur ou derrière le mur on renvoie false
                                return false;
                            break;                                   // Sinon on arrête simplement le tir
                        }

                        if (plateauDeJeu[x][y].containJoueur != null)// On regarde si il y a un joueur
                            joueurSurQuiTirer.add(plateauDeJeu[x][y].containJoueur);//On l'ajoute dans le tableau
                    }
                }
            } else if (directionX < 0) { // On regarde la direction
                for (int portee = 1; portee <= porteDeTir; portee++) {  // On part du joueur qui a tiré
                    int x = joueurQuiTire.position.x + portee;
                    int y = joueurQuiTire.position.y;
                    if (x >= 0 && x < nbLigne) {                        // On regarde si on est pas hors du plateau
                        if (plateauDeJeu[x][y].containMur != null) {    // On regarde si il y a un mur
                            if (caseCliquer.x > x)                      // Si la case cliquer était sur le mur ou derrière le mur on renvoie false
                                return false;
                            break;                                      // Sinon on arrête simplement le tir
                        }
                        if (plateauDeJeu[x][y].containJoueur != null)// On regarde si il y a un joueur
                            joueurSurQuiTirer.add(plateauDeJeu[x][y].containJoueur);//On l'ajoute dans le tableau
                    }
                }
            } else if (directionX > 0) { // On regarde la direction
                for (int porte = 1; porte <= porteDeTir; porte++) {  // On part du joueur qui a tiré
                    int x = joueurQuiTire.position.x - porte;
                    int y = joueurQuiTire.position.y;
                    if (x >= 0 && x < nbLigne) {                     // On regarde si on est pas hors du plateau
                        if (plateauDeJeu[x][y].containMur != null) { // On regarde si il y a un mur
                            if (caseCliquer.x < x)                   // Si la case cliquer était sur le mur ou derrière le mur on renvoie false
                                return false;
                            break;                                   // Sinon on arrête simplement le tir
                        }
                        if (plateauDeJeu[x][y].containJoueur != null)// On regarde si il y a un joueur
                            joueurSurQuiTirer.add(plateauDeJeu[x][y].containJoueur);//On l'ajoute dans le tableau
                    }
                }
            }
            for (Joueur j:joueurSurQuiTirer) { // On parcours le tableau pour infliger les dégats au cible
                infligeDegats(j);
            }
            joueurQuiTire.energie -= this.cout; // On retire le cout en énergie du tir au joueur
            return true; // On retourne true car tout s'est bien passé
        }
        return false;
    }
}
