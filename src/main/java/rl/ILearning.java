package rl;

import policy.IPolicy;
import representation.*;

public interface ILearning {
    IAction takeStep(IState lastState, IAction lastAction, IState currentState);

    IStateRepresentation getStateRepresentation();
    IActionRepresentation getActionRepresentation();

    IPolicy getPolicy();
}
