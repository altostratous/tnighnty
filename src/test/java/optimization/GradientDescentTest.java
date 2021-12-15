package optimization;

import autograd.UniformInitializer;
import dataset.*;
import jdk.jshell.spi.ExecutionControl;
import nn.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class GradientDescentTest {

    private final static int trials = 300;

    @Ignore
    @Test
    public void TestSimpleGD() throws ExecutionControl.NotImplementedException {
        var model = Factory.createNeuralNetwork(
                new int[]{2, 4, 1},
                new Sigmoid(),
                new UniformInitializer(-0.5, 0.5)
        );
        var dataSet = new XORBinaryDataSet();
        var optimizer = new GradientDescent(0.2, 0.);
        var loss = new MeanSquaredError(model.getOutput());
        double finalLoss = model.fit(dataSet, optimizer, loss, 40000, 0.05);
        Assert.assertTrue("Big loss " + finalLoss, finalLoss < 0.05);
    }

    @Ignore("Skipping slow convergence tests.")
    @Test
    public void TestConvergence() throws ExecutionControl.NotImplementedException, IOException {
        int diverged = 0;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < GradientDescentTest.trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{2, 4, 1},
                    new Sigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new XORBinaryDataSet();
            var optimizer = new GradientDescent(0.2, 0.);
            var loss = new MeanSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 40000, 0.05, collector);
            stats.add(collector);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
        }
        outputGraphData("a", stats);
        Assert.assertTrue("Convergence with high probability busted!", diverged < 6);
    }

    private void outputGraphData(String assignmentPart, ArrayList<ConvergenceCollector> stats) throws IOException {
        FileWriter of = new FileWriter("doc/" + assignmentPart + "_avg.tex");
        double average = stats.stream().mapToInt(ConvergenceCollector::getEpochs).average().getAsDouble();
        of.write(String.valueOf(average));
        of.close();

        Optional<ConvergenceCollector> representative = stats.stream().min(Comparator.comparingDouble(c -> Math.abs(c.getEpochs() - average)));
        of = new FileWriter("doc/" + assignmentPart + ".tex");
        of.write(representative.get().toString());
        of.close();
    }

    @Ignore("Skipping slow convergence tests.")
    @Test
    public void TestBipolarGD() throws ExecutionControl.NotImplementedException, IOException {
        int diverged = 0;
        int trials = GradientDescentTest.trials;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{2, 4, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new BinaryToBipolarWrapper(new XORBinaryDataSet());
            var optimizer = new GradientDescent(0.2, 0.);
            var loss = new MeanSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 3500, 0.05, collector);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
            stats.add(collector);
        }
        outputGraphData("b", stats);
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }

    @Ignore
    @Test
    public void TestBipolarMomentumGD() throws ExecutionControl.NotImplementedException, IOException {

        int diverged = 0;
        int trials = GradientDescentTest.trials;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{2, 4, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new BinaryToBipolarWrapper(new XORBinaryDataSet());
            var optimizer = new GradientDescent(0.2, 0.9);
            var loss = new MeanSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 1000, 0.05, collector);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
            stats.add(collector);
        }
        outputGraphData("c", stats);
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }

    @Ignore("ReLU not working on XOR or buggy")
    @Test
    public void TestBipolarMomentumGDReLU() throws ExecutionControl.NotImplementedException, IOException {

        int diverged = 0;
        int trials = GradientDescentTest.trials;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{2, 4, 1},
                    new ReLU(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new BinaryToBipolarWrapper(new XORBinaryDataSet());
            var optimizer = new GradientDescent(0.01, 0.9);
            var loss = new MeanSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 1000, 0.05, collector);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
            stats.add(collector);
        }
        outputGraphData("c", stats);
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }


    @Test
    public void TestLookupNeuralNet() throws ExecutionControl.NotImplementedException, IOException {
        var model = Factory.createNeuralNetwork(
                new int[]{12, 1},
                new BipolarSigmoid(),
                new UniformInitializer(-0.5, 0.5),
                false
        );
        double[] input = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        double desired = 2.1;
        DataPointDataSet dataPointDataSet = new DataPointDataSet(new DataPoint(input, new double[]{desired}));
        double toleratedError = 0.01;
        double lossLimit = Math.pow(toleratedError, 2) / 2;
        model.fit(dataPointDataSet, new GradientDescent(0.5, 0.), new MeanSquaredError(model.getOutput()), 1000, lossLimit);
        Assert.assertEquals(model.evaluate(input)[0], desired, toleratedError);
    }
}
