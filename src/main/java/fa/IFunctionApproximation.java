package fa;

import representation.IRepresentable;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFunctionApproximation {
    void train(IRepresentable input, double[] output);
    double[] eval(IRepresentable input);

    void save() throws IOException;

    void load() throws IOException, ClassNotFoundException;
}
