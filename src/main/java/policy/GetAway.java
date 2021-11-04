package policy;

import state.IState;

public class GetAway implements IPolicy {
    @Override
    public double getReward(IState run) {
        return 0;
    }
}
