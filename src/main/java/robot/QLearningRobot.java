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
    private long lastTurn;

    @Override
    public void run() {
        super.run();
//        setAdjustGunForRobotTurn(true);
//        setAdjustRadarForGunTurn(true);
        while (true) {
            // Replace the next 4 lines with any behavior you would like
//            ahead(100);
//            turnGunRight(360);
//            back(100);
//            turnRadarLeft(360);
            turnGunRight(360);
        }
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
        if (event.getTime() > this.lastTurn) {
            this.lastTurn = this.getTime();
            IState newState = learning.getStateRepresentation().represent(getState(), event);
            //set current state
            setState(newState);
            IAction action = learning.takeStep(getLastState(), getLastAction(), getState());
            //take new action
            takeAction(action);
        }
    }

    private IAction getLastAction() {
        return lastAction;
    }

    private void takeAction(IAction action) {
        learning.getActionRepresentation().takeAction(this, action);
        if (action != null) lastAction = action;
    }

    public void setState(IState state) {
        this.lastState = this.state;
        this.state = state;
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
