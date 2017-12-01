package schedule;

import java.util.ArrayList;

/**
 * Contrainte de durée d'un ensemble d'activités.
 *
 * @author Alexis MORTELIER
 */
public class MaxSpanConstraint implements Constraint {

    /**
     * Ensemble des activités
     */
    private ArrayList<Activity> activities;

    /**
     * Durée maximum entre le moment où la première activité commence et le
     * moment où la dernière activité se termine.
     */
    private int maxSpan;

    /**
     * Constructeur de la classe MaxSpanConstraint
     *
     * @param activities Une liste d'activités
     * @param maxSpan    Durée maximum entre le moment où la première activité
     *                   commence et le moment où la dernière activité se
     *                   termine.
     */
    public MaxSpanConstraint(ArrayList<Activity> activities, int maxSpan) {
        this.activities = activities;
        this.maxSpan = maxSpan;
    }

    /**
     * Parcourt l'ensemble des activités et additionne leurs durées afin de
     * vérifier si la durée totale de l'ensemble des activités satisfait la
     * contrainte.
     *
     * @param emploiDuTemps Un emploi du temps
     * @return true si la contrainte est respectée, false sinon.
     */
    public boolean isSatisfied(Schedule emploiDuTemps) {
        Integer startingDate = null;
        Integer endingDate = null;

        for (Activity activity : activities) {
            int scheduleStartingDate = emploiDuTemps.getDate(activity);
            int scheduleMinutesStartingDate = scheduleStartingDate * 60;

            if (startingDate == null || scheduleMinutesStartingDate <= startingDate) {
                startingDate = scheduleMinutesStartingDate;
            }

            if (endingDate == null || scheduleMinutesStartingDate + activity.getDuration() >= endingDate) {
                endingDate = scheduleMinutesStartingDate + activity.getDuration();
            }
        }

        /* endingDate = null s'il n'y a aucune activité */
        if (endingDate == null || endingDate - startingDate <= maxSpan) {
            return true;
        }

        return false;
    }
}