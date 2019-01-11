package Joueur;

import Plateau.*;

import static Parametres.FonctionCommune.generateRandom;
import static Plateau.Plateau.nbLigne;

/**
 * La classe de gestion de l'IA Facile
 *
 * La classe implémente StrategyJoueur pour ainsi utiliser le pattern Strategy
 */

public class IAFacile implements StrategyJoueur{

    /**
     * Décide de la prochaine action du robot
     * @param plateau Les données du modèle
     * @param joueur Le joueur robot
     */

    @Override
    public void executeIA(Plateau plateau, Joueur joueur) {
        int choix = generateRandom(0,5); // On décide au hasard de l'action du joueur robot
        switch (choix){
            case 0: // Deplacement
                int choixDeplacement = generateRandom(1,4);
                int x = 0;
                int y = 0;
                switch (choixDeplacement){
                    case 1:
                        x++;
                        break;
                    case 2:
                        x--;
                        break;
                    case 3:
                        y++;
                        break;
                    case 4:
                        y--;
                        break;
                }
                if(joueur.position.x+x >= 0 && joueur.position.x+x < nbLigne && joueur.position.y+y >= 0 && joueur.position.y+y < nbLigne){
                    Case caseFinal = plateau.plateauDeJeu[joueur.position.x+x][joueur.position.y+y];
                    if(!plateau.deplacement(joueur,joueur.position,caseFinal))
                        executeIA(plateau,joueur);
                } else {
                    executeIA(plateau,joueur);
                }
                break;
            case 1: // Tir
                int choixTir = generateRandom(1,4);
                int xTir = 0;
                int yTir = 0;
                switch (choixTir){
                    case 1:
                        xTir++;
                        break;
                    case 2:
                        xTir--;
                        break;
                    case 3:
                        yTir++;
                        break;
                    case 4:
                        yTir--;
                        break;
                }
                if(joueur.position.x+xTir >= 0 && joueur.position.x+xTir < nbLigne && joueur.position.y+yTir >= 0 && joueur.position.y+yTir < nbLigne){
                    Case caseCliquer = plateau.plateauDeJeu[joueur.position.x+xTir][joueur.position.y+yTir];
                    if(!plateau.tir(joueur,joueur.position,caseCliquer))
                        executeIA(plateau,joueur);
                } else {
                    executeIA(plateau,joueur);
                }
                break;
            case 2: // Bombe
                int choixBombe = generateRandom(1,4);
                int xBombe = 0;
                int yBombe = 0;
                switch (choixBombe){
                    case 1:
                        xBombe++;
                        break;
                    case 2:
                        xBombe--;
                        break;
                    case 3:
                        yBombe++;
                        break;
                    case 4:
                        yBombe--;
                        break;
                    case 5:
                        xBombe++;
                        yBombe++;
                        break;
                    case 6:
                        xBombe++;
                        yBombe--;
                        break;
                    case 7:
                        xBombe--;
                        yBombe--;
                        break;
                    case 8:
                        xBombe--;
                        yBombe++;
                        break;
                }
                if(joueur.position.x+xBombe >= 0 && joueur.position.x+xBombe < nbLigne && joueur.position.y+yBombe >= 0 && joueur.position.y+yBombe < nbLigne){
                    Case casePose = plateau.plateauDeJeu[joueur.position.x+xBombe][joueur.position.y+yBombe];
                    if(!plateau.bombe(casePose,joueur))
                        executeIA(plateau,joueur);
                } else {
                    executeIA(plateau,joueur);
                }
                break;
            case 3: // Mine
                int choixMine = generateRandom(1,4);
                int xMine = 0;
                int yMine = 0;
                switch (choixMine){
                    case 1:
                        xMine++;
                        break;
                    case 2:
                        xMine--;
                        break;
                    case 3:
                        yMine++;
                        break;
                    case 4:
                        yMine--;
                        break;
                    case 5:
                        xMine++;
                        yMine++;
                        break;
                    case 6:
                        xMine++;
                        yMine--;
                        break;
                    case 7:
                        xMine--;
                        yMine--;
                        break;
                    case 8:
                        xMine--;
                        yMine++;
                        break;
                }
                if(joueur.position.x+xMine >= 0 && joueur.position.x+xMine < nbLigne && joueur.position.y+yMine >= 0 && joueur.position.y+yMine < nbLigne){
                    Case casePose = plateau.plateauDeJeu[joueur.position.x+xMine][joueur.position.y+yMine];
                    if(!plateau.mine(casePose,joueur))
                        executeIA(plateau,joueur);
                } else {
                    executeIA(plateau,joueur);
                }
                break;
            case 4: // Bouclier
                if(!plateau.bouclier(joueur))
                    executeIA(plateau,joueur);
                break;
            case 5: // Next
                if(!plateau.next())
                    executeIA(plateau,joueur);
                break;
        }
    }
}
