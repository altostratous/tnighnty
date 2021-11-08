package policy;

import representation.IState;

public class GetAway implements IPolicy {
    @Override
    public double getReward(IState run) {
        return 0;
    }
}
