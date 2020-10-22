package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mdp.MDP;
import mdp.Policy;
import mdp.Reward;
import mdp.TransitionFunction;

public class ValueIterationPolicy<S, A> implements Policy<S, A> {

    private MDP mdp;
    private Map<S, A> policy;

    public ValueIterationPolicy(MDP<S, A> mdp) {
	this.mdp = mdp;
	this.policy = ValueIterationBellman(0.95, 0.1);
    }

    private double computeDelta(Map<S, Double> mapPrecedent, Map<S, Double> mapCurrent) {
	double delta = 0.0;
	for (S state : mapPrecedent.keySet()) {
	    delta = Math.max(delta, Math.abs(mapPrecedent.get(state) - mapCurrent.get(state)));
	}
	return delta;
    }

    private Map<S, A> ValueIterationBellman(double gamma, double epsilon) {

	Map<S, Double> allPreviousState = new HashMap<>();
	Map<S, Double> allCurrentState = new HashMap<>();
	Map<S, A> mapPolicy = new HashMap<>();
	Reward<S, A> R = mdp.getRewardFunction();
        TransitionFunction<S, A> p = mdp.getTransitionFunction();
	Iterable<S> states = mdp.states();
	Iterable<A> actions = mdp.actions();
	Iterator<S> iterStates = states.iterator();
        double delta = Double.POSITIVE_INFINITY;
	while (iterStates.hasNext()) {
	    S state = iterStates.next();
	    allPreviousState.put(state, 0.0);
	    allCurrentState.put(state, Double.NEGATIVE_INFINITY);
	}
	
	while (delta > epsilon) {
	    Iterator<S> iterCurrentState = states.iterator();
	    while (iterCurrentState.hasNext()) {
		S currentState = iterCurrentState.next();
		Iterator<A> iterAction = actions.iterator();
		while (iterAction.hasNext()) {
		    A a = iterAction.next();
		    Set<S> nextStates = mdp.getTransitionFunction().getNextStatesDistribution(currentState, a).support();
                    double value = 0;
		    for (S sPrime : nextStates) {
                        value += p.getTransitionProbability(currentState, a, sPrime) * (R.getReward(currentState,a , sPrime) + gamma * allPreviousState.get(sPrime));
                    }
                    if (value > allCurrentState.get(currentState)) {
                        allCurrentState.put(currentState, value);
                        mapPolicy.put(currentState, a);
                    }
		}
	    }
	    delta = computeDelta(allPreviousState, allCurrentState);
            allPreviousState = new HashMap<>(allCurrentState);
            while (iterStates.hasNext()) {
                S state = iterStates.next();
                allCurrentState.put(state, Double.NEGATIVE_INFINITY);
            }
	}
	return mapPolicy;
    }

    @Override
    public A get(S s) {
	return policy.get(s);
    }

}
