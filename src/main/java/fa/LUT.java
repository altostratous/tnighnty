package fa;

import representation.IRepresentable;

import java.io.*;
import java.util.HashMap;

public class LUT implements IFunctionApproximation {

    private final String filePath;
    int distance_level = 3;
    int robot_energy_level = 3;
    int enemy_energy_level = 3;
    int position_level = 48;
    HashMap StateMap = new HashMap(distance_level * robot_energy_level *
            enemy_energy_level * position_level);
    boolean readOnly;


    public LUT(String filePath, boolean readOnly) {
        this.filePath = filePath;
        this.readOnly = readOnly;
    }

    public void save(File argFile) {

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
        if (readOnly) {return;}
        double[] repr = input.toVector();
        System.out.print("train " + input + " to " + output[0]);
        System.out.println();
        this.StateMap.put(input, output);
    }

    @Override
    public double[] eval(IRepresentable input) {
        return (double[]) StateMap.getOrDefault(input, new double[]{ 0 });
    }

    @Override
    public void save() throws IOException {
        if (readOnly) return;
        new ObjectOutputStream(new FileOutputStream(this.filePath)).writeObject(StateMap);
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        this.StateMap = (HashMap) new ObjectInputStream(new FileInputStream(this.filePath)).readObject();
    }


}
