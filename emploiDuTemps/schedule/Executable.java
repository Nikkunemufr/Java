package schedule;

/**
 * Lit des activités et des contraintes dans des fichiers passés en argument.
 *
 * La lecture des fichiers (.txt) se fait en passant les noms de ceux-ci en
 * argument :
 *
 * Argument 1 : fichier contenant des activités
 * Argument 2 : fichier contenant des contraintes de précédence
 * Argument 3 (optionnel) : fichier contenant des contraintes de connexité
 * Argument 4 (optionnel) : fichier contenant des contraintes de durée maximale
 *
 * @author Alexis MORTELIER
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.io.IOException;


public class Executable {
    public static void main(String[] args) throws IOException {

        if (args.length >= 2) {
            String activitiesFile = args[0];
            String precedenceConstraintsFile = args[1];

            ScheduleReader initActivities = new ScheduleReader();
            ScheduleReader initConstraints = new ScheduleReader();

            Map<String, Activity> activities = initActivities.readActivities(activitiesFile);
            Collection<Constraint> toutesLesContraintes = new ArrayList<>();

            Collection<Constraint> precedenceConstraints =
                    initConstraints.readPrecedenceConstraints(precedenceConstraintsFile, activities);
            toutesLesContraintes.addAll(precedenceConstraints);

            if (args.length >= 3) {
                String meetConstraintsFile = args[2];
                ScheduleReader initMeetConstraints = new ScheduleReader();

                Collection<Constraint> meetConstraints =
                        initMeetConstraints.readMeetConstraints(meetConstraintsFile, activities);
                toutesLesContraintes.addAll(meetConstraints);
            }

            if (args.length == 4) {
                String maxSpanConstraintsFile = args[3];
                ScheduleReader initMaxSpanConstraints = new ScheduleReader();

                Collection<Constraint> maxSpanConstraints =
                        initMaxSpanConstraints.readMaxSpanConstraints(maxSpanConstraintsFile, activities);
                toutesLesContraintes.addAll(maxSpanConstraints);
            }

            Scheduler monEmploiDuTemps = new Scheduler();
            Schedule edt = monEmploiDuTemps.computeSchedule(activities.values(),
                    precedenceConstraints,
                    8);

            System.out.println("Emploi du temps trouvé pour les contraintes de précédence fournies :");
            System.out.println(edt.toStringMultilines());

            if (edt.satisfies(toutesLesContraintes)) {
                System.out.println("Toutes les contraintes sont satisfaites");
            }
			
			else {
                System.out.println("Toutes les contraintes ne sont pas satisfaites");
            }

        }
    }
}
