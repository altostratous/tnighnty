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

import java.io.IOException;
import java.util.ArrayList;

public class TestLUTNN {

    @Ignore
    @Test
    public void TestBipolarMomentumGD() throws ExecutionControl.NotImplementedException, IOException, ClassNotFoundException {
        int diverged = 0;
        int trials = 3000;
        ArrayList<ConvergenceCollector> stats = new ArrayList<>();
        LUT lut = new LUT(this.getClass().getClassLoader().getResource("LUTTNinetyRobot.obj").getPath(), true);
        lut.load();
        IDataSet dataSet = new LookupTableDataSet(lut);

        var model = Factory.createNeuralNetwork(
                new int[]{15, 2, 1},
                new BipolarSigmoid(),
                new UniformInitializer(-0.05, 0.05),
                false
        );
        var optimizer = new GradientDescent(1e-4, 0.9);

        for (int i = 0; i < trials; i++) {
//            for (int j = 0; j < 1; j++) {
//                dataSet.reset();
//                var p = dataSet.next();
//                for (int k = 0; k < p.getX().length; k++) {
//                    System.out.print(p.getX()[k] + " ");
//                }
//                System.out.println();
//                System.out.println("Desired " + p.getY()[0]);
//                System.out.println("model output " + model.evaluate(p.getX())[0]);
//            }

            var loss = new MeanSquaredError(model.getOutput());
            var collector = new ConvergenceCollector();
            double finalLoss = model.fit(dataSet, optimizer, loss, 100, 0.05, collector, true);
            if (finalLoss > 0.05) {
                System.out.println(Math.sqrt(finalLoss / lut.getSize()));
                var fireKey = new Concatenation(
                        new Concatenation(
                                new Concatenation(
                                        new States(500, 300, 300, 90, 100, 50, 90),
                                        new TNinetyAction(TNinetyAction.ActionType.FIRE)
                                ),
                                new Concatenation(
                                        new States(500, 300, 300, 90, 100, 50, 90),
                                        new TNinetyAction(TNinetyAction.ActionType.FIRE)
                                )
                        ),
                        new Concatenation(
                                new States(500, 300, 300, 90, 100, 50, 90),
                                new TNinetyAction(TNinetyAction.ActionType.FIRE)
                        )
                );

                var aheadKey = new Concatenation(
                        new Concatenation(
                                new Concatenation(
                                        new States(500, 300, 300, 90, 100, 50, 90),
                                        new TNinetyAction(TNinetyAction.ActionType.AHEAD)
                                ),
                                new Concatenation(
                                        new States(500, 300, 300, 90, 100, 50, 90),
                                        new TNinetyAction(TNinetyAction.ActionType.FIRE)
                                )
                        ),
                        new Concatenation(
                                new States(500, 300, 300, 90, 100, 50, 90),
                                new TNinetyAction(TNinetyAction.ActionType.FIRE)
                        )
                );
//                System.out.println("FIRE from model " + model.evaluate(fireKey.toVector())[0]);
//                System.out.println("FIRE from LUT " + lut.eval(fireKey)[0]);
//                System.out.println("AHEAD from model " + model.evaluate(aheadKey.toVector())[0]);
//                System.out.println("AHEAD from LUT " + lut.eval(aheadKey)[0]);
//                System.out.println();
                diverged += 1;
            }
            stats.add(collector);
        }
        Assert.assertTrue("Convergence with high probability busted! " + diverged + " failure out of " + trials, diverged < 6);
    }
}
