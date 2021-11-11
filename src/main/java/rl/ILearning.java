package rl;

import fa.IFunctionApproximation;
import fa.LUT;
import policy.IPolicy;
import representation.*;

public interface ILearning {
    IAction takeStep(IState lastState, IAction lastAction, IState currentState);

    IStateRepresentation getStateRepresentation();
    IActionRepresentation getActionRepresentation();

    IPolicy getPolicy();

    IFunctionApproximation getFunctionApproximation();
}
