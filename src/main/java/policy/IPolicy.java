package policy;

import state.Coordinates;
import state.IState;

public interface IPolicy {
    double getReward(IState run);
}
