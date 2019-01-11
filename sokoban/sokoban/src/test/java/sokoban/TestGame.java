package sokoban;

import java.io.IOException;

import sokoban.gui.GuiController;
import sokoban.model.Game;
import sokoban.util.LevelLoader;

public class TestGame {

    public static void main(String[] args) throws IOException {

        System.out.println("sokoban.controller to vide");
        LevelLoader chargeCtoV = new LevelLoader();
        chargeCtoV.loadLevel("src/test/resources/testMaps/basicMovement/upControllerToEmpty.sok");
        Game gameCtoV = new Game(chargeCtoV);
        gameCtoV.initBoard(chargeCtoV);
        GuiController controllerCtoV = new GuiController(gameCtoV);
        controllerCtoV.move(3);
        System.out.println(gameCtoV);

//        System.out.println("sokoban.controller to goal");
//        LevelLoader chargeCtoG = new LevelLoader();
//        chargeCtoG.loadLevel("resources/levels/testLevel/basicMovement/upControllerToGoal.sok");
//        Game gameCtoG = new Game(chargeCtoG);
//        GuiController controllerCtoG = new GuiController(gameCtoG);
//        controllerCtoG.move(3);
//        System.out.println(gameCtoG);

        System.out.println("sokoban.controller goal to vide");
        LevelLoader chargeCGtoV = new LevelLoader();
        chargeCGtoV.loadLevel("src/test/resources/testMaps/basicMovement/upControllerGoalToEmpty.sok");
        Game gameCGtoV = new Game(chargeCGtoV);
        gameCGtoV.initBoard(chargeCGtoV);
        GuiController controllerCGtoV = new GuiController(gameCGtoV);
        controllerCGtoV.move(3);
        System.out.println(gameCGtoV);

        System.out.println("sokoban.controller goal to goal");
        LevelLoader chargeCGtoG = new LevelLoader();
        chargeCGtoG.loadLevel("src/test/resources/testMaps/basicMovement/upControllerGoalToGoal.sok");
        Game gameCGtoG = new Game(chargeCGtoG);
        gameCGtoG.initBoard(chargeCGtoG);
        GuiController controllerCGtoG = new GuiController(gameCGtoG);
        controllerCGtoG.move(3);
        System.out.println(gameCGtoG);

        System.out.println("sokoban.controller pushes box to vide");
        LevelLoader chargeCPushV = new LevelLoader();
        chargeCPushV.loadLevel("src/test/resources/testMaps/boxMovement/upControllerPushBoxOnEmpty.sok");
        Game gameCPushV = new Game(chargeCPushV);
        gameCPushV.initBoard(chargeCPushV);
        GuiController controllerCPushV = new GuiController(gameCPushV);
        controllerCPushV.move(3);
        System.out.println(gameCPushV);

        System.out.println("sokoban.controller pushes box to goal");
        LevelLoader chargeCPushG = new LevelLoader();
        chargeCPushG.loadLevel("src/test/resources/testMaps/boxMovement/upControllerPushBoxOnGoal.sok");
        Game gameCPushG = new Game(chargeCPushG);
        gameCPushG.initBoard(chargeCPushG);
        GuiController controllerCPushG = new GuiController(gameCPushG);
        controllerCPushG.move(3);
        System.out.println(gameCPushG);

        System.out.println("sokoban.controller goal pushes box to vide");
        LevelLoader chargeCGPushV = new LevelLoader();
        chargeCGPushV.loadLevel("src/test/resources/testMaps/boxMovement/upControllerGoalPushBoxOnEmpty.sok");
        Game gameCGPushV = new Game(chargeCGPushV);
        gameCGPushV.initBoard(chargeCGPushV);
        GuiController controllerCGPushV = new GuiController(gameCGPushV);
        controllerCGPushV.move(3);
        System.out.println(gameCGPushV);

        System.out.println("sokoban.controller goal pushes box to goal");
        LevelLoader chargeCGPushG = new LevelLoader();
        chargeCGPushG.loadLevel("src/test/resources/testMaps/boxMovement/upControllerGoalPushBoxOnGoal.sok");
        Game gameCGPushG = new Game(chargeCGPushG);
        gameCGPushG.initBoard(chargeCGPushG);
        GuiController controllerCGPushG = new GuiController(gameCGPushG);
        controllerCGPushG.move(3);
        System.out.println(gameCGPushG);

        System.out.println("sokoban.controller pushes box to walls");
        LevelLoader chargeCPushW = new LevelLoader();
        chargeCPushW.loadLevel("src/test/resources/testMaps/boxMovement/upControllerPushBoxOnWall.sok");
        Game gameCPushW = new Game(chargeCPushW);
        gameCPushW.initBoard(chargeCPushW);
        GuiController controllerCPushW = new GuiController(gameCPushW);
        controllerCPushW.move(3);
        System.out.println(gameCPushW);

    }

}
