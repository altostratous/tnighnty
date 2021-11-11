package representation;

public class Move implements IAction {
    public enum ActionType {
        TURN_RIGHT,
        TURN_LEFT,
        AHEAD,
        BACK,
    }

    ActionType actionType;

    public Move(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {return actionType;}
}
