package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.GoRight;
import policy.IPolicy;
import representation.*;
import rl.ILearning;
import rl.QLearning;

public class TrivialLUTRobot extends QLearningRobot {
    public TrivialLUTRobot() {
        super(createLearning());
    }


    public static ILearning createLearning() {
        IActionRepresentation actionRepresentation = new MoveRepresentation();
        IStateRepresentation stateRepresentation = new CoordinatesRepresentation();
        IFunctionApproximation functionApproximation = new LUT();
        IPolicy policy = new GoRight();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new Concatenation(),
                policy,
                functionApproximation,
                0.8, 0.2, 0.8);
    }
}
