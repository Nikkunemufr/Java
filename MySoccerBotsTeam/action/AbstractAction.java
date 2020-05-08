package action;

import EDU.gatech.cc.is.abstractrobot.*;
import sensors.Sensors;

public abstract class AbstractAction {
    protected SocSmall abstract_robot;
    protected long curr_time;
    protected Sensors sensors;

    public AbstractAction(SocSmall abstract_robot) {
        this.abstract_robot = abstract_robot;
        this.curr_time = abstract_robot.getTime();
        this.sensors = new Sensors(this.abstract_robot);
    }

    public abstract boolean isActivable();

    public abstract void action();
}