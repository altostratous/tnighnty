package policy;

import representation.IState;

public interface IPolicy {
    double getReward(IState currentState, IState lastState);
}
