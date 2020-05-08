package action;

import EDU.gatech.cc.is.abstractrobot.*;

public class FactoryAction {
    SocSmall abstract_robot;

    public FactoryAction(SocSmall abstract_robot) {
        this.abstract_robot = abstract_robot;
    }

    /**
     * Chaine de priorité de la stratégie, Tir > goal > placement milieu de terrain > interception > esquive allié > esquive adverse > déplcement en nuée
     *
     * @return Retourne la chaine d'action de la strategie.
     */
    public ChainAction generateStrategy() {
        ShootToGoal shoot = new ShootToGoal(this.abstract_robot);
        Goalkeeper goal = new Goalkeeper(this.abstract_robot);
        GoToMiddleTerrain middleTerrain = new GoToMiddleTerrain(this.abstract_robot);
        Interception interception = new Interception(this.abstract_robot);
        AwayFromTeammates awayteammates = new AwayFromTeammates(this.abstract_robot);
        AwayFromOpponents awayopponents = new AwayFromOpponents(this.abstract_robot);
        Nuee nuee = new Nuee(this.abstract_robot);
        ChainAction chainNuee = new ChainAction(nuee, null);
        ChainAction chainAwayFromOpponents = new ChainAction(awayopponents, chainNuee);
        ChainAction chainAwayFromTeammates = new ChainAction(awayteammates, chainAwayFromOpponents);
        ChainAction chainInterception = new ChainAction(interception, chainAwayFromTeammates);
        ChainAction chainGoToMiddleTerrain = new ChainAction(middleTerrain, chainInterception);
        ChainAction chainGoalkeeper = new ChainAction(goal, chainGoToMiddleTerrain);
        ChainAction chainShootToGoal = new ChainAction(shoot, chainGoalkeeper);
        return chainShootToGoal;
    }
}