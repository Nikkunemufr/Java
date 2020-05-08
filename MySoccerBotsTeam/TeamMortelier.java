/**
 * TeamMortelier.java
 * Développé dans le cadre de ma formation de Master Informatique spécialité Décision et OPtimisation.
 *
 * @author: Alexis Mortelier
 */

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import action.*;
import sensors.*;

public class TeamMortelier extends ControlSystemSS {
    /**
     * Configure the control system.  This method is
     * called once at initialization time.  You can use it
     * to do whatever you like.
     */
    public void Configure() {
        // not used in this example.
    }

    /**
     * Called every timestep to allow the control system to
     * run.
     */
    public int TakeStep() {
        long curr_time = abstract_robot.getTime();
        abstract_robot.setSpeed(curr_time, 1);
        FactoryAction strategy = new FactoryAction(abstract_robot);
        ChainAction action = strategy.generateStrategy();
        action.bestAction();
        // kick it if we can
        if (abstract_robot.canKick(curr_time)) {
            abstract_robot.kick(curr_time);
        }

        // tell the parent we're OK
        return (CSSTAT_OK);
    }
}