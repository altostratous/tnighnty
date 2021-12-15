package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import fa.NN;
import fa.NNLUT;
import policy.EnergyReward;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;
import robocode.Robot;

public class NNTNinetyRobot extends QLearningRobot {
    public NNTNinetyRobot() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new TNinetyActionRepresentation();
        IStateRepresentation stateRepresentation = new StateRep();
        IFunctionApproximation functionApproximation = new NN("NNTNinetyRobot.obj", false);
//        IFunctionApproximation functionApproximation = new NNLUT();
//        IFunctionApproximation functionApproximation = new LUT("NNTNinetyRobot.obj", false);
        IPolicy policy = new EnergyReward();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.8, 0.1, 0.9, 3, false);
    }

}
