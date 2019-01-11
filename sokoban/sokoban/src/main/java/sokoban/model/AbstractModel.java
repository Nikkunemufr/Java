package sokoban.model;

import java.util.ArrayList;

import sokoban.util.*;

public abstract class AbstractModel implements Observable {

    private ArrayList<Observer> listObserver = new ArrayList<>();

    @Override
    public void notifyObserver(Object obj) {
        for (Observer obs : listObserver) {
            obs.update(obj);
        }
    }

    @Override
    public void removeObserver(Observer obs) {
        listObserver.remove(obs);
    }

    @Override
    public void addObserver(Observer obs) {
        listObserver.add(obs);
    }
}
