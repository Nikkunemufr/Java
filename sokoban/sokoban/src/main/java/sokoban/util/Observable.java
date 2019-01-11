package sokoban.util;

/**
 * Define an observable object.
 */
public interface Observable {

    /**
     * Adds an observer to the observable object.
     *
     * @param obs An observer
     */
    public void addObserver(Observer obs);

    /**
     * Removes an observer from the observable object.
     *
     * @param obs An observer
     */
    public void removeObserver(Observer obs);

    /**
     * Notify observer from changes.
     *
     * @param obj The changed object to be sent
     */
    public void notifyObserver(Object obj);

}
