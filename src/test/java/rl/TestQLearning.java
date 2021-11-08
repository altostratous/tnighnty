package rl;

import representation.*;
import fa.IFunctionApproximation;
import fa.LUT;
import org.junit.Assert;
import org.junit.Test;
import policy.GetAway;
import policy.IPolicy;
import robocode.Robot;
import robot.FunctionApproximationRobot;
import robot.TopLeftCornerRobot;
import utils.AutoRoboCode;
import utils.FinalStatesCollector;

import java.util.ArrayList;

public class TestQLearning {

    @Test
    public void TestTrivialLUTRobot() {
        IActionRepresentation actionRepresentation = new MoveRepresentation();
        IStateRepresentation stateRepresentation = new CoordinatesRepresentation();
        ActionStateRepresentation representation = new ActionStateRepresentation(stateRepresentation, actionRepresentation);
        ILearning learning = new QLearning();
        IFunctionApproximation functionApproximation = new LUT(representation, learning);
        IPolicy policy = new GetAway();
        Robot opponent = new TopLeftCornerRobot();
        Robot robot = new FunctionApproximationRobot(functionApproximation, policy);
        FinalStatesCollector collector = new FinalStatesCollector();
        for (int i = 0; i < 100; i++) {
            AutoRoboCode.battle(opponent, robot, collector);
        }
        ArrayList<IState> states = collector.getFinalStates();
        Assert.assertTrue("Seemingly battle didn't happen. No state is collected.", states.size() > 1);
        IState lastRun = states.get(states.size() - 1);
        IState firstRun = states.get(0);
        Assert.assertTrue(
                "Learning wasn't effective",
                policy.getReward(firstRun) < policy.getReward(lastRun));
    }
}
