package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class Goalkeeper extends AbstractAction {

    public Goalkeeper(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * Vérifie si la condtion d'activation est remplie
     *
     * @return Un booléen indiquant si les conditions sont respectés
     */
    public boolean isActivable() {
        return (this.sensors.imNearToOurGoal(this.sensors.getTeammates()) && this.sensors.didTheyAttack());
    }

    /**
     * Gardien dans les buts, fonce sur la balle pour la degager si elle a porté d'une sortie du gardien
     */
    public void action() {
        if (this.sensors.getBall().r < 3 * this.abstract_robot.RADIUS) {
            ShootToGoal shoot = new ShootToGoal(this.abstract_robot);
            shoot.action();
        } else {
            Vec2 goalkeeper = new Vec2(this.sensors.getOurGoal());
            Vec2 betweenBallGoal = new Vec2(this.sensors.ChaslesRelation(this.sensors.getBall(), this.sensors.getOurGoal()));
            if (this.sensors.getOurGoal().x < 0) {
                goalkeeper.setx(goalkeeper.x + 3 * this.abstract_robot.RADIUS);
            } else {
                goalkeeper.setx(goalkeeper.x - 3 * this.abstract_robot.RADIUS);
            }
            goalkeeper.sety(betweenBallGoal.y);
            // Si position atteint, immobile et regarde le ballon
            if (goalkeeper.r < this.abstract_robot.RADIUS) {
                this.abstract_robot.setSpeed(this.curr_time, 0);
                goalkeeper.sett(this.sensors.getBall().t);
            }
            this.abstract_robot.setSteerHeading(this.curr_time, goalkeeper.t);
        }

    }
}