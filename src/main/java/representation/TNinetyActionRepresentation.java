package representation;

import robocode.Robot;

public class TNinetyActionRepresentation implements IActionRepresentation {
    @Override
    public void takeAction(Robot qLearningRobot, IAction action) {
        if (action == null) return;
        if (!(action instanceof TNinetyAction)) {
            throw new IllegalArgumentException("TNinety representation can only take TNinety actions.");
        }
        TNinetyAction move = (TNinetyAction) action;
        if (move.getActionType() == TNinetyAction.ActionType.TURN_LEFT) {
            qLearningRobot.turnLeft(90);
        } else if (move.getActionType() == TNinetyAction.ActionType.TURN_RIGHT) {
            qLearningRobot.turnRight(90);
        } else if (move.getActionType() == TNinetyAction.ActionType.AHEAD) {
            qLearningRobot.ahead(100);
        } else if (move.getActionType() == TNinetyAction.ActionType.FIRE) {
            qLearningRobot.fire(1);
        }
    }

    @Override
    public IAction[] getActions() {
        return new IAction[] {
            new TNinetyAction(TNinetyAction.ActionType.AHEAD),
            new TNinetyAction(TNinetyAction.ActionType.TURN_LEFT),
            new TNinetyAction(TNinetyAction.ActionType.TURN_RIGHT),
            new TNinetyAction(TNinetyAction.ActionType.FIRE),
        };
    }
}
