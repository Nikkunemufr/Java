package action;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;
import sensors.*;

public class Nuee extends AbstractAction {

    public Nuee(SocSmall abstract_robot) {
        super(abstract_robot);
    }

    /**
     * @return retourne true car derniere action possible dans la stratégie établie
     */
    public boolean isActivable() {
        return true;
    }

    /**
     * Deplacement en nuée
     */
    public void action() {
        Vec2[] teammates = this.sensors.getTeammates();
        Vec2 cohesion = new Vec2(this.sensors.getBarycentre(teammates));
        Vec2 separation = new Vec2(0, 0);

        int j = 0;
        for (int i = 0; i < teammates.length; i++) {
            if (teammates[i].r < 0.5) {
                separation.add(teammates[i]);
                j = j + 1;
            }
        }
        separation.setx(-separation.x / j);
        separation.sety(-separation.y / j);
        cohesion.setr(cohesion.r * 0.8);
        cohesion.add(separation);
        this.abstract_robot.setSteerHeading(this.curr_time, cohesion.t);
    }
}