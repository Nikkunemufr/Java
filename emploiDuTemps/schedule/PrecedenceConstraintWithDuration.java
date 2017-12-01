package schedule;

/**
 * Représente une contrainte de précédence avec durées.
 *
 * @author Alexis MORTELIER
 */
public class PrecedenceConstraintWithDuration extends PrecedenceConstraint {

    /**
     * Durée minimum en minute
     */
    private int minimalSpan;

    /**
     * Durée maximum en minute
     */
    private int maximalSpan;

    /**
     * Constructeur de la classe PrecedenceConstraintWithDuration
     *
     * @param first       Première Activité
     * @param second      Seconde Activité
     * @param minimalSpan Durée minimale en minutes
     * @param maximalSpan Durée maximale en minutes
     */
    public PrecedenceConstraintWithDuration(Activity first, Activity second,
                                            int minimalSpan, int maximalSpan) {
        super(first, second);
        this.minimalSpan = minimalSpan;
        this.maximalSpan = maximalSpan;
    }

    /**
     * Affichage des deux activités et de la durée minimale et maximale en
     * minutes entre la fin de la premiere activité et le début de la seconde.
     *
     * @return Une chaîne de caractères des deux activités et des durées
     * minimale et maximale.
     */
    public String toString() {
        return super.toString() + this.minimalSpan + ", " + this.maximalSpan + ")";
    }

    /**
     * Vérifie que la contrainte de précédence avec durées est satisfaite.
     *
     * @param date1 Date de début de la première activité en heure(s)
     * @param date2 Date de début de la seconde activité en heure(s)
     * @return true si la contrainte est satisfaite, false sinon.
     */
    @Override
    public boolean isSatisfied(int date1, int date2) {
        return (minimalSpan <= date2 * 60 - (date1 * 60 + first.getDuration())
                && date2 * 60 - (date1 * 60 + first.getDuration()) <= maximalSpan);
    }
}