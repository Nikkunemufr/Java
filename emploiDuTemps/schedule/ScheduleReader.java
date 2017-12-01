package schedule;

/**
 * Lit des activités et des contraintes dans un fichier.
 *
 * @author Alexis MORTELIER
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import scheduleio.OrderedPair;
import scheduleio.OrderedPairReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import scheduleio.StringsStringReader;
import scheduleio.IdStringStringReader;


public class ScheduleReader {
    /**
     * Lit des activités dans un fichier.
     *
     * @param filename Le nom du fichier
     * @return Une Map des activités lues dans le fichier.
     */
    public Map<String, Activity> readActivities(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        IdStringStringReader activityReader = new IdStringStringReader(fileReader, "=", "_lasting_");
        Map<String, Activity> activities = new HashMap<>();

        for (Map.Entry<String, OrderedPair<String, String>> strings : activityReader.readAll().entrySet()) {
            String id = strings.getKey();
            OrderedPair<String, String> activityStrings = strings.getValue();
            String description = activityStrings.getFirst();
            int duration = Integer.parseInt(activityStrings.getSecond());
            Activity activity = new Activity(description, duration);
            activities.put(id, activity);
        }

        return activities;
    }

    /**
     * Lit des contraintes de précédence dans un fichier.
     *
     * @param filename       Le nom du fichier
     * @param readActivities Une Map comportant les activités lues.
     * @return Une collection de contraintes de précédence
     */
    public Collection<Constraint> readPrecedenceConstraints(String filename, 
															Map<String, Activity> readActivities)throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        OrderedPairReader precedenceConstraintReader = new OrderedPairReader(fileReader, readActivities.keySet(), "_before_");
        Collection<Constraint> allPrecedenceConstraints = new ArrayList<>();

        for (OrderedPair<String, String> id : precedenceConstraintReader.readAll()) {
            PrecedenceConstraint precedenceConstraint = new PrecedenceConstraint(readActivities.get(id.getFirst()), readActivities.get(id.getSecond()));
            allPrecedenceConstraints.add(precedenceConstraint);
        }

        return allPrecedenceConstraints;
    }

    /**
     * Lit des contraintes de connexité (MeetConstraint) dans un fichier
     *
     * @param filename       Le nom du fichier
     * @param readActivities Une Map comportant les activités lues
     * @return Une collection des contraintes de connexité (MeetConstraint).
     */
    public Collection<Constraint> readMeetConstraints(String filename,
                                                      Map<String, Activity> readActivities) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        OrderedPairReader MeetConstraintReader = new OrderedPairReader(fileReader, readActivities.keySet(), "_meets_");
        Collection<Constraint> allMeetConstraints = new ArrayList<>();
		
        for (OrderedPair<String, String> id : MeetConstraintReader.readAll()) {
            MeetConstraint meetConstraint = new MeetConstraint(readActivities.get(id.getFirst()), readActivities.get(id.getSecond()));
            allMeetConstraints.add(meetConstraint);
        }

        return allMeetConstraints;
    }

    /**
     * Lit des contraintes de durée maximale (MaxSpanConstraint) dans un
     * fichier.
     *
     * @param filename       Le nom du fichier
     * @param readActivities Une Map comportant les activités lues.
     * @return Une collection des contraintes de durée maximale
     * (MaxSpanConstraint).
     */
    public Collection<Constraint> readMaxSpanConstraints(String filename,
                                                         Map<String, Activity> readActivities) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        StringsStringReader MaxSpanConstraintReader = new StringsStringReader(fileReader, readActivities.keySet(), ",", "_within_");
        Collection<Constraint> allMaxSpanConstraints = new ArrayList<>();
		
        for (OrderedPair<List<String>, String> id : MaxSpanConstraintReader.readAll()) {
            ArrayList<Activity> activities = new ArrayList<>();

            for (String activity : id.getFirst()) {
                activities.add(readActivities.get(activity));
            }

            int duration = Integer.parseInt(id.getSecond());
            MaxSpanConstraint maxSpanConstraint = new MaxSpanConstraint(activities, duration);
            allMaxSpanConstraints.add(maxSpanConstraint);
        }

        return allMaxSpanConstraints;
    }
}

