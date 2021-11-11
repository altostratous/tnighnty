package robot;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.GetAway;
import policy.IPolicy;
import representation.CoordinatesRepresentation;
import representation.IActionRepresentation;
import representation.IStateRepresentation;
import representation.MoveRepresentation;
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
        IPolicy policy = new GetAway();
        return new QLearning(stateRepresentation, actionRepresentation, policy, functionApproximation);
    }
}
