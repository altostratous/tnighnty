package optimization;

import autograd.UniformInitializer;
import dataset.BinaryToBipolarWrapper;
import dataset.XORBinaryDataSet;
import jdk.jshell.spi.ExecutionControl;
import nn.BipolarSigmoid;
import nn.Factory;
import nn.MinimumSquaredError;
import nn.Sigmoid;
import org.junit.Assert;
import org.junit.Test;

public class GradientDescentTest {
    @Test
    public void TestSimpleGD() throws ExecutionControl.NotImplementedException {
        var model = Factory.createNeuralNetwork(
                new int[] {2, 4, 1},
                new Sigmoid(),
                new UniformInitializer(-0.5, 0.5)
        );
        var dataSet = new XORBinaryDataSet();
        var optimizer = new GradientDescent(0.2, 0.);
        var loss = new MinimumSquaredError(model.getOutput());
        double finalLoss = model.fit(dataSet, optimizer, loss, 40000, 0.05);
        Assert.assertTrue("Big loss " + finalLoss, finalLoss < 0.05);
    }

    @Test
    public void TestConvergence() throws ExecutionControl.NotImplementedException {
        int diverged = 0;
        for (int i = 0; i < 50; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[] {2, 4, 1},
                    new Sigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new XORBinaryDataSet();
            var optimizer = new GradientDescent(0.2, 0.);
            var loss = new MinimumSquaredError(model.getOutput());
            double finalLoss = model.fit(dataSet, optimizer, loss, 4000, 0.05);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
        }
        Assert.assertTrue("Convergence with high probability busted!", diverged < 6);
    }

    @Test
    public void TestBipolarGD() throws ExecutionControl.NotImplementedException {
        int diverged = 0;
        int trials = 50;
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[] {2, 4, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new BinaryToBipolarWrapper(new XORBinaryDataSet());
            var optimizer = new GradientDescent(0.2, 0.);
            var loss = new MinimumSquaredError(model.getOutput());
            double finalLoss = model.fit(dataSet, optimizer, loss, 350, 0.05);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
        }
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }

    @Test
    public void TestBipolarMomentumGD() throws ExecutionControl.NotImplementedException {

        int diverged = 0;
        int trials = 50;
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[] {2, 4, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            var dataSet = new BinaryToBipolarWrapper(new XORBinaryDataSet());
            var optimizer = new GradientDescent(0.2, 0.9);
            var loss = new MinimumSquaredError(model.getOutput());
            double finalLoss = model.fit(dataSet, optimizer, loss, 40, 0.05);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
        }
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }
}
