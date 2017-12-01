package schedule;

/**
 * Représente une contrainte binaire
 *
 * @author Alexis MORTELIER
 */
abstract class BinaryConstraint implements Constraint {

    /**
     * Première activité comportant une description et une durée en minutes
     */
    protected Activity first;

    /**
     * Seconde activité comportant une description et une durée en minutes
     */
    protected Activity second;

    /**
     * Constructeur de la classe BinaryConstraint
     *
     * @param first  Première activité comportant une description et une durée
     *               en minutes.
     * @param second Seconde activité comportant une description et une durée en
     *               minutes.
     */
    public BinaryConstraint(Activity first, Activity second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Méthode abstraite de satisfaction la contrainte
     *
     * @param date1 Date de la première activité en minutes
     * @param date2 Date de la seconde activité en minutes
     * @return true si la contrainte est satisfaite, false sinon.
     */
    abstract boolean isSatisfied(int date1, int date2);

    /**
     * Vérification de satisfaction de la contrainte pour deux dates de l'emploi
     * du temps donné.
     *
     * @param emploiDuTemps Un emploi du temps
     * @return true si la contrainte est satisfaite, false sinon.
     */
    //je vérifie si les deux dates de l'emploi du temps satisfont la contrainte
    @Override//On redéfinie la méthode
    public boolean isSatisfied(Schedule emploiDuTemps) {
        int firstDate = emploiDuTemps.getDate(first);
        int secondDate = emploiDuTemps.getDate(second);

        return isSatisfied(firstDate, secondDate);
    }

}
