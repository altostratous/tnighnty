package rl;

import action.IAction;
import action.Move;
import fa.IFunctionApproximation;
import fa.LUT;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import policy.GetAway;
import policy.IPolicy;
import robocode.Robot;
import robot.FunctionApproximationRobot;
import robot.TopLeftCornerRobot;
import state.Coordinates;
import state.IState;
import utils.AutoRoboCode;
import utils.FinalStatesCollector;

import java.util.ArrayList;

public class TestQLearning {

    @Ignore("Skipping not implemented test.")
    @Test
    public void TestTrivialLUTRobot() {
        IAction action = new Move();
        IState state = new Coordinates();
        ILearning learning = new QLearning();
        IFunctionApproximation functionApproximation = new LUT(state, action, learning);
        IPolicy policy = new GetAway();
        Robot opponent = new TopLeftCornerRobot();
        Robot robot = new FunctionApproximationRobot(functionApproximation, policy);
        FinalStatesCollector collector = new FinalStatesCollector();
        for (int i = 0; i < 100; i++) {
            AutoRoboCode.battle(opponent, robot, collector);
        }
        ArrayList<IState> states = collector.getFinalStates();
        IState lastRun = states.get(states.size() - 1);
        IState firstRun = states.get(0);
        Assert.assertTrue(
                "Learning wasn't effective",
                policy.getReward(firstRun) < policy.getReward(lastRun));
    }
}
