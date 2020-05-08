package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class GoToMiddleTerrain extends AbstractAction {

    public GoToMiddleTerrain(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * Vérifie si la condtion d'activation est remplie
     *
     * @return Un booléen indiquant si les conditions sont respectés
     */
    public boolean isActivable() {
        return ((this.sensors.imFarthestToOurGoal(this.sensors.getTeammates()) && this.sensors.isOnMySide()) || (this.sensors.imNearToOurGoal(this.sensors.getTeammates()) && !this.sensors.isOnMySide()));
    }

    /**
     * Va au milieu de terrain sur le cercle du milieu de terrain et regarde le but ennemie
     */
    public void action() {
        Vec2 player = new Vec2(this.sensors.getMiddleTerrain());
        if (this.sensors.getOurGoal().x < 0) {
            player.setx(player.x - 6 * this.abstract_robot.RADIUS);
        } else {
            player.setx(player.x + 6 * this.abstract_robot.RADIUS);
        }
        // Si position atteinte, on ne bouge plus et on regarde le but ennemie.
        if (player.r < this.abstract_robot.RADIUS) {
            this.abstract_robot.setSpeed(this.curr_time, 0);
            player.sett(this.sensors.getTheirGoal().t);
        }
        this.abstract_robot.setSteerHeading(this.curr_time, player.t);
    }
}