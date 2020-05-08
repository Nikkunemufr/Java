package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class ShootToGoal extends AbstractAction {

    public ShootToGoal(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * Vérifie si la condtion d'activation est remplie
     *
     * @return Un booléen indiquant si les conditions sont respectés
     */
    public boolean isActivable() {
        return this.sensors.imNearToBall(this.sensors.getTeammates());
    }

    /**
     * Force l'agent à se placer derriere la balle pour tirer vers le but adverse
     */
    public void action() {
        Vec2 shoot = new Vec2(this.sensors.getBall());
        shoot.sub(this.sensors.getTheirGoal());
        shoot.setr(this.abstract_robot.RADIUS);
        shoot.add(this.sensors.getBall());
        this.abstract_robot.setSteerHeading(this.curr_time, shoot.t);
    }
}