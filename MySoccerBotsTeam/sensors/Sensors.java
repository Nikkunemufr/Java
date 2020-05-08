package sensors;

import EDU.gatech.cc.is.util.Vec2;
import EDU.gatech.cc.is.abstractrobot.*;

public class Sensors {

    private SocSmall abstract_robot;
    private long curr_time;

    public Sensors(SocSmall abstract_robot) {
        this.abstract_robot = abstract_robot;
        this.curr_time = abstract_robot.getTime();
    }

    /**
     * Relation de Chasles entre deux vecteurs afin de se placer sur le vecteur à un certain coefficient
     *
     * @param vecteur1 vecteur 1
     * @param vecteur2 vecteur 2
     * @param coeff    coefficient de distance sur le vecteur cible
     * @return vecteur vecteur cible
     */
    public Vec2 ChaslesRelationWithCoeff(Vec2 vecteur1, Vec2 vecteur2, double coeff) {
        Vec2 vec1 = new Vec2(vecteur1);
        vec1.sub(vecteur2);
        vec1.setr(vec1.r * coeff);
        Vec2 vec2 = new Vec2(vecteur2);
        vec2.add(vec1);
        return vec2;
    }

    /**
     * Relation de Chasles entre deux vecteurs afin de se placer sur le vecteur à mi distance
     *
     * @param vecteur1 vecteur 1
     * @param vecteur2 vecteur 2
     * @return vecteur cible
     */
    public Vec2 ChaslesRelation(Vec2 vecteur1, Vec2 vecteur2) {
        return ChaslesRelationWithCoeff(vecteur1, vecteur2, 0.5);
    }

    /**
     * Retourne les alliés de l'agent
     *
     * @return liste de vecteurs de l'agent vers ses alliés
     */
    public Vec2[] getTeammates() {
        return this.abstract_robot.getTeammates(this.curr_time);
    }

    /**
     * Retourne les adversaires de l'agent
     *
     * @return liste de vecteurs de l'agent vers ses adversaires
     */
    public Vec2[] getOpponents() {
        return this.abstract_robot.getOpponents(this.curr_time);
    }

    /**
     * Retourne le vecteur de l'agent à la balle
     *
     * @return le vecteur de l'agent à la balle
     */
    public Vec2 getBall() {
        return this.abstract_robot.getBall(this.curr_time);
    }

    /**
     * Retourne le vecteur de l'agent a son but
     *
     * @return le vecteur de l'agent a son but
     */
    public Vec2 getOurGoal() {
        return this.abstract_robot.getOurGoal(this.curr_time);
    }

    /**
     * Retourne le vecteur de l'agent au but adverse
     *
     * @return le vecteur de l'agent au but adverse
     */
    public Vec2 getTheirGoal() {
        return this.abstract_robot.getOpponentsGoal(this.curr_time);
    }

    /**
     * Retourne le vecteur indiquant le milieu de terrain + une marge pour anticiper le repli
     *
     * @return Retourne le vecteur indiquant le milieu de terrain
     */
    public Vec2 getMiddleTerrain() {
        Vec2 vec = new Vec2(ChaslesRelation(getOurGoal(), getTheirGoal()));
        if (getOurGoal().x > 0) {
            vec.setx(vec.x - 3 * this.abstract_robot.RADIUS);
        } else {
            vec.setx(vec.x + 3 * this.abstract_robot.RADIUS);
        }
        return vec;
    }

    /**
     * Indique l'agent le plus proche
     *
     * @param playerList liste des vecteurs de l'agent vers les autres agents
     * @return retourne l'agent le plus proche
     */
    public Vec2 nearestPlayer(Vec2[] playerList) {
        Vec2 nearestPlayer = new Vec2(99999, 0);
        for (int i = 0; i < playerList.length; i++) {
            if (playerList[i].r < nearestPlayer.r) {
                nearestPlayer = playerList[i];
            }
        }
        return nearestPlayer;
    }

    /**
     * Calcul le vecteur de l'agent le plus proche de la cible
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @param target  cible
     * @return retorune le vecteur de l'agent le plus proche de la cible
     */
    public Vec2 nearestTo(Vec2[] players, Vec2 target) {
        Vec2 nearestTo = new Vec2(99999, 0);
        for (int i = 0; i < players.length; i++) {
            Vec2 vec = new Vec2(target);
            vec.sub(players[i]);
            if (nearestTo.r > vec.r)
                nearestTo = vec;
        }
        return nearestTo;
    }

    /**
     * Calcul le vecteur de l'agent le plus proche des buts alliés
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return retorune le vecteur de l'agent le plus proche des buts alliés
     */
    public Vec2 nearestToOurGoal(Vec2[] players) {
        return nearestTo(players, getOurGoal());
    }

    /**
     * Calcul le vecteur de l'agent le plus proche des buts adverses
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return retorune le vecteur de  l'agent le plus proche des buts adverses
     */
    public Vec2 nearestToTheirGoal(Vec2[] players) {
        return nearestTo(players, getTheirGoal());
    }

    /**
     * Calcul le vecteur de l'agent le plus proche du ballon
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return retorune le vecteur de  l'agent le plus proche du ballon
     */
    public Vec2 nearestToTheBall(Vec2[] players) {
        return nearestTo(players, getBall());
    }

    /**
     * Esquive l'agent le plus proche
     *
     * @param closestPlayers agent le plus proche
     * @return Retourne le vecteur évitant la collision entre l'agent et l'agent le plus proche
     */
    private Vec2 awayFrom(Vec2 closestPlayers) {
        Vec2 awayfromclosest = new Vec2(closestPlayers.x, closestPlayers.y);
        awayfromclosest.sett(awayfromclosest.t + Math.PI);
        return awayfromclosest;
    }

    /**
     * Esquive l'adversaire le plus proche
     *
     * @return Retourne le vecteur évitant la collision entre l'agent et l'adversaire le plus proche
     */
    public Vec2 awayFromOpponents() {
        return awayFrom(nearestPlayer(getOpponents()));
    }

    /**
     * Esquive l'adversaire le plus proche
     *
     * @return Retourne le vecteur évitant la collision entre l'agent et l'allié le plus proche
     */
    public Vec2 awayFromTeammates() {
        return awayFrom(nearestPlayer(getTeammates()));
    }

    /**
     * Calcul le vecteur de l'agent vers le barycentre de son équipe.
     *
     * @param teammates liste des agents alliés
     * @return Retourne le vecteur de l'agent vers le barycentre de son équipe
     */
    public Vec2 getBarycentre(Vec2[] teammates) {
        Vec2 barycentre = new Vec2(0, 0);
        for (int i = 0; i < teammates.length; i++) {
            barycentre.add(teammates[i]);
        }
        barycentre.setx(barycentre.x / teammates.length);
        barycentre.sety(barycentre.y / teammates.length);
        return barycentre;
    }

    /**
     * Indique si oui ou non l'agent est le plus proche de la cible ou s'il existe un agent allié plus proche
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @param target  cible
     * @return Retourne un booléen indiquant si l'agent est le plus proche de la cible
     */
    public boolean imNearTo(Vec2[] players, Vec2 target) {
        for (int i = 0; i < players.length; i++) {
            Vec2 vec = new Vec2(players[i]);
            vec.sub(target);
            if (vec.r < target.r) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indique si oui ou non l'agent est le plus proche du ballon ou s'il existe un agent allié plus proche
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return Retourne un booléen indiquant si l'agent est le plus proche du ballon
     */
    public boolean imNearToBall(Vec2[] players) {
        return imNearTo(players, getBall());
    }

    /**
     * Indique si oui ou non l'agent est le plus proche des buts alliés ou s'il existe un agent allié plus proche
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return Retourne un booléen indiquant si l'agent est le plus proche des buts alliés
     */
    public boolean imNearToOurGoal(Vec2[] players) {
        return imNearTo(players, getOurGoal());
    }

    /**
     * Indique si oui ou non l'agent est le plus loin de la cible ou s'il existe un agent allié plus loin
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return Retourne un booléen indiquant si l'agent est le plus loin de la cible
     */
    public boolean imFarthestTo(Vec2[] players, Vec2 target) {
        for (int i = 0; i < players.length; i++) {
            Vec2 vec = new Vec2(players[i]);
            vec.sub(target);
            if (vec.r > target.r) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indique si oui ou non l'agent est le plus loin de la balle ou s'il existe un agent allié plus proche
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return Retourne un booléen indiquant si l'agent est le plus loin de la balle
     */
    public boolean imFarthestToBall(Vec2[] players) {
        return imFarthestTo(players, getBall());
    }

    /**
     * Indique si oui ou non l'agent est le plus loin des buts alliés ou s'il existe un agent allié plus loin
     *
     * @param players liste des vecteurs de l'agent vers les autres agents
     * @return Retourne un booléen indiquant si l'agent est le plus loin des buts alliés
     */
    public boolean imFarthestToOurGoal(Vec2[] players) {
        return imFarthestTo(players, getOurGoal());
    }

    /**
     * Indique a l'agent qu'elle est l'agent qu'il doit marqué. L'agent le plus proche de lui est peut être plus proche
     * d'un autre agent. Donc il prendra le joueur le plus proche de lui et le plus loin de ses agents alliés
     *
     * @param teammates liste des vecteurs de l'agent vers les agents alliés
     * @param opponents liste des vecteurs de l'agent vers les agents adverses
     * @return Retourne un booléen indiquant si l'agent doit marquer un agent.
     */
    public boolean IGuardThisPlayer(Vec2[] teammates, Vec2[] opponents) {
        Vec2 vec = nearestPlayer(opponents);
        for (int i = 0; i < teammates.length; i++) {
            Vec2 vec2 = teammates[i];
            vec2.sub(vec);
            if (vec2.r < vec.r) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indique si oui ou non l'équipe ennemie possede la balle
     *
     * @return Retourne un booléen indiquant si l'équipe adverse possède la balle
     */
    public boolean theyHaveBall() {
        Vec2 nearestMateToTheBall = nearestToTheBall(getTeammates());
        Vec2 nearestOpponentToTheBall = nearestToTheBall(getOpponents());
        if (nearestMateToTheBall.r < nearestOpponentToTheBall.r) {
            return true;
        }
        return false;
    }

    /**
     * Indique si oui ou non la balle est du côté allié du terrain
     *
     * @return Retourne un booléen indiquant si la balle est du côté allié du terrain
     */
    public boolean isOnMySide() {
        if (getBall().x < getMiddleTerrain().x) {
            return true;
        }
        return false;
    }

    /**
     * Indique si l'équipe adverse attaque
     *
     * @return Retourne un booléen indiquant si les adversaires ont la balle ou si la balle est du côté allié du terrain
     */
    public boolean didTheyAttack() {
        if (theyHaveBall() || isOnMySide()) {
            return true;
        }
        return false;
    }
}