package representation;

import robocode.Robot;

public class MementoActionRepresentation implements IActionRepresentation {
    public MementoActionRepresentation(IActionRepresentation tNinetyActionRepresentation) {
    }

    @Override
    public void takeAction(Robot robot, IAction action) {

    }

    @Override
    public IAction[] getActions() {
        return new IAction[0];
    }
}
