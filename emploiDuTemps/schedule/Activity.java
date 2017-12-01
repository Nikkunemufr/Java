package schedule;

/**
 * Représente des activités avec une description et une durée associées.
 *
 * @author Alexis MORTELIER
 */
public class Activity {

    /**
     * Description (étiquette) de l'activité
     */
    private String description;

    /**
     * Durée de l'activité, en minutes
     */
    private int duration;

    /**
     * Constructeur de la classe Activity
     *
     * @param description Description (étiquette) de l'activité
     * @param duration    Durée de l'activitée en minutes
     */
    public Activity(String description, int duration) {
        this.description = description;
        this.duration = duration;
    }

    /**
     * Retourne la description de l'activité.
     *
     * @return Une chaîne de caractères représantant la description de
     * l'activité.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retourne la durée de l'activité.
     *
     * @return Un entier représantant la durée de l'activité en minutes.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Retourne la représentation de l'activité.
     *
     * @return Une chaîne de caractères représantant l'activité courante sous la
     * forme : (description, durée).
     */
    public String toString() {
        return "(" + this.description + ", " + this.duration + ")";
    }

    /**
     * Compare l'activité courante à une activité passée en paramètre.
     *
     * @param activity Activité à comparer
     * @return true si l'activité courant correspond à celle passée en
     * paramètre, false sinon.
     */
    public boolean equals(Activity activity) {
        boolean bool = false;

        if (this.getDuration() == activity.getDuration() && this.getDescription() == activity.getDescription()) {
            bool = true;
        }

        return bool;
    }

}
