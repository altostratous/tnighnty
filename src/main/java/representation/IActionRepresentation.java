package representation;

import robocode.Robot;

public interface IActionRepresentation extends IRepresentation {
    void takeAction(Robot robot, IAction action);

    IAction[] getActions();
}
