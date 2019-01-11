package sokoban.launcher;

import sokoban.model.Game;
import sokoban.model.LevelLoader;
import sokoban.model.Solver;

import java.util.ArrayList;

/**
 * Launches a new IA game.
 */
public class MainIA {
    public static void main(String[] args) {
        LevelLoader chargement = new LevelLoader();
        chargement.loadLevel("src/main/resources/levels/level6.sok");
        Game myplateau = new Game(chargement);
        myplateau.initBoard(chargement);

        LevelLoader charg = new LevelLoader();
        charg.loadLevel("src/main/resources/levels/level6.sok");
        Solver solver = new Solver(charg);
        solver.initBoard(charg);
        solver.deadlockBoard(solver.board);

        ArrayList<ArrayList<Integer>> iaMoves = new ArrayList<>();

        iaMoves = solver.solver(solver.getPlayerx(), solver.getPlayery(), solver.board, 0, iaMoves);

        ArrayList<Integer> position = new ArrayList<>();

        position.add(solver.getPlayerx());
        position.add(solver.getPlayery());

        System.out.println("Start");
        System.out.println("Number of IA moves: " + iaMoves.size());
        System.out.println("IA moves: " + iaMoves);
        for (int i = 0; i < iaMoves.size(); i++) {
            position = solver.move(iaMoves.get(i).get(0), iaMoves.get(i).get(1), solver.board, position);
            solver.printBoard(solver.board);
        }
    }
}
