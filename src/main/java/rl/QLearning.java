package rl;

import fa.IFunctionApproximation;
import org.jetbrains.annotations.NotNull;
import policy.IPolicy;
import representation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

public class QLearning implements ILearning {

    private final double epsilon;
    private final double alpha;
    private final double gamma;
    private final Random random;
    private IStateRepresentation stateRepresentation;
    private IActionRepresentation actionRepresentation;
    private IPolicy policy;
    private IFunctionApproximation functionApproximation;
    private IStateActionRepresentation stateActionRepresentation;

    public QLearning(IStateRepresentation stateRepresentation,
                     IActionRepresentation actionRepresentation,
                     IStateActionRepresentation stateActionRepresentation,
                     IPolicy policy, IFunctionApproximation functionApproximation, double epsilon, double alpha, double gamma) {
        this.stateActionRepresentation = stateActionRepresentation;
        this.stateRepresentation = stateRepresentation;
        this.actionRepresentation = actionRepresentation;
        this.policy = policy;
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
        this.random = new Random();
        this.functionApproximation = functionApproximation;
    }

    @Override
    public IAction takeStep(IState lastState, IAction lastAction, IState currentState) {
        if (lastState == null || lastAction == null || currentState == null) {
            return explore();
        }
        IAction lastBestAction = exploit(lastState);
        IRepresentable oldSA = stateActionRepresentation.represent(
                lastState,
                lastAction
        );
        double oldQ = functionApproximation.eval(oldSA)[0];
        double r = policy.getReward(lastState);
        double qMax = functionApproximation.eval(lastBestAction)[0];
        functionApproximation.train(
                oldSA,
                new double[] {
                        oldQ + alpha * (r + gamma * qMax - oldQ)
                }
        );
        if (this.random.nextDouble() < this.epsilon) {
            return explore();
        } else {
            return exploit(currentState);
        }
    }

    private IAction exploit(IState currentState) {
        Optional<IAction> bestAction = Arrays.stream(actionRepresentation.getActions()).max(
                Comparator.comparing(
                        (action) -> functionApproximation.eval(
                                stateActionRepresentation.represent(
                                        currentState, action
                                ))[0]));
        assert bestAction.isPresent();
        System.out.println(bestAction.get());
        System.out.println(functionApproximation.eval(
                stateActionRepresentation.represent(
                        currentState, bestAction.get()
                ))[0]);
        return bestAction.get();
    }

    private IAction explore() {
        IAction[] actions = actionRepresentation.getActions();
        return actions[getRandom().nextInt(actions.length)];
    }

    @NotNull
    private Random getRandom() {
        return this.random;
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
