package rl;

import representation.IAction;
import representation.IRepresentable;
import representation.IState;

public interface ILearning {
    IAction takeStep(IRepresentable lastStateAction, IState currentState);
}
