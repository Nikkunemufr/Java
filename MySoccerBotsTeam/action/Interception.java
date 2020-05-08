package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class Interception extends AbstractAction {

    public Interception(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * Vérifie si la condtion d'activation est remplie
     *
     * @return Un booléen indiquant si les conditions sont respectés
     */
    public boolean isActivable() {
        return (this.sensors.theyHaveBall() && this.sensors.isOnMySide());
    }

    /**
     * Intercepte l'agent adverse que l'agent doit marqué. Le plus proche de lui selon si aucun allié est plus proche que lui.
     */
    public void action() {
        Vec2[] opponents = this.sensors.getOpponents();
        Vec2[] teammates = this.sensors.getTeammates();
        Vec2 interception = new Vec2(this.sensors.ChaslesRelationWithCoeff(this.sensors.nearestPlayer(opponents), this.sensors.getOurGoal(), 0.7));
        for (int i = 0; i < opponents.length; i++) {
            if (this.sensors.IGuardThisPlayer(teammates, opponents)) {
                interception = new Vec2(this.sensors.ChaslesRelationWithCoeff(opponents[i], this.sensors.getOurGoal(), 0.7));
            }
        }
        this.abstract_robot.setSteerHeading(this.curr_time, interception.t);
    }
}