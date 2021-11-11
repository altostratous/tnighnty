package rl;

import policy.IPolicy;
import representation.*;
import org.junit.Assert;
import org.junit.Test;
import robocode.Robot;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robot.TopLeftCornerRobot;
import robot.TrivialLUTRobot;

import java.util.ArrayList;

public class TestQLearning {

    @Test
    public void TestTrivialLUTRobot() {
        Robot opponent = new TopLeftCornerRobot();
        ArrayList<IState> states = new ArrayList<>();
        TrivialLUTRobot robot = new TrivialLUTRobot();
        System.setProperty("NOSECURITY", "true");
        RobocodeEngine.setLogMessagesEnabled(false);
        RobocodeEngine engine = new RobocodeEngine(new java.io.File(System.getProperty("user.home") + "/robocode/"));
        engine.addBattleListener(new BattleAdaptor() {
            @Override
            public void onTurnEnded(TurnEndedEvent event) {
                IRobotSnapshot robotSnapshot = event.getTurnSnapshot().getRobots()[0];
//                states.add(new Coordinates(
//                        (int) (robotSnapshot.getX() / 100),
//                        (int) (robotSnapshot.getY() / 100), 0));
                System.out.println(robotSnapshot.getX());
            }
        });
        engine.setVisible(true);
        int numberOfRounds = 1000;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
        RobotSpecification[] selectedRobots = engine.getLocalRepository(robot.getClass().getCanonicalName() + "*," + opponent.getClass().getCanonicalName() + "*");
        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        engine.close();

        Assert.assertTrue("Seemingly battle didn't happen. No state is collected.", states.size() > 1);
        IState lastRun = states.get(states.size() - 1);
        IState firstRun = states.get(0);
        IPolicy policy = robot.getLearning().getPolicy();
        double initialReward = policy.getReward(firstRun);
        double finalReward = policy.getReward(lastRun);
        Assert.assertTrue(
                String.format("Learning wasn't effective, initial reward %f, final reward %f", initialReward, finalReward),
                initialReward < finalReward);
    }
}
