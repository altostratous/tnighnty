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
            new int[]{12, 12, 1},
            new BipolarSigmoid(),
            new UniformInitializer(-1, 1),
//            new UniformInitializer(0, 0),
            true,
            false
    );
    IOptimizer optimizer = new GradientDescent(0.01, 0.9);
    ILayer activation;
    IInitializer initializer;
    RobotDataSet dataSet = new RobotDataSet(10);
    ILoss loss = new MeanSquaredError(model.getOutput());
    int epochs = 1;
    double lossLimit = 0.000001;
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
        dataSet.addPattern(toInternalRepresentation(input.toVector()), output);
        System.out.println("SIZE: " + dataSet.getSize());
        // call fit on the neural network
        try {
            dataSet.reset();
            DataPoint next = dataSet.next();
            System.out.println("Desired: " + next.getY()[0]);
            System.out.println("Before fit: " + model.evaluate(next.getX())[0]);
            var totalLoss = model.fit(dataSet, optimizer, loss, epochs, lossLimit);
            System.out.println("After fit: " + model.evaluate(next.getX())[0]);
            System.out.println("LOSS: " + Math.sqrt(totalLoss / dataSet.getSize()));
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        System.out.println("So many parameters " + model.getTrainableParameters().length);
        for (var p :
                model.getTrainableParameters()) {
            System.out.print(p.getValue() + " ");
        }
        System.out.println();
    }

    @Override
    public double[] eval(IRepresentable input) {
        // feed the input to the neural network and return the outcome as the q value
        double[] input_vector = input.toVector();
        return model.evaluate(toInternalRepresentation(input_vector));
    }

    private double[] toInternalRepresentation(double[] rawPattern) {
        return rawPattern;
//        double[] sixtyFourPattern = new double[64];
//        int sixtyFourIndex = 0;
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 4; j++) {
//                sixtyFourIndex += Math.pow(4, i) * j * rawPattern[i * 4 + j];
//            }
//        }
//        for (int i = 0; i < 64; i++) {
//            if (sixtyFourIndex == i) {
//                sixtyFourPattern[i] = 1;
//            } else {
//                sixtyFourPattern[i] = 0;
//            }
//        }
//        return sixtyFourPattern;
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

//        ObjectInputStream modelStream = new ObjectInputStream(new FileInputStream(this.getClass().getClassLoader().getResource("TrainedNN.obj").getPath()));
//        Parameter[] parameters = (Parameter[])(modelStream.readObject());
//        Parameter[] trainables = model.getTrainableParameters();
//        for (int i = 0; i < parameters.length; i++) {
//            trainables[i].setValue(parameters[i].getValue());
//        }
//        modelStream.close();

        dataSet = new RobotDataSet(1);
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
