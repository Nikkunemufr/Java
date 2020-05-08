package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class AwayFromTeammates extends AbstractAction {

    public AwayFromTeammates(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * Vérifie si la condtion d'activation est remplie
     *
     * @return Un booléen indiquant si les conditions sont respectés
     */
    public boolean isActivable() {
        return (this.sensors.awayFromTeammates().r < 2 * this.abstract_robot.RADIUS);
    }

    /**
     * Esquive l'agent allié
     */
    public void action() {
        Vec2 away = new Vec2(this.sensors.awayFromTeammates());
        this.abstract_robot.setSteerHeading(this.curr_time, away.t);
    }
}