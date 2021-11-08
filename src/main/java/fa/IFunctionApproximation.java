package fa;

import representation.IRepresentable;

public interface IFunctionApproximation {
    void train(IRepresentable input, double[] output);
    double[] eval(IRepresentable input);
}
