package rl;

import policy.IPolicy;
import representation.*;

public interface ILearning {
    IAction takeStep(IState lastStateAction, IState currentState);

    IStateRepresentation getStateRepresentation();
    IActionRepresentation getActionRepresentation();

    IPolicy getPolicy();
}
