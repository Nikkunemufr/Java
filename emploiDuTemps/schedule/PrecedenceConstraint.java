package schedule;

/**
 * Représente une contrainte de précédence.
 *
 * @author Alexis MORTELIER
 */
public class PrecedenceConstraint extends BinaryConstraint {

    public PrecedenceConstraint(Activity first, Activity second) {
        super(first, second);
    }

    /**
     * Retourne la représentation des deux activités.
     *
     * @return Une chaîne de caractères représantant les deux activités sous la
     * forme : (première activité, seconde activité).
     */
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    /**
     * Vérifie que la contrainte est satisfaite (la première activité et la
     * seconde activité n'ont pas lieu en même temps).
     *
     * @param date1 Date de début de la première activité en heure(s)
     * @param date2 Date de début de la seconde activité en heure(s)
     * @return true si la contrainte est satisfaite (les activités ne se
     * chevauchent pas), false sinon.
     */
    @Override
    public boolean isSatisfied(int date1, int date2) {
        return date1 * 60 + first.getDuration() <= date2 * 60;
    }
}