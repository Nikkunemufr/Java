package schedule;

/**
 * Satisfaction des contraintes d'arité quelconque.
 * 
 * @author Alexis MORTELIER
 */
public interface Constraint {
	public boolean isSatisfied(Schedule emploiDuTemps);
}