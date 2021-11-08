package fa;

import representation.IRepresentable;

public interface IFunctionApproximation {
    void train(IRepresentable input, double[] output);
    void eval(IRepresentable input, double[] output);
}
