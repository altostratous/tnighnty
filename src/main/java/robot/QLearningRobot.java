package robot;

import representation.IAction;
import representation.IState;
import rl.ILearning;
import rl.QLearning;
import robocode.*;
import robocode.Event;
import robocode.Robot;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

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
        try {
            this.learning.getFunctionApproximation().load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        IState newState = learning.getStateRepresentation().represent(getState(), event);
        setState(newState);
        IAction action = learning.takeStep(getLastState(), getLastAction(), getState());
        takeAction(action);
    }

    private IAction getLastAction() {
        return lastAction;
    }

    private void takeAction(IAction action) {
        learning.getActionRepresentation().takeAction(this, action);
        if (action != null) lastAction = action;
    }

    public void setState(IState state) {
        if (!state.equals(this.lastState)) {
            this.lastState = this.state;
            this.state = state;
        }
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        try {
            learning.getFunctionApproximation().save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
