package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import mdp.MDP;
import mdp.Policy;

public class RandomStatePolicy<S, A> implements Policy<S, A> {

    private Map<S, A> mapActions;

    public RandomStatePolicy(MDP<S, A> mdp) {
	this.mapActions = getMapActions(mdp);
    }

    private List<A> getListActions(Iterable<A> iterActions) {
	List<A> actions = new ArrayList<>();
	Iterator<A> iter = iterActions.iterator();
	while (iter.hasNext()) {
	    actions.add(iter.next());
	}
	return actions;
    }

    private Map<S, A> getMapActions(MDP<S, A> mdp) {
	Random r = new Random();
	Map<S, A> mapActions = new HashMap<>();
	List<A> actions = getListActions(mdp.actions());
	Iterable<S> states = mdp.states();
	Iterator<S> iterStates = states.iterator();
	while (iterStates.hasNext()) {
	    int randomIndex = r.nextInt(actions.size() - 1);
	    mapActions.put(iterStates.next(), actions.get(randomIndex));
	}
	return mapActions;
    }

    @Override
    public A get(S s) {
	return mapActions.get(s);
    }

}
