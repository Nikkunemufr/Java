package schedule;

/**
 * Contrainte de connexité entre deux activités.
 *
 * @author Alexis MORTELIER
 */
public class MeetConstraint extends BinaryConstraint {

    public MeetConstraint(Activity first, Activity second) {
        super(first, second);
    }

    /**
     * Vérification de la satisfaction de la contrainte de connexité, i.e. si
     * une seconde activité commence exactement lorsque la première se termine.
     *
     * @param date1 Date de début de la première activités en heure(s)
     * @param date2 Date de début de la première activités en heure(s)
     * @return true si la contrainte est satisfaite, false sinon.
     */
    @Override
    public boolean isSatisfied(int date1, int date2) {
        return date2 * 60 + first.getDuration() == date2 * 60;
    }

}
