package sokoban.model;

import java.util.ArrayList;

public class Solver extends Game {

    public ArrayList<ArrayList<Integer>> dir = new ArrayList<>();
    public int nbr = 27;
    public ArrayList<ArrayList<Integer>> solution = new ArrayList<>();


    public Solver(LevelLoader level) {
        super(level);
        dir = initDirection();
    }

    /**
     * Initialise les directions pour que l'IA puisse se déplace sur la Square.
     */
    public ArrayList<ArrayList<Integer>> initDirection() {
        ArrayList<ArrayList<Integer>> coup = new ArrayList<>();
        ArrayList<Integer> coup_haut = new ArrayList<>();
        coup_haut.add(-1);
        coup_haut.add(0);
        coup.add(coup_haut);
        ArrayList<Integer> coup_droit = new ArrayList<>();
        coup_droit.add(0);
        coup_droit.add(1);
        coup.add(coup_droit);
        ArrayList<Integer> coup_bas = new ArrayList<>();
        coup_bas.add(1);
        coup_bas.add(0);
        coup.add(coup_bas);
        ArrayList<Integer> coup_gauche = new ArrayList<>();
        coup_gauche.add(0);
        coup_gauche.add(-1);
        coup.add(coup_gauche);

        return coup;
    }

    /**
     * Permet de faire une copie de la board afin de pouvoir retourner en
     * arrière.
     */
    public Square[][] copy(Square[][] newBoard, Square[][] oldBoard) {

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                newBoard[i][j] = oldBoard[i][j].clone();
            }
        }

        return newBoard;
    }

    /**
     * Genere une liste de position où se trouve les faces des boxs.
     */
    public ArrayList<ArrayList<Integer>> faceBox(int i, int j, Square[][] myboard) {
        ArrayList<ArrayList<Integer>> liste = new ArrayList<>();
        ArrayList<Integer> direction_init = new ArrayList<>();

        direction_init.add(0);
        direction_init.add(0);

        initCheck(myboard);
        liste = destinationBox(i, j, liste, direction_init, myboard);

        return liste;
    }

    /**
     * Calcul les chemins depuis une position donnée aux boxs.
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> cheminBox(int i, int j, Square[][] myboard) {
        ArrayList<ArrayList<ArrayList<Integer>>> chemin = new ArrayList<>();
        ArrayList<ArrayList<Integer>> liste = new ArrayList<>();

        resetamelioration(myboard);
        amelioration(i, j, 0, myboard);

        liste = faceBox(i, j, myboard);
        for (int coup = 0; coup < liste.size(); coup++) {
            if (isvalidDestination(liste.get(coup).get(0), liste.get(coup).get(1), liste.get(coup), myboard) == true) {
                //System.out.println("coup validé" + liste.get(coup));
                chemin.add(detectChemin(liste.get(coup), myboard));
            }
        }

        return chemin;
    }

    /**
     * Cherche si le chemin est classé par taille croissante et retourne
     * l'indice s'il trouve une erreur.
     */
    public int isCheminCroissant(ArrayList<ArrayList<ArrayList<Integer>>> chemin) {
        if (chemin.size() != 0) {

            for (int i = 1; i < chemin.size(); i++) {
                if (chemin.get(i - 1).size() > chemin.get(i).size()) {
                    return i - 1;
                }
            }
        }

        //System.out.println(chemin.get(0).size());
        return -1;
    }

    /**
     * Permet de trier une liste de chemin du moins éloigné au plus éloigné.
     */
    public ArrayList<ArrayList<ArrayList<Integer>>> triChemin(ArrayList<ArrayList<ArrayList<Integer>>> chemin) {
        ArrayList<ArrayList<Integer>> tampon = new ArrayList<>();
        int index = isCheminCroissant(chemin);

        while (index != -1) {
            tampon = chemin.get(index);
            chemin.remove(index);
            chemin.add(tampon);
            index = isCheminCroissant(chemin);
        }

        return chemin;
    }

    /**
     * Permet de sauvergarder un chemin validé par l'IA.
     */
    public void cloneTrajet(ArrayList<ArrayList<Integer>> newtrajet) {
        solution.clear();
        for (int i = 0; i < newtrajet.size(); i++) {
            ArrayList<Integer> direction = new ArrayList<>();
            for (int j = 0; j < newtrajet.get(i).size(); j++) {
                direction.add(newtrajet.get(i).get(j));
            }
            solution.add(direction);
        }
    }

    /**
     * Permet de stocker un trajet dans une liste.
     */
    public ArrayList<ArrayList<Integer>> recupTrajet(ArrayList<ArrayList<Integer>> newtrajet, ArrayList<ArrayList<Integer>> oldtrajet) {
        for (int i = 0; i < oldtrajet.size(); i++) {
            ArrayList<Integer> direction = new ArrayList<>();
            for (int j = 0; j < oldtrajet.get(i).size(); j++) {
                direction.add(oldtrajet.get(i).get(j));
            }
            newtrajet.add(direction);
        }
        return newtrajet;
    }

    /**
     * Algorithme permetant de faire fonctionner les 2 modes de l'IA.
     */
    public ArrayList<ArrayList<Integer>> solver(int i, int j, Square[][] myboard, int total, ArrayList<ArrayList<Integer>> trajet) {
        System.out.println("solver");

        ArrayList<ArrayList<Integer>> sauv = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Integer>>> listeChemin = new ArrayList<>();

        listeChemin = triChemin(cheminBox(i, j, myboard));
        System.out.println(listeChemin.size());
        //System.out.println("ma liste de chemin" + listeChemin);
        if (hasEnded(myboard) == false && jeublock(myboard) == false && listeChemin.size() > 0 && total < nbr) {
            printBoard(myboard);

            for (int index = 0; index < listeChemin.size(); index++) {
                sauv = recupTrajet(sauv, trajet);
                goToBox(listeChemin.get(index), i, j, myboard, total, trajet);
                trajet.clear();
                trajet = recupTrajet(trajet, sauv);
            }
        } else {
            if (hasEnded(myboard) == true && nbr >= total) {
                nbr = total;
                System.out.println("la taille : " + trajet.size() + " total : " + total);
                cloneTrajet(trajet);
            }
        }
        return solution;
    }

    /**
     * Premier mode de l'IA : Aller vers les boites, dans ce mods l'IA va aller
     * vers les boites à l'aide d'un parcour en largeur.
     */
    public void goToBox(ArrayList<ArrayList<Integer>> chemin, int i, int j, Square[][] myboard, int total, ArrayList<ArrayList<Integer>> trajet) {
        System.out.println("gotobox");
        Square[][] newboard = new Square[level.getHeight()][level.getWidth()];
        newboard = copy(newboard, myboard);
        ArrayList<Integer> position = new ArrayList<>();

        position.add(i);
        position.add(j);

        if (chemin.get(0).get(0) != 0 || chemin.get(0).get(1) != 0) {
            for (int index = 0; index < chemin.size(); index++) {
                position = move(chemin.get(index).get(0), chemin.get(index).get(1), newboard, position);
                trajet.add(chemin.get(index));
                total++;
            }
        }

        printBoard(newboard);
        heuristic(position.get(0), position.get(1), newboard, total, trajet);
    }

    /**
     * Deuxieme mode de l'IA : Executer le mouvement calculé puis continuer à
     * chercher une solution en appelant l'Algorithm
     */
    public void heuristic(int i, int j, Square[][] myboard, int total, ArrayList<ArrayList<Integer>> trajet) {
        System.out.println("heuristic");
        ArrayList<Integer> position = new ArrayList<>();

        position.add(i);
        position.add(j);

        for (int index = 0; index < dir.size(); index++) {
            if (myboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.BOX) {
                position = move(dir.get(index).get(0), dir.get(index).get(1), myboard, position);
                trajet.add(dir.get(index));
                total++;
            }
        }
        printBoard(myboard);
        solver(position.get(0), position.get(1), myboard, total, trajet);
    }

    /**
     * Detects simple deadlock .
     *
     * @param
     * @return A string of the formatted board
     */
    public void deadlockBoard(Square[][] newboard) {
        //Square[][] newBoard = new Square[level.getHeight()][level.getWidth()];
        //newBoard = copy(newBoard);
        //move(newBoard, 0,1);
        //System.out.println(formatBoard(newBoard));
        ArrayList<ArrayList<Integer>> liste = new ArrayList<>();
        ArrayList<Integer> old = new ArrayList<>();
        old.add(0);
        old.add(0);
        liste = binaryDestination(getPlayerx(), getPlayery(), liste, old, newboard);

        //se deplace dans la liste des target accessible
        for (int index = 0; index < liste.size(); index++) {
            //System.out.println(" le mouvement : " + detectDirection(liste.get(index).get(0), liste.get(index).get(1)));
            //pour les mouvement haut, gauche, bas et droite
            for (int sens = 0; sens < dir.size(); sens++) {

                //détermine la direction ou il faut aller
                if (detectDirection(liste.get(index).get(0), liste.get(index).get(1), newboard) == dir.get(sens)) {
                    int i = liste.get(index).get(0);
                    int j = liste.get(index).get(1);


                    //Si la case futur n'est pas un mur
                    if (newboard[i + dir.get(sens).get(0)][j + dir.get(sens).get(1)].getType() != Square.WALL) {
                        newboard[i][j].changeState(); //case non bloqué

                        //nouvelle direction pour la prochaine case
                        for (int direction = 0; direction < dir.size(); direction++) {

                            //si la direction n'est pas egale a la direction de la case target
                            if (dir.get(direction) != dir.get((sens + 2) % 4)) {
                                //System.out.println(liste.size() +" taille " + dir.get(direction) + "la liste : " + liste.get(index));
                                newMobile(liste.get(index).get(0) + dir.get(direction).get(0), liste.get(index).get(1) + dir.get(direction).get(1), dir.get(direction), newboard);
                            }
                        }
                    }
                }
            }
        }

        stateImpass(newboard);
    }

    /**
     * Permet à chaque case trouver en non deadlock de continuer la recherche
     * dans le secteur.
     */
    public void newMobile(int i, int j, ArrayList<Integer> oldDir, Square[][] newboard) {
        if (newboard[i][j].getState() != 0 && newboard[i][j].getType() != Square.WALL) {
            if (newboard[i][j].getType() != Square.TARGET && newboard[i][j].getType() != Square.BOX_ON_TARGET &&
                    newboard[i][j].getType() != Square.PLAYER_ON_TARGET) {
                if (newboard[i + oldDir.get(0)][j + oldDir.get(1)].getType() != Square.WALL) {
                    newboard[i][j].changeState();
                    newMobile(i + oldDir.get(0), j + oldDir.get(1), oldDir, newboard);

                    for (int index = 0; index < dir.size(); index++) {
                        if (oldDir != dir.get(index)) {

                            if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL) {
                                if (newboard[i][j].getType() != Square.TARGET && newboard[i][j].getType() != Square.BOX_ON_TARGET &&
                                        newboard[i][j].getType() != Square.PLAYER_ON_TARGET) {
                                    newMobile(i + dir.get(index).get(0), j + dir.get(index).get(1), dir.get(index), newboard);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Permet de déterminer si la position est dans un tunnel de "MUR".
     */
    public boolean isTunnel(int i, int j, Square[][] newboard) {
        int compteur = 0;

        for (int index = 0; index < dir.size(); index++) {
            if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.WALL) {
                compteur++;
            }
        }
        if (compteur == 2) {
            return true;
        }
        return false;
    }

    /**
     * Permet de déterminer si la postion est une impasse de "MUR".
     */
    public boolean isImpass(int i, int j, Square[][] newboard) {
        int compteur = 0;

        for (int index = 0; index < dir.size(); index++) {
            if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.WALL) {
                compteur++;
            }
        }
        if (compteur == 3) {
            return true;
        }
        return false;
    }

    /**
     * Permet de déterminer depuis une position si le tunnel mene vers une
     * impasse ou non.
     */
    public boolean detectImpass(int i, int j, int sens, Square[][] newboard) {
        if (newboard[i][j].getType() != Square.WALL) {
            while (isTunnel(i, j, newboard) == true) {
                for (int index = 0; index < dir.size(); index++) {
                    if (isImpass(i, j, newboard)) {
                        return true;
                    } else if (isTunnel(i, j, newboard) == true) {
                        if (dir.get(index) != dir.get(sens) && newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL) {
                            sens = (index + 2) % 4;
                            i = i + dir.get(index).get(0);
                            j = j + dir.get(index).get(1);
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Meme utilité que isImpass sauf qu'elle calcule avec mur et box.
     */
    public boolean ImpassBox(int i, int j, int sens, Square[][] newboard) {
        int compteur = 0;
        for (int index = 0; index < dir.size(); index++) {
            if (index != sens &&
                    (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.WALL ||
                            newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.BOX ||
                            newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.BOX_ON_TARGET)) {
                compteur++;
            }
        }

        if (compteur == 3) {
            return true;
        }

        return false;
    }

    /**
     * Meme utilité que detectImpass sauf qu'elle calcule avec mur et box
     */
    public boolean boxTunnel(int i, int j, int sens, Square[][] newboard) {
        while (isTunnel(i, j, newboard) == true) {
            for (int index = 0; index < dir.size(); index++) {
                if (ImpassBox(i, j, sens, newboard)) {
                    return true;
                } else if (isTunnel(i, j, newboard) == true) {
                    if (dir.get(index) != dir.get(sens) && newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL &&
                            (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX ||
                                    newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX_ON_TARGET)) {
                        sens = (index + 2) % 4;
                        i = i + dir.get(index).get(0);
                        j = j + dir.get(index).get(1);

                    }
                }
            }
        }
        return false;
    }

    /**
     * Detecte toute les impasses sur le plateau
     */
    public void stateImpass(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                for (int compteur = 0; compteur < 4; compteur++) {
                    if (newboard[i][j].getState() == 0 && detectImpass(i, j, compteur, newboard) == true) {
                        newboard[i][j].changeState(1);
                    }
                }
            }
        }
    }

    /**
     * Detecte toute les impasses de mur et box sur le plateau
     */
    public void stateImpassBox(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                for (int compteur = 0; compteur < 4; compteur++) {
                    if (newboard[i][j].getState() == 0 && boxTunnel(i, j, compteur, newboard) == true) {
                        newboard[i][j].changeState(1);
                    }
                }
            }
        }
    }

    /**
     * Renitialise la face recherche du plateau
     */
    public void initCheck(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                newboard[i][j].changeCheck(false);
            }
        }
    }

    /**
     * Attribut à chaque case du plateau une chiffre déterminant sa distance
     * avec la postion donnée
     */
    public void amelioration(int i, int j, int compteur, Square[][] newboard) {
        if (compteur == 0) {
            newboard[i][j].changeChemin(compteur);
            compteur++;
        }

        for (int index = 0; index < dir.size(); index++) {
            if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL &&
                    newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX &&
                    newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX_ON_TARGET &&
                    (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getChemin() > compteur ||
                            newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getChemin() == -1)) {
                newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].changeChemin(compteur);
                amelioration(i + dir.get(index).get(0), j + dir.get(index).get(1), compteur + 1, newboard);
            }
        }
    }

    /**
     * Renintialise la face distance position du plateau
     */
    public void resetamelioration(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                if (newboard[i][j].getChemin() != -1) {
                    newboard[i][j].changeChemin(-1);
                }
            }
        }
    }

    /**
     * Permet de valider un chemin qui mene pas vers une situation de blocage
     */
    public boolean isvalidDestination(int i, int j, ArrayList<Integer> coup, Square[][] myboard) {
        for (int index = 0; index < dir.size(); index++) {
            if (index == directionForBox(i, j, myboard)) {
                int x = i + (dir.get(index).get(0) + dir.get(index).get(0));
                int y = j + (dir.get(index).get(1) + dir.get(index).get(1));

                //System.out.println(i + " : " + j + x + " : " + y + " direction " + index);
                if (x < level.getHeight() && x >= 0 &&
                        y < level.getWidth() && y >= 0) {

                    //System.out.println(myboard[x][y].getElement() + " direction " + index);
                    if (myboard[x][y].getType() == Square.WALL ||
                            (myboard[x][y].getState() == 1 &&
                                    myboard[x][y].getType() != Square.TARGET)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Fait une recherche sur le plateau afin de trouver une box
     */
    public ArrayList<ArrayList<Integer>> destinationBox(int i, int j, ArrayList<ArrayList<Integer>> maliste, ArrayList<Integer> oldDir, Square[][] newboard) {
        if (newboard[i][j].getCheck() != true) {
            newboard[i][j].changeCheck(true);

            for (int index = 0; index < dir.size(); index++) {
                if (oldDir.get(0) != dir.get(index).get(0) || oldDir.get(1) != dir.get(index).get(1)) {
                    if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX) { //&& newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX_ON_TARGET){
                        if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL) {
                            maliste = destinationBox(i + dir.get(index).get(0), j + dir.get(index).get(1), maliste, dir.get((index + 2) % 4), newboard);
                        }
                    } else {
                        ArrayList<Integer> caseTarget = new ArrayList<>();
                        caseTarget.add(i);
                        caseTarget.add(j);
                        maliste.add(caseTarget);
                    }
                }
            }
        }
        return maliste;
    }

    /**
     * A partir de la face distance position, il cacule le chemin jusqu'a la
     * case distance 0
     */
    public ArrayList<ArrayList<Integer>> detectChemin(ArrayList<Integer> position, Square[][] newboard) {
        ArrayList<ArrayList<Integer>> chemin = new ArrayList<>();
        int i = position.get(0);
        int j = position.get(1);
        int compteur = newboard[i][j].getChemin();
        boolean deplacement;

        if (compteur == 0) {
            ArrayList<Integer> vide = new ArrayList<>();
            vide.add(0);
            vide.add(0);
            chemin.add(0, vide);
        }

        while (compteur > 0) {
            deplacement = true;
            for (int index = 0; index < dir.size(); index++) {
                if (deplacement == true && compteur - 1 == newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getChemin()) {
                    i = i + dir.get(index).get(0);
                    j = j + dir.get(index).get(1);
                    compteur--;
                    chemin.add(0, dir.get((index + 2) % 4));
                    deplacement = false;
                }
            }
        }

        return chemin;
    }

    /**
     * Fait une recherche afin de trouver les targets sur le plateau
     */
    public ArrayList<ArrayList<Integer>> binaryDestination(int i, int j, ArrayList<ArrayList<Integer>> maliste, ArrayList<Integer> oldDir, Square[][] newboard) {
        if (newboard[i][j].getCheck() != true) {
            newboard[i][j].changeCheck(true);

            for (int index = 0; index < dir.size(); index++) {
                if (oldDir.get(0) != dir.get(index).get(0) || oldDir.get(1) != dir.get(index).get(1)) {
                    if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.TARGET && newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX_ON_TARGET && newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.PLAYER_ON_TARGET) {
                        if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.WALL) {
                            maliste = binaryDestination(i + dir.get(index).get(0), j + dir.get(index).get(1), maliste, dir.get((index + 2) % 4), newboard);
                        }
                    } else {
                        ArrayList<Integer> caseTarget = new ArrayList<>();
                        caseTarget.add(i);
                        caseTarget.add(j);
                        maliste.add(caseTarget);
                    }
                }
            }
        }
        return maliste;
    }

    /**
     * Permet de trouver la direction de départ pour le calcule de deadlock
     */
    public ArrayList<Integer> detectDirection(int i, int j, Square[][] newboard) {
        int index = 0;

        while (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.TARGET &&
                newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() != Square.BOX_ON_TARGET) {
            index++;
        }

        return dir.get((index + 2) % 4);
        //return dir.get(index);
    }

    /**
     * meme utilité mais pour les boxs
     */
    public int directionForBox(int i, int j, Square[][] newboard) {
        int index = 0;

        for (index = 0; index < dir.size(); index++) {
            if (newboard[i + dir.get(index).get(0)][j + dir.get(index).get(1)].getType() == Square.BOX) {
                return index;
            }
        }

        return 5;
        //return dir.get(index);
    }

    /**
     * Détermine si le plateau est bloqué
     */
    public boolean jeublock(Square[][] newboard) {
        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getWidth(); j++) {
                if (newboard[i][j].getType() == Square.BOX && isblock(i, j, newboard) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Détermine si la case est bloqué
     */
    public boolean isblock(int i, int j, Square[][] newboard) {
        if (newboard[i][j].getType() == Square.BOX_ON_TARGET) {
            return false;
        }

        if (newboard[i][j].getState() == 0) {
            if (vertical(i, j, newboard) == false || horizontal(i, j, newboard) == false) {

                if ((detectImpass(i + dir.get(0).get(0), j + dir.get(0).get(1), 2, newboard) == false ||
                        newboard[i + dir.get(0).get(0)][j + dir.get(0).get(1)].getType() == Square.TARGET)
                        &&
                        (detectImpass(i + dir.get(2).get(0), j + dir.get(2).get(1), 0, newboard) == false ||
                                newboard[i + dir.get(2).get(0)][j + dir.get(2).get(1)].getType() == Square.TARGET)) {

                    if ((detectImpass(i + dir.get(1).get(0), j + dir.get(1).get(1), 3, newboard) == false ||
                            newboard[i + dir.get(1).get(0)][j + dir.get(1).get(1)].getType() == Square.TARGET)
                            &&
                            (detectImpass(i + dir.get(3).get(0), j + dir.get(3).get(1), 1, newboard) == false ||
                                    newboard[i + dir.get(3).get(0)][j + dir.get(3).get(1)].getType() == Square.TARGET)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Détermine si l'axe vertical est bloqué.
     */
    public boolean vertical(int i, int j, Square[][] newboard) {
        boolean haut = false;
        boolean bas = false;

        if (newboard[i + dir.get(0).get(0)][j + dir.get(0).get(1)].getType() != Square.WALL &&
                newboard[i + dir.get(2).get(0)][j + dir.get(2).get(1)].getType() != Square.WALL) {

            if (newboard[i + dir.get(0).get(0)][j + dir.get(0).get(1)].getState() == 0 ||
                    newboard[i + dir.get(2).get(0)][j + dir.get(2).get(1)].getState() == 0) {

                if (newboard[i + dir.get(0).get(0)][j + dir.get(0).get(1)].getType() == Square.BOX ||
                        newboard[i + dir.get(0).get(0)][j + dir.get(0).get(1)].getType() == Square.BOX_ON_TARGET) {
                    haut = horizontal(i + dir.get(0).get(0), j + dir.get(0).get(1), newboard);
                }

                if (newboard[i + dir.get(2).get(0)][j + dir.get(2).get(1)].getType() == Square.BOX ||
                        newboard[i + dir.get(2).get(0)][j + dir.get(2).get(1)].getType() == Square.BOX_ON_TARGET) {
                    bas = horizontal(i + dir.get(2).get(0), j + dir.get(2).get(1), newboard);
                }

                if (haut == false && bas == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Détermine si l'axe horizontale est bloqué.
     */
    public boolean horizontal(int i, int j, Square[][] newboard) {
        boolean droite = false;
        boolean gauche = false;

        if (newboard[i + dir.get(1).get(0)][j + dir.get(1).get(1)].getType() != Square.WALL &&
                newboard[i + dir.get(3).get(0)][j + dir.get(3).get(1)].getType() != Square.WALL) {

            if (newboard[i + dir.get(1).get(0)][j + dir.get(1).get(1)].getState() == 0 ||
                    newboard[i + dir.get(3).get(0)][j + dir.get(3).get(1)].getState() == 0) {

                if (newboard[i + dir.get(1).get(0)][j + dir.get(1).get(1)].getType() == Square.BOX ||
                        newboard[i + dir.get(1).get(0)][j + dir.get(1).get(1)].getType() == Square.BOX_ON_TARGET) {
                    droite = horizontal(i + dir.get(1).get(0), j + dir.get(1).get(1), newboard);
                }

                if (newboard[i + dir.get(3).get(0)][j + dir.get(3).get(1)].getType() == Square.BOX ||
                        newboard[i + dir.get(3).get(0)][j + dir.get(3).get(1)].getType() == Square.BOX_ON_TARGET) {
                    gauche = vertical(i + dir.get(3).get(0), j + dir.get(3).get(1), newboard);
                }

                if (droite == false && gauche == false) {
                    return false;
                }
            }
        }
        return true;
    }
}
