package fa;

import autograd.IInitializer;
import dataset.DataPoint;
import dataset.IDataSet;
import dataset.LookupTableDataSet;
import dataset.RobotDataSet;
import nn.IFitCallback;
import nn.ILayer;
import nn.Model;
import optimization.ILoss;
import optimization.IOptimizer;
import representation.IRepresentable;

import java.io.IOException;

// TODO Christina and Husna
public class NN implements IFunctionApproximation {
    IOptimizer optimizer;
    ILayer activation;
    IInitializer initializer;
    IDataSet dataSet = new RobotDataSet();
    Model model;
    ILoss loss;
    int epochs;
    double lossLimit;
    IFitCallback callback;

    public NN(String filePath, boolean readOnly) {
        // construct the neural network

    }

    @Override
    public void train(IRepresentable input, double[] output) {
        // construct a single datapoint dataset out of the data point
        dataSet.addPattern(input, output);
        // call fit on the neural network
        model.fit(dataSet, optimizer, loss, epochs, lossLimit, callback);
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
