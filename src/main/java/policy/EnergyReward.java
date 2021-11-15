package policy;

import representation.IState;
import representation.States;

public class EnergyReward implements IPolicy {
    @Override
    public double getReward(IState run) {
        // should we give rewards on one state or on the change of last two states?
        States states = (States) run;
        if (states.getMyEnergy() == States.energy.LOW) {
            return 0;
        }
        else if (states.getMyEnergy() == States.energy.MEDIUM) {
            return 1;
        }
        else if (states.getMyEnergy() == States.energy.HIGH) {
            return 2;
        }
        else
            return -1;
    }
}
