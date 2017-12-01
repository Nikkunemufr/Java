package schedule;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Collection;

/**
 * Calcule un emploi du temps à partir d'activités associées à des contraintes.
 *
 * @author Alexis MORTELIER
 */
public class Scheduler {

    /**
     * Retourne une activité planifiable.
     *
     * @param activities  Une liste d'activités
     * @param constraints Une liste de contraintes
     * @param scheduled   La liste d'activités déjà planifiées.
     * @return Une activité planifiable
     */
    private Activity next(Collection<Activity> activities,
                          Collection<Constraint> constraints,
                          Collection<Activity> scheduled) {
        boolean schedulable;

        for (Activity activity : activities) {
            schedulable = true;

            for (Constraint constraint : constraints) {
                PrecedenceConstraint precedenceConstraint = (PrecedenceConstraint) constraint;

                if ((activity.equals(precedenceConstraint.second))
                        && !(scheduled.contains(precedenceConstraint.first))) {
                    schedulable = false;
                    break;
                }
            }

            if (schedulable) {
                return activity;
            }
        }

        throw new NoSuchElementException("Aucune activité planifiable.");
    }

    /**
     * Calcule un emploi du temps à partir d'activités associées à des
     * contraintes et retourne cet emploi du temps.
     *
     * @param activities   Une liste d'activités à planifier
     * @param constraints  Une liste de contraintes à respecter
     * @param startingHour Heure de début de l'emploi du temps
     * @return Un emploi du temps d'activités planifiées respectant les
     * contraintes données.
     */
    public Schedule computeSchedule(Collection<Activity> activities,
                                    Collection<Constraint> constraints,
                                    int startingHour) {

        /* Création d'un emploi du temps à retourner */
        Schedule emploiDuTemps = new Schedule();

        /* Création d'une liste destinée à contenir des activités planifiées */
        Collection<Activity> scheduled = new ArrayList<>();
        startingHour *= 60;  /* Conversion de l'heure de début en minutes */

        while (activities.size() > 0) {

            /* Affectation de la prochaine activité planifiable */
            Activity nextSchedulableActivity = next(activities, constraints, scheduled);

            /* Ajout de l'activité planifiable à l'emploi du temps */
            emploiDuTemps.addActivity(nextSchedulableActivity, startingHour / 60, startingHour % 60);

            /*
             * Ajout de l'activité planifiable dans liste des activités déjà
             * planifiées
             */
            scheduled.add(nextSchedulableActivity);

            /*
             * Retrait de l'activité planifiable de la liste des activités
             * restant à traiter
             */
            activities.remove(nextSchedulableActivity);

            /* Incrémentation de l'heure par rapport à la précedente activité */
            startingHour += nextSchedulableActivity.getDuration();
        }

        return emploiDuTemps;

    }

}