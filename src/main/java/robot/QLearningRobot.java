package robot;

import representation.IAction;
import representation.IState;
import rl.ILearning;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

public class QLearningRobot extends Robot {
    private ILearning learning;
    private IState state;
    private IState lastState;

    public QLearningRobot(ILearning learning) {


        this.learning = learning;
    }

    public ILearning getLearning() {
        return learning;
    }

    @Override
    public void onStatus(StatusEvent e) {
        super.onStatus(e);
        setState(learning.getStateRepresentation().represent(getState(), e));
        IAction action = learning.takeStep(getLastState(), getState());
        learning.getActionRepresentation().takeAction(this, action);
    }

    private IState getLastState() {
        return lastState;
    }

    public IState getState() {
        return this.state;
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        super.onScannedRobot(event);
        setState(learning.getStateRepresentation().represent(getState(), event));
    }

    public void setState(IState state) {
        this.lastState = this.state;
        this.state = state;
    }
}
