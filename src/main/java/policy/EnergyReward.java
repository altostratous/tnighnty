package policy;

import representation.IState;
import representation.States;

public class EnergyReward implements IPolicy {
    @Override
    public double getReward(IState run, IState last) {
        // should we give rewards on one state or on the change of last two states?
        States states = (States) run;
        States lastStates = (States) last;
        return - states.getEnemyEnergy();
    }
}
