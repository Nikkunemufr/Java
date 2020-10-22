package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import mdp.MDP;
import mdp.Policy;

public class RandomPolicy<S, A> implements Policy<S, A> {

    private List<A> listActions;

    public RandomPolicy(MDP<S, A> mdp) {
	this.listActions = getListActions(mdp.actions());
    }

    private List<A> getListActions(Iterable<A> iterActions) {
	List<A> actions = new ArrayList<>();
	Iterator<A> iter = iterActions.iterator();
	while (iter.hasNext()) {
	    actions.add(iter.next());
	}
	return actions;
    }

    @Override
    public A get(S s) {
	Random r = new Random();
	int randomIndex = r.nextInt(listActions.size() - 1);
	return listActions.get(randomIndex);
    }

}
