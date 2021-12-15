package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import fa.NN;
import jdk.jshell.spi.ExecutionControl;
import org.junit.Ignore;
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

    @Ignore
    @Test
    public void TestTrivialLUTRobot() {
//        testRobot(new LUTTNinetyRobot0(), 1, 100);
//        testRobot(new LUTTNinetyRobotOnline(), 1, 100);
//        testRobot(new LUTTNinetyRobotTerminal(), 500, 100);
//        testRobot(new LUTTNinetyRobot05(), 100, 100);
//        testRobot(new LUTTNinetyRobot(), 500, 2);
//        NN functionApproximation = (NN) (new NNTNinetyRobot()).getLearning().getFunctionApproximation();
//        var dataSet = functionApproximation.getDataSet();
//        for (int i = 0; i < dataSet.getX().size(); i++) {
//            double[] x = dataSet.getX().get(i);
//            for (int j = 0; j < x.length; j++) {
//                System.out.print(x[j] + " ");
//            }
//            System.out.println(dataSet.getY().get(i)[0]);
//        }
        testRobot(new NNTNinetyRobot(), 100, 100);
    }

    private void testRobot(Robot trainRobot, int rounds, int battles) {

        String outputFileName = "doc/" + trainRobot.getClass().getName() + ".tex";
//        new File(outputFileName).deleteOnExit();
        new File("NNTNinetyRobot.obj").deleteOnExit();
        new File("NNTNinetyRobot.LUT").deleteOnExit();
        new File("NNTNinetyRobot.NN").deleteOnExit();
        ArrayList<IState> states = new ArrayList<>();
        NNTNinetyRobotConfident testRobot = new NNTNinetyRobotConfident();
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
                        NN functionApproximation = (NN) (new NNTNinetyRobot()).getLearning().getFunctionApproximation();
                        System.out.println(functionApproximation.getSize() + "\n");
                        System.out.println(functionApproximation.getLoss() + "\n");
                    }
                    of.close();
                } catch (IOException | ExecutionControl.NotImplementedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i = 0; i < battles; i++) {
            int numberOfRounds = rounds;
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
