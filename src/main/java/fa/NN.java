package fa;

import autograd.IInitializer;
import autograd.UniformInitializer;
import dataset.DataPoint;
import dataset.IDataSet;
import dataset.LookupTableDataSet;
import dataset.RobotDataSet;
import nn.*;
import optimization.GradientDescent;
import optimization.ILoss;
import optimization.IOptimizer;
import representation.IRepresentable;

import java.io.*;

// TODO Christina and Husna
public class NN implements IFunctionApproximation {
    Model model = Factory.createNeuralNetwork(
            new int[]{2, 4, 1},
            new BipolarSigmoid(),
            new UniformInitializer(-0.5, 0.5));
    IOptimizer optimizer = new GradientDescent(0.2, 0.9);;
    ILayer activation;
    IInitializer initializer;
    IDataSet dataSet = new RobotDataSet();
    ILoss loss = new MinimumSquaredError(model.getOutput());
    int epochs = 1000;
    double lossLimit = 0.05;
    IFitCallback collector = new ConvergenceCollector();

    private final String filePath;
    boolean readOnly;

    public NN(String filePath, boolean readOnly) {
        // construct the neural network
        this.filePath = filePath;
        this.readOnly = readOnly;
    }

    @Override
    public void train(IRepresentable input, double[] output) {
        if (readOnly) {return;}
        System.out.print("train " + input + " to " + output[0]);
        System.out.println();
        // construct a single datapoint dataset out of the data point
        dataSet.addPattern(input, output);
        // call fit on the neural network
        model.fit(dataSet, optimizer, loss, epochs, lossLimit, collector);
    }

    @Override
    public double[] eval(IRepresentable input) {
        // feed the input to the neural network and return the outcome as the q value
        double[] input_vector = input.toVector();
        return model.evaluate(input_vector);
    }

    @Override
    public void save() throws IOException {
        // save all the parameters of the neural network, the weights
        if (readOnly) return;
        new ObjectOutputStream(new FileOutputStream(this.filePath)).writeObject(model);
        // save the entire model, or just the weights?
        // need to record the different versions of weight along the way?
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        // load the weights of the neural network from the filePath
        model = (Model) new ObjectInputStream(new FileInputStream(this.filePath)).readObject();

    }
}
