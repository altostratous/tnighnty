package rl;

import fa.IFunctionApproximation;
import policy.IPolicy;
import representation.IAction;
import representation.IRepresentable;
import representation.IState;
import representation.StateActionRepresentation;

public class QLearning implements ILearning {
    public QLearning(StateActionRepresentation representation, IPolicy policy, IFunctionApproximation functionApproximation) {

    }

    @Override
    public IAction takeStep(IRepresentable lastStateAction, IState currentState) {
        return null;
    }
}
