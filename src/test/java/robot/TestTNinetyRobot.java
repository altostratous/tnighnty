package robot;

import org.junit.Test;
import representation.IState;
import robocode.Robot;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleFinishedEvent;

import java.io.File;
import java.util.ArrayList;

public class TestTNinetyRobot {

    @Test
    public void TestTrivialLUTRobot() {
//        new File("LUTTNinetyRobot.obj").deleteOnExit();
        ArrayList<IState> states = new ArrayList<>();
        LUTTNinetyRobot trainRobot = new LUTTNinetyRobot();
        LUTTNinetyRobotConfident testRobot = new LUTTNinetyRobotConfident();
        Corners opponent = new Corners();
        System.setProperty("NOSECURITY", "true");
        RobocodeEngine.setLogMessagesEnabled(false);
        RobocodeEngine engine = new RobocodeEngine(new File(System.getProperty("user.home") + "/robocode/"));
        engine.addBattleListener(new BattleAdaptor() {
            @Override
            public void onBattleCompleted(BattleCompletedEvent event) {
                super.onBattleCompleted(event);
                for (var result :
                        event.getSortedResults()) {
                    System.out.println(result.getFirsts());
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            int numberOfRounds = 200;
            BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
            Robot robot;
            if (i % 2 == 0) {
                engine.setVisible(false);
                robot = trainRobot;
            } else {
                numberOfRounds = 100;
                engine.setVisible(false);
                robot = testRobot;
            }
            RobotSpecification[] selectedRobots = engine.getLocalRepository(robot.getClass().getCanonicalName() + "*," + opponent.getClass().getCanonicalName() + "*");
            BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
            engine.runBattle(battleSpec, true); // waits till the battle finishes
        }
        engine.close();
    }
}
