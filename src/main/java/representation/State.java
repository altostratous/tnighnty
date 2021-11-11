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
    private int robot_energy;
    private int enemy_energy;
    private int position;

    public State(){
        //?
    }


    List<Action> actions = new ArrayList<Action>();

    public State(int distance, int robot_energy, int enemy_energy, int position) {

        this.distance = distance;
        this.robot_energy = robot_energy;
        this.enemy_energy = enemy_energy;
        this.position = position;
    }

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
        return distance == state.distance && robot_energy == state.robot_energy && enemy_energy == state.enemy_energy && position == state.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, robot_energy, enemy_energy, position);
    }




}
