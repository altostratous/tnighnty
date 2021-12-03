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
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class TestLUTNN {

    @Ignore
    @Test
    public void TestBipolarMomentumGD() throws ExecutionControl.NotImplementedException, IOException, ClassNotFoundException {
        int diverged = 0;
        int trials = 300;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            var model = Factory.createNeuralNetwork(
                    new int[]{6, 12, 1},
                    new BipolarSigmoid(),
                    new UniformInitializer(-0.5, 0.5)
            );
            LUT lut = new LUT(this.getClass().getClassLoader().getResource("LUTTNinetyRobot.obj").getPath(), true);
            lut.load();
            IDataSet dataSet = new LookupTableDataSet(lut);
            for (int j = 0; j < 1; j++) {
                var p = dataSet.next();
                for (int k = 0; k < p.getX().length; k++) {
                    System.out.print(p.getX()[k] + " ");
                }
                System.out.println();
                System.out.println("Desired " + p.getY()[0]);
                System.out.println("model output " + model.evaluate(p.getX())[0]);
            }

            var optimizer = new GradientDescent(0.000001, 0.1);
            var loss = new MinimumSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 10, 0.05, collector);
            if (finalLoss > 0.05) {
                System.out.println(finalLoss);
                diverged += 1;
            }
            stats.add(collector);
        }
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }
}
