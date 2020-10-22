package model;

import bw.BWMDP;
import bw.BWMap;
import bw.BWSimulation;
import bw.maps.SeveralBridgesBWMap;
import bw.mdp.BWAction;
import bw.mdp.BWState;
import bw.visualization.BWPolicyViewer;
import java.util.Random;
import mdp.Policy;

public class Main {

    public static void main(String[] args) {

	// Problem definition
	System.out.println("Building problem...");
	BWMap map = new SeveralBridgesBWMap();
	int maxSpeed = 3;
	BWMDP mdp = BWMDP.makeStandardProblem(map, maxSpeed);
	System.out.println(" done.");

	// Policy definition
	System.out.println("Building policy...");
	Policy<BWState, BWAction> policy = new ValueIterationPolicy<>(mdp);
	System.out.println(" done.");

	// Agent definition, at a random initial state
	Random rand = new Random();
	int row = rand.nextInt(map.height);
	int col = rand.nextInt(map.width);
	BWState initialState = mdp.stateFactory.getState(row, col, 0);
	System.out.println("Agent created at initial state " + initialState);

	// Simulation
	System.out.println("Running simulation forever...");
	BWSimulation controller = new BWSimulation(mdp);
	controller.addAgent(state -> policy.get(state), initialState);
	int sleep = 200;
	controller.run(sleep);

	// Policy visualization
	for (int speed = 0; speed <= maxSpeed; speed++) {
	    System.out.println("Displaying policy at speed " + speed);
	    BWPolicyViewer viewer = new BWPolicyViewer(mdp, policy, speed);
	    viewer.showFrame("Bridge world policy at speed " + speed);
	    System.out.println(" done.");
	}
    }

}
