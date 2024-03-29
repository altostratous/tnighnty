package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.EnergyReward;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;
import robocode.ScannedRobotEvent;

public class LUTTNinetyRobotOnline extends QLearningRobot {
    public LUTTNinetyRobotOnline() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new TNinetyActionRepresentation();
        IStateRepresentation stateRepresentation = new StateRep();
        IFunctionApproximation functionApproximation = new LUT("LUTTNinetyRobot.obj", false);
        IPolicy policy = new EnergyReward();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.8, 0.1, 0.9, 3, true);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        super.onScannedRobot(event);
        System.out.println("HELLLLLOOOOOOO");
    }
}
