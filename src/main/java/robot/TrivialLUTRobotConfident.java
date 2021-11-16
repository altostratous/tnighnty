package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.EnergyReward;
import policy.GoTopRight;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;

public class TrivialLUTRobotConfident extends QLearningRobot {
    public TrivialLUTRobotConfident() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new MoveRepresentation();
        IStateRepresentation stateRepresentation = new StateRep();
        IFunctionApproximation functionApproximation = new LUT("TrivialLUTRobot.obj", false);
        IPolicy policy = new EnergyReward();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.05, 0.1, 0.8);
    }
}
