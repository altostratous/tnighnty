package fa;

import representation.IRepresentable;
import representation.State;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LUT implements IFunctionApproximation {

    int distance_level = 3;
    int robot_energy_level = 3;
    int enemy_energy_level = 3;
    int position_level = 48;
    HashMap StateMap = new HashMap(distance_level * robot_energy_level *
            enemy_energy_level * position_level);




    public void save(File argFile) {

    }


    public double outputFor(double[] X) {
        return 0;
    }


    public void load(String argFileName) throws IOException {

    }

    //LEFT HERE: finish the two methods below for implementation tomorrow morning.
    // for each unique state vector, generate its key and match it with a state object that contains 5 actions

    public void initialiseLUT() {
        for (int distance = 0; distance < distance_level; distance++) {
            for (int robot_energy = 0; robot_energy < robot_energy_level; robot_energy++) {
                for (int enemy_energy = 0; enemy_energy < enemy_energy_level; enemy_energy++) {
                    for (int position = 0; position < position_level; position++) {
                        //double[] state_vector = {distance, robot_energy, enemy_energy, position};
                        //double key = indexFor(state_vector);
//                        State newState = new State(distance, robot_energy, enemy_energy, position);
                        // Q values are automatically set to 0 by default
//                        newState.addAll(); // add all actions for each state?

//                        StateMap.put(newState, newState);

                    }
                }
            }
        }
    }



    public double train(double[] X, double argValue) {




        return 0;
    }

    @Override
    public void train(IRepresentable input, double[] output) {

    }

    @Override
    public double[] eval(IRepresentable input) {
        return new double[]{};
    }
}
