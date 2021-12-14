package robot;

import fa.IFunctionApproximation;
import fa.NN;
import policy.EnergyReward;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;

public class NNTNinetyRobotConfident extends QLearningRobot {
    public NNTNinetyRobotConfident() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new TNinetyActionRepresentation();
        IStateRepresentation stateRepresentation = new StateRep();
        IFunctionApproximation functionApproximation = new NN("NNTNinetyRobot.obj", true);
        IPolicy policy = new EnergyReward();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.05, 0.1, 0.9, 3, false);
    }

}
