package policy;

import representation.IState;

public class EnergyReward implements IPolicy{
    @Override
    public double getReward(IState run) {
        return 0;
    }
}
