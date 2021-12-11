package representation;

import java.io.Serializable;
import java.util.Objects;

public class TNinetyAction implements IAction, Serializable {
    @Override
    public double[] toVector() {
        var value = new double[] {0, 0, 0, 0};
        switch (actionType) {
            case AHEAD:
                value = new double[] {1, 0, 0, 0};
                break;
            case TURN_LEFT:
                value = new double[] {0, 1, 0, 0};
                break;
            case TURN_RIGHT:
                value = new double[] {0, 0, 1, 0};
                break;
            case FIRE:
                value = new double[] {0, 0, 0, 1};
                break;
            default:
                assert false;
        }
        return value;
    }

    @Override
    public String toString() {
        return "Move{" +
                "actionType=" + actionType +
                '}';
    }

    public enum ActionType {
        TURN_RIGHT,
        TURN_LEFT,
        AHEAD,
        FIRE,
        RANDOMLY_MOVE,
    }

    ActionType actionType;

    public TNinetyAction(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {return actionType;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TNinetyAction that = (TNinetyAction) o;
        return actionType == that.actionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType);
    }
}
