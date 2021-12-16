package robot;

import autograd.UniformInitializer;
import dataset.*;
import fa.LUT;
import jdk.jshell.spi.ExecutionControl;
import nn.*;
import optimization.GradientDescent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import representation.Concatenation;
import representation.States;
import representation.TNinetyAction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TestLUTNN {

    @Ignore
    @Test
    public void GridSearch() throws ExecutionControl.NotImplementedException, IOException, ClassNotFoundException {
        
        LUT lut = new LUT(this.getClass().getClassLoader().getResource("LUTTNinetyRobot.obj").getPath(), true);
        lut.load();
        System.out.println(lut.getSize());
        Assert.assertTrue(false);
        IDataSet dataSet = new LookupTableDataSet(lut);

        for (double momentum :
                new double[]{0., 0.5, 0.9}) {
            for (double lr :
                    new double[] {1e-4, 1e-3, 1e-2}) {
                for (int hiddenNeurons :
                        new int[]{5, 15, 30}) {
                    var model = Factory.createNeuralNetwork(
                            new int[]{15, hiddenNeurons, 1},
                            new BipolarSigmoid(),
                            new UniformInitializer(-0.05, 0.05),
                            false
                    );
                    var optimizer = new GradientDescent(lr, momentum);

                    var loss = new MeanSquaredError(model.getOutput());
                    var collector = new ConvergenceCollector();
                    double finalLoss = model.fit(dataSet, optimizer, loss, 100, 0.00, collector, true);
                    System.out.println(lr + " " + momentum + " " + hiddenNeurons + " " + finalLoss);
                }
            }   
        }
    }

    @Ignore
    @Test
    public void BestGraph() throws ExecutionControl.NotImplementedException, IOException, ClassNotFoundException {

        LUT lut = new LUT(this.getClass().getClassLoader().getResource("LUTTNinetyRobot.obj").getPath(), true);
        lut.load();
        IDataSet dataSet = new LookupTableDataSet(lut);

        var model = Factory.createNeuralNetwork(
                new int[]{15, 15, 1},
                new BipolarSigmoid(),
                new UniformInitializer(-0.05, 0.05),
                false
        );
        var optimizer = new GradientDescent(0.001, 0.5);

        var loss = new MeanSquaredError(model.getOutput());
        var collector = new ConvergenceCollector();
        for (int i = 0; i < 30; i++) {
            double finalLoss = model.fit(dataSet, optimizer, loss, 100, 0.00, collector, true);
            System.out.println((100 * i + 100) + " " + Math.sqrt(finalLoss / lut.getSize()));
        }
    }
}
