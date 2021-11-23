package policy;

import representation.IState;
import representation.Memento;
import representation.States;

public class EnergyReward implements IPolicy {
    @Override
    public double getReward(IState run, IState last) {
        // should we give rewards on one state or on the change of last two states?
        States states = (States)((Memento) run).getS2();
        States lastStates = (States)((Memento) last).getS2();
//        return states.getMyEnergy() - states.getEnemyEnergy();

        return lastStates.getEnemyEnergy() - states.getEnemyEnergy();
    }
}
