package rl;

import fa.IFunctionApproximation;
import policy.IPolicy;
import representation.*;

public class QLearning implements ILearning {

    private IStateRepresentation stateRepresentation;
    private IActionRepresentation actionRepresentation;
    private IPolicy policy;
    private IFunctionApproximation functionApproximation;

    public QLearning(IStateRepresentation stateRepresentation, IActionRepresentation actionRepresentation, IPolicy policy, IFunctionApproximation functionApproximation) {
        this.stateRepresentation = stateRepresentation;
        this.actionRepresentation = actionRepresentation;
        this.policy = policy;

        this.functionApproximation = functionApproximation;
    }

    @Override
    public IAction takeStep(IState lastStateAction, IState currentState) {
        return null;
    }

    @Override
    public IStateRepresentation getStateRepresentation() {
        return stateRepresentation;
    }

    @Override
    public IActionRepresentation getActionRepresentation() {
        return actionRepresentation;
    }

    @Override
    public IPolicy getPolicy() {
        return policy;
    }
}
