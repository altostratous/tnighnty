package policy;

import representation.Coordinates;
import representation.IState;

public class GoRight implements IPolicy {
    @Override
    public double getReward(IState run) {
        Coordinates coordinates = (Coordinates) run;
        return coordinates.getX();
    }
}
