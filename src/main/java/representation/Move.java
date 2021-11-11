package representation;

import java.io.Serializable;
import java.util.Objects;

public class Move implements IAction, Serializable {
    @Override
    public double[] toVector() {
        double value = 0;
        switch (actionType) {
            case AHEAD:
                value = 1;
                break;
            case TURN_LEFT:
                value = 2;
                break;
            case TURN_RIGHT:
                value = 3;
                break;
            default:
                assert false;
        }
        return new double[] { value };
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
    }

    ActionType actionType;

    public Move(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {return actionType;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return actionType == move.actionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionType);
    }
}
