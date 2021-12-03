package robot;

import fa.LUT;
import org.junit.Test;
import representation.IState;
import robocode.Robot;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TestTNinetyRobot {

    @Test
    public void TestTrivialLUTRobot() {
//        testRobot(new LUTTNinetyRobot0(), 1, 100);
//        testRobot(new LUTTNinetyRobotOnline(), 1, 100);
//        testRobot(new LUTTNinetyRobotTerminal(), 500, 100);
//        testRobot(new LUTTNinetyRobot05(), 100, 100);
//        testRobot(new LUTTNinetyRobot(), 500, 2);
//        testRobot(new NNTNinetyRobot(), 100, 100);
    }

    private void testRobot(Robot trainRobot, int step, int epochs) {

        String outputFileName = "doc/" + trainRobot.getClass().getName() + ".tex";
//        new File(outputFileName).deleteOnExit();
        new File("LUTTNinetyRobot.obj").deleteOnExit();
        ArrayList<IState> states = new ArrayList<>();
        LUTTNinetyRobotConfident testRobot = new LUTTNinetyRobotConfident();
        Corners opponent = new Corners();
        System.setProperty("NOSECURITY", "true");
        RobocodeEngine.setLogMessagesEnabled(false);
        RobocodeEngine engine = new RobocodeEngine(new File(System.getProperty("user.home") + "/robocode/"));
        engine.addBattleListener(new BattleAdaptor() {
            @Override
            public void onBattleCompleted(BattleCompletedEvent event) {
                super.onBattleCompleted(event);
                boolean shouldPrint = false;
                try {
                    FileWriter of = new FileWriter(outputFileName, true);
                    for (var result :
                            event.getIndexedResults()) {
                        if (Objects.equals(result.getTeamLeaderName(), testRobot.getClass().getCanonicalName() + "*")) {
                            of.write(result.getFirsts() + " ");
                            System.out.println(result.getFirsts());
                            shouldPrint = true;
                        }
                    }
                    if (shouldPrint) {
                        of.write(((LUT) (new LUTTNinetyRobot().getLearning().getFunctionApproximation())).getSize() + "\n");
                    }
                    of.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i = 0; i < epochs; i++) {
            int numberOfRounds = step;
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
