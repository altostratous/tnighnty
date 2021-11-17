package robot;

import org.junit.Test;
import representation.IState;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import java.io.File;
import java.util.ArrayList;

public class TestTNinetyRobot {

    @Test
    public void TestTrivialLUTRobot() {
        new File("LUTTNinetyRobot.obj").deleteOnExit();
        ArrayList<IState> states = new ArrayList<>();
        LUTTNinetyRobot robot = new LUTTNinetyRobot();
        System.setProperty("NOSECURITY", "true");
        RobocodeEngine.setLogMessagesEnabled(false);
        RobocodeEngine engine = new RobocodeEngine(new File(System.getProperty("user.home") + "/robocode/"));
        engine.setVisible(true);
        int numberOfRounds = 100;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
        RobotSpecification[] selectedRobots = engine.getLocalRepository(robot.getClass().getCanonicalName() + "*,sample.SittingDuck");
        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        engine.close();
    }
}
