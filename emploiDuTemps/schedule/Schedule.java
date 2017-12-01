package schedule;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Représente un emploi du temps.
 *
 * @author Alexis MORTELIER
 */
public class Schedule {

    /**
     * Dictionnaire contenant des activités associées à l'heure à laquelle elles
     * commencent.
     */
    private HashMap<Activity, Integer> activities;

    /**
     * Initialise un emploi du temps vide, sans aucune activité planifiée.
     */
    public Schedule() {
        this.activities = new HashMap<>();
    }

    /**
     * Ajoute une activité à l'emploi du temps.
     *
     * @param activity Une activité
     * @param date     Date à laquelle l'activité est à planifier (en heures).
     */
    public void addActivity(Activity activity, int date) {
        activities.put(activity, date * 60);
    }

    /**
     * Ajoute une activité à l'emploi du temps.
     *
     * @param activity Une activité
     * @param heure    Date en heure(s) à laquelle l'activité est à planifier.
     * @param minutes  Date en minutes à laquelle l'activité est à planifier.
     */
    public void addActivity(Activity activity, int heure, int minutes) {
        activities.put(activity, heure * 60 + minutes);
    }

    /**
     * Vérifie que l'emploi du temps passe toutes les contraintes nécessaires.
     *
     * @param constraints La liste des différentes contraintes
     * @return true si l'emploi du temps satisfait toutes les contraintes, false
     * sinon.
     */
    public boolean satisfies(Collection<Constraint> constraints) {
        for (Constraint constraint : constraints) {
			
            if (!constraint.isSatisfied(this)) {
                return false;
            }
			
        }
		
        return true;
    }

    public int getDate(Activity activity) {
        return activities.get(activity);
    }

    /**
     * Tri de la liste d'activités en fonction de leur position dans le temps.
     *
     * @return La liste d'activités triée.
     */
    private ArrayList<Activity> getSortedActivities() {
        ArrayList<Activity> lstActivities = new ArrayList<>(activities.keySet());
		
        Collections.sort(lstActivities, (activity1, activity2) -> {
                    if (activities.get(activity1) > activities.get(activity2))
                        return 1;
					
                    if (activities.get(activity1) == activities.get(activity2))
                        return 0;
					
                    return -1;
                }

        );

        /*
         * Ancienne version de la méthode getSortedActivities() en version tri
         * bulle
         */
//      int longueur = lstActivities.size();
//      Activity tampon;
//		boolean permut;
//
//		do {
//			permut = false;
//			for (int i = 0; i < longueur - 1; i++) {
//	
//				if (activities.get(lstActivities.get(i)) > activities.get(lstActivities.get(i + 1))) {
//					tampon = lstActivities.get(i);
//					lstActivities.set(i, lstActivities.get(i + 1));
//					lstActivities.set(i + 1, tampon);
//					permut = true;
//
//				}
//			}
//		} while (permut);

        return lstActivities;
    }

    /**
     * Affiche l'emploi du temps.
     *
     * @return Une chaîne de caractères représentant l'emploi du temps avec les
     * activités, leur durée et leur heure de début.
     */
    public String toString() {
        ArrayList<Activity> sortedActivities = getSortedActivities();
        StringBuilder res = new StringBuilder();

        for (Activity activity : sortedActivities) {
            res.append(activity)
                    .append(" : ")
                    .append(activities.get(activity))
                    .append("h  ; ");
        }

        return "[" + res + "]";
    }

    public String toStringMultilines() {
        StringBuilder displaySchedule = new StringBuilder();
        ArrayList<Activity> tri = getSortedActivities();

        for (Activity activity : tri) {
			
            if (activities.get(activity) % 60 > 9) {
                displaySchedule.append(System.lineSeparator())
                        .append("* ")
                        .append(activity.getDescription())
                        .append(" : ")
                        .append(activities.get(activity) / 60)
                        .append("H")
                        .append(activities.get(activity) % 60);
            }
			
			else {
                displaySchedule.append(System.lineSeparator())
                        .append("* ")
                        .append(activity.getDescription())
                        .append(" : ")
                        .append(activities.get(activity) / 60)
                        .append("H0")
                        .append(activities.get(activity) % 60);
            }
			
        }

        return displaySchedule.toString();
    }
}
