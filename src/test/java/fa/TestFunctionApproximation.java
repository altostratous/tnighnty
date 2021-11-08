package fa;

import org.junit.Assert;
import org.junit.Test;
import representation.IRepresentable;

public class TestFunctionApproximation {
    @Test
    public void TestLUT() {
        LUT lut = new LUT();
        IRepresentable desiredState = new IRepresentable() {};
        IRepresentable otherState = new IRepresentable() {};
        double[] desiredResponse = new double[] {10};
        double[] otherDesiredResponse = new double[] {5};
        lut.train(desiredState, desiredResponse);
        Assert.assertArrayEquals(desiredResponse, lut.eval(desiredState), 0.);
        lut.train(otherState, otherDesiredResponse);
        Assert.assertArrayEquals(otherDesiredResponse, lut.eval(otherState), 0.);
        lut.train(desiredState, otherDesiredResponse);
        Assert.assertArrayEquals(otherDesiredResponse, lut.eval(desiredState), 0.);
    }
}
