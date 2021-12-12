package fa;

import autograd.IInitializer;
import autograd.Parameter;
import autograd.UniformInitializer;
import dataset.DataPoint;
import dataset.IDataSet;
import dataset.RobotDataSet;
import jdk.jshell.spi.ExecutionControl;
import nn.*;
import optimization.GradientDescent;
import optimization.ILoss;
import optimization.IOptimizer;
import representation.IRepresentable;

import java.io.*;

// TODO Christina and Husna
public class NN implements IFunctionApproximation {
    Model model = Factory.createNeuralNetwork(
            new int[]{15, 10, 1},
            new BipolarSigmoid(),
            new UniformInitializer(-0.05, 0.05),
            false
    );
    IOptimizer optimizer = new GradientDescent(1e-4, 0.9);
    ILayer activation;
    IInitializer initializer;
    RobotDataSet dataSet = new RobotDataSet(20);
    ILoss loss = new MeanSquaredError(model.getOutput());
    int epochs = 1;
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
        System.out.println("SIZE: " + dataSet.getSize());
        // call fit on the neural network
        try {
            var totalLoss = model.fit(dataSet, optimizer, loss, epochs, lossLimit);
            System.out.println("LOSS: " + Math.sqrt(totalLoss / dataSet.getSize()));
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
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
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.filePath));
        stream.writeObject(dataSet);
        for (Parameter param :
                model.getTrainableParameters()) {
            stream.writeObject(param);
        }
        // save the entire model, or just the weights?
        // need to record the different versions of weight along the way?
        stream.close();
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        // load the weights of the neural network from the filePath
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(this.filePath));
        dataSet = (RobotDataSet) stream.readObject();
        for (var param: model.getTrainableParameters()) {
            param.setValue(((Parameter) stream.readObject()).getValue());
        }
        stream.close();
    }

    @Override
    public int getSize() {
        return dataSet.getSize();
    }

    public double getLoss() throws ExecutionControl.NotImplementedException {
        IDataSet wrappedDataset = new IDataSet() {
            int index = 0;

            @Override
            public DataPoint next() {
                if (NN.this.dataSet.getX().size() <= index) {
                    return null;
                }
                DataPoint dataPoint = new DataPoint(NN.this.dataSet.getX().get(index), NN.this.dataSet.getY().get(index));
                index++;
                return dataPoint;
            }

            @Override
            public void reset() {
                index = 0;
            }

            @Override
            public DataPoint onlyReadNext() {
                return null;
            }
        };
        return model.fit(dataSet, new GradientDescent(0, 0), loss, 1, 0) / this.dataSet.getSize();
    }

    public RobotDataSet getDataSet() {
        return dataSet;
    }
}
