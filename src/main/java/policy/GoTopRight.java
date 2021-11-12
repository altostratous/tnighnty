package policy;

import representation.Coordinates;
import representation.IState;

public class GoTopRight implements IPolicy {
    @Override
    public double getReward(IState run) {
        Coordinates coordinates = (Coordinates) run;
        var x = coordinates.getX();
        var y = coordinates.getY();
        if (x == 7 && y == 5) {
            return 1;
        }
        return 0;
    }
}
