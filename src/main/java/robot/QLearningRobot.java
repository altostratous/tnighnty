package robot;

import representation.IAction;
import representation.IState;
import rl.ILearning;
import robocode.Event;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

public class QLearningRobot extends Robot {
    private ILearning learning;
    private IState state;
    private IState lastState;
    private IAction lastAction;

    @Override
    public void run() {
        super.run();
        ahead(5);
    }

    public QLearningRobot(ILearning learning) {
        this.learning = learning;
    }

    public ILearning getLearning() {
        return learning;
    }

    @Override
    public void onStatus(StatusEvent e) {
        super.onStatus(e);
        processEvent(e);
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
        processEvent(event);
    }

    private void processEvent(Event event) {
        setState(learning.getStateRepresentation().represent(getState(), event));
        IAction action = learning.takeStep(getLastState(), getLastAction(), getState());
        takeAction(action);
    }

    private IAction getLastAction() {
        return lastAction;
    }

    private void takeAction(IAction action) {
        learning.getActionRepresentation().takeAction(this, action);
        lastAction = action;
    }

    public void setState(IState state) {
        this.lastState = this.state;
        this.state = state;
    }
}
