package representation;

import robocode.Robot;

public class MoveRepresentation implements IActionRepresentation {
    @Override
    public void takeAction(Robot qLearningRobot, IAction action) {
        if (!(action instanceof Move)) {
            throw new IllegalArgumentException("Move representation can only take move actions.");
        }
        Move move = (Move) action;
        if (move.getActionType() == Move.ActionType.TURN_LEFT) {
            qLearningRobot.turnLeft(90);
        } else if (move.getActionType() == Move.ActionType.TURN_RIGHT) {
            qLearningRobot.turnRight(90);
        } else if (move.getActionType() == Move.ActionType.AHEAD) {
            qLearningRobot.ahead(100);
        } else if (move.getActionType() == Move.ActionType.BACK) {
            qLearningRobot.back(100);
        }
    }
}
