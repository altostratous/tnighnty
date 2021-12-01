package robot;

import autograd.UniformInitializer;
import dataset.BinaryToBipolarWrapper;
import dataset.IDataSet;
import dataset.LookupTableDataSet;
import dataset.XORBinaryDataSet;
import fa.LUT;
import jdk.jshell.spi.ExecutionControl;
import nn.BipolarSigmoid;
import nn.ConvergenceCollector;
import nn.Factory;
import nn.MinimumSquaredError;
import optimization.GradientDescent;
import optimization.GradientDescentTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class TestLUTNN {

    @Test
    public void TestBipolarMomentumGD() throws ExecutionControl.NotImplementedException, IOException {
        int diverged = 0;
        int trials = 300;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{2, 4, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            IDataSet dataSet = new LookupTableDataSet(new LUT("LUTTNinetyRobot.obj", true));
            var optimizer = new GradientDescent(0.2, 0.9);
            var loss = new MinimumSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 1000, 0.05, collector);
            if (finalLoss > 0.05) {
                diverged += 1;
            }
            stats.add(collector);
        }
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }
}
