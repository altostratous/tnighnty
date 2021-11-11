package representation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {

    // The states

    // the relative distance to the enemy (<200, <300, >=300)
    // our energy (<30, >=30, >100)
    // the enemy's energy (<30, >=30, >100)
    // x, y position of our own (step by 100)

    // The actions

    // fire(1)
    // turn right (90)
    // turn left (90)
    // go ahead (100)
    // go back (100)
    // do nothing

    private int distance;
    private int energy;
    private int enemyEnergy;
    private int x;
    private int y;
    private int enemyBearing;

    List<Action> actions = new ArrayList<Action>();

    public void add(Action a) {
        actions.add(a);
    }

    public void addAll() {
        actions.add(new Action("fire"));
        actions.add(new Action("right"));
        actions.add(new Action("left"));
        actions.add(new Action("ahead"));
        actions.add(new Action("back"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return distance == state.distance && energy == state.energy && enemyEnergy == state.enemyEnergy && x == state.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, energy, enemyEnergy, x);
    }




}
