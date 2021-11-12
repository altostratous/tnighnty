package rl;

import fa.IFunctionApproximation;
import org.jetbrains.annotations.NotNull;
import policy.IPolicy;
import representation.*;

import java.util.Random;

public class QLearning implements ILearning {

    private double epsilon;
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
        System.out.println("Current state " + currentState);
        IAction bestAction = exploit(currentState);
        IRepresentable oldSA = stateActionRepresentation.represent(
                lastState,
                lastAction
        );
        IRepresentable currentBest = stateActionRepresentation.represent(
                currentState,
                bestAction
        );
        double oldQ = functionApproximation.eval(oldSA)[0];
        double r = policy.getReward(lastState);
        double qMax = functionApproximation.eval(currentBest)[0];
        System.out.println("train " + oldQ + " = " + oldQ + " + " + alpha + " (" + r + " + " + gamma + " * " + qMax  + " - " + oldQ + ")");
        System.out.println("train " + oldSA + " " + bestAction);
        functionApproximation.train(
                oldSA,
                new double[]{
                        oldQ + alpha * (r + gamma * qMax - oldQ)
                }
        );
        if (this.random.nextDouble() < this.epsilon) {
            IAction action = explore();
            logAction(currentState, action, "explored");
            return action;
        } else {
            logAction(currentState, bestAction, "exploited");
            return bestAction;
        }
    }

    private IAction exploit(IState currentState) {
        double bestQ = 0;
        IAction bestAction = actionRepresentation.getActions()[0];
        for (IAction action: actionRepresentation.getActions()) {
            double q = functionApproximation.eval(
                    stateActionRepresentation.represent(
                            currentState, action
                    ))[0];
            System.out.print("    " + q + " " + action);
            System.out.println();
            if (q > bestQ) {
                bestAction = action;
                bestQ = q;
            }
        }
        return bestAction;
    }

    private void logAction(IState currentState, IAction bestAction, String hint) {
        if (bestAction == null) return;
        System.out.print(bestAction);
        System.out.print(" " + hint + " ");
        System.out.println(functionApproximation.eval(
                stateActionRepresentation.represent(
                        currentState, bestAction
                ))[0]);
    }

    private IAction explore() {
        IAction[] actions = actionRepresentation.getActions();
        IAction action = actions[getRandom().nextInt(actions.length)];
        return action;
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

    @Override
    public IFunctionApproximation getFunctionApproximation() {
        return functionApproximation;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public double getEpsilon() {
        return this.epsilon;
    }
}
