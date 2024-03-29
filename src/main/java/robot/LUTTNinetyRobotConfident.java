package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.EnergyReward;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;
import robocode.ScannedRobotEvent;

public class LUTTNinetyRobotConfident extends QLearningRobot {
    public LUTTNinetyRobotConfident() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new TNinetyActionRepresentation();
        IStateRepresentation stateRepresentation = new StateRep();
        IFunctionApproximation functionApproximation = new LUT("LUTTNinetyRobot.obj", true);
        IPolicy policy = new EnergyReward();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.05, 0.1, 0.8, 3, false);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        super.onScannedRobot(event);
        System.out.println("HELLLLLOOOOOOO");
    }
}
