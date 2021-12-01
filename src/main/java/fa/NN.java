package fa;

import dataset.DataPoint;
import representation.IRepresentable;

import java.io.IOException;

// TODO Christina and Huna
public class NN implements IFunctionApproximation {
    public NN(String filePath, boolean readOnly) {
        // construct the neural network
    }

    @Override
    public void train(IRepresentable input, double[] output) {
        DataPoint dataPoint = new DataPoint(input.toVector(), output);
        // construct a single datapoint dataset out of the data point
        // call fit on the neural network
    }

    @Override
    public double[] eval(IRepresentable input) {
        // feed the input to the neural network and return the outcome as the q value
        return new double[0];
    }

    @Override
    public void save() throws IOException {
        // save all the parameters of the neural network, the weights
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        // load the weights of the neural network from the filePath
    }
}
