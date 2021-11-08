package fa;

import representation.IRepresentable;

public class LUT implements IFunctionApproximation {

    @Override
    public void train(IRepresentable input, double[] output) {

    }

    @Override
    public double[] eval(IRepresentable input) {
        return new double[]{};
    }
}
