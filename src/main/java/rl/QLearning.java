package rl;

import fa.IFunctionApproximation;
import policy.IPolicy;
import representation.*;

import java.util.Random;

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

        return explore();
    }

    private IAction explore() {
        IAction[] actions = actionRepresentation.getActions();
        return actions[new Random().nextInt(actions.length)];
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
