package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.GoTopRight;
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
        IFunctionApproximation functionApproximation = new LUT("TrivialLURRobot.obj");
        IPolicy policy = new GoTopRight();
        return new QLearning(
                stateRepresentation,
                actionRepresentation,
                new ConcatenationRepresentation(),
                policy,
                functionApproximation,
                0.08, 0.1, 0.8);
    }
}
