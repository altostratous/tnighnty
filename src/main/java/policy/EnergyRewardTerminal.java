package policy;

import representation.IState;
import representation.States;

public class EnergyRewardTerminal implements IPolicy {
    @Override
    public double getReward(IState run, IState last) {
        // should we give rewards on one state or on the change of last two states?
        States states = (States) run;
        States lastStates = (States) last;
//        return states.getMyEnergy() - states.getEnemyEnergy();

        if (states.getEnemyEnergy() == 0)
            return 1;
        if (states.getMyEnergy() == 0)
            return -1;
        return 0;
    }
}
