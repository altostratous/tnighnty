package rl;

import fa.IFunctionApproximation;
import org.jetbrains.annotations.NotNull;
import policy.IPolicy;
import representation.*;

import java.util.ArrayList;
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
    private int depth;
    private boolean onlineLearning = false;
    private ArrayList<IRepresentable> history = new ArrayList<>();
    private ConcatenationRepresentation concatenation = new ConcatenationRepresentation();

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
        this.depth = 1;
        this.onlineLearning = false;
    }


    public QLearning(IStateRepresentation stateRepresentation,
                     IActionRepresentation actionRepresentation,
                     IStateActionRepresentation stateActionRepresentation,
                     IPolicy policy, IFunctionApproximation functionApproximation,
                     double epsilon, double alpha, double gamma, int depth, boolean onlineLearning) {        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
        this.stateRepresentation = stateRepresentation;
        this.actionRepresentation = actionRepresentation;
        this.policy = policy;
        this.functionApproximation = functionApproximation;
        this.stateActionRepresentation = stateActionRepresentation;
        this.depth = depth;
        this.random = new Random();
        this.onlineLearning = onlineLearning;
    }

    @Override
    public IAction takeStep(IState lastState, IAction lastAction, IState currentState) {
        if (lastState == null || lastAction == null || currentState == null) {
            return explore();
        }
        System.out.println("Current state " + currentState);
        IRepresentable oldSA = stateActionRepresentation.represent(
                lastState,
                lastAction
        );
        history.add(oldSA);
        while (history.size() > depth) {
            history.remove(0);
        }
        if (history.size() < depth) return explore();
        IAction bestAction = exploit(currentState);
        IAction backupAction;
        IAction toTakeAction;
        double randomDouble = this.random.nextDouble();
        boolean explored = false;
        if (randomDouble < this.epsilon) {
            IAction action = explore();
            backupAction = action;
            toTakeAction = action;
            explored = true;
        } else {
            backupAction = bestAction;
            toTakeAction = bestAction;
        }
        if (!onlineLearning) {
            backupAction = bestAction;
        }

        for (int i = 0; i < history.size() - 1; i++) {
            oldSA = new Concatenation(oldSA, history.get(i));
        }
        IRepresentable currentAction = stateActionRepresentation.represent(currentState, backupAction);
        for (int i = 1; i < history.size(); i++) {
            currentAction = new Concatenation(currentAction, history.get(i));
        }

        if (explored) {
            logAction(currentAction, "explored");
        } else {
            logAction(currentAction, "exploited");
        }

        double oldQ = functionApproximation.eval(oldSA)[0];
        double r = policy.getReward(currentState, lastState); // why would evaluate Rewards for last state?


        double currentQ = functionApproximation.eval(currentAction)[0];
        System.out.println("train " + oldQ + " = " + oldQ + " + " + alpha + " (" + r + " + " + gamma + " * " + currentQ  + " - " + oldQ + ")");
        System.out.println("train " + oldSA + " " + backupAction);
        functionApproximation.train(
                oldSA,
                new double[]{
                        oldQ + alpha * (r + gamma * currentQ - oldQ)
                }
        );
        return toTakeAction;
    }

    private IAction exploit(IState currentState) {
        IAction bestAction = actionRepresentation.getActions()[0];
        IRepresentable stateAction = stateActionRepresentation.represent(currentState, bestAction);
        for (int i = 1; i < history.size(); i++) {
            stateAction = new Concatenation(stateAction, history.get(i));
        }
        double bestQ = functionApproximation.eval(stateAction)[0];

        for (IAction action: actionRepresentation.getActions()) {
            stateAction = stateActionRepresentation.represent(
                    currentState, action
            );
            for (int i = 1; i < history.size(); i++) {
                stateAction = new Concatenation(stateAction, history.get(i));
            }
            double q = functionApproximation.eval(
                    stateAction)[0];
            System.out.print("    " + q + " " + action);
            System.out.println();
            if (q > bestQ) {
                bestAction = action;
                bestQ = q;
            }
        }
        return bestAction;
    }

    private void logAction(IRepresentable bestAction, String hint) {
        if (bestAction == null) return;
        System.out.print(bestAction);
        System.out.print(" " + hint + " ");
        System.out.println(functionApproximation.eval(bestAction)[0]);
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
