package nn;

import autograd.IVariable;
import autograd.Parameter;

public class Factory {
    public static Model createNeuralNetwork(int[] sizes) {
        if (sizes.length < 2) {
            throw new IllegalArgumentException("Sizes must at least contain 2 integers for the first and the second layer.");
        }
        var inputs = new Parameter[sizes[0]];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new Parameter(Math.random());
        }
        IVariable[] lastLayerOutput = inputs;
        for (int i = 1; i < sizes.length; i++) {
            lastLayerOutput = new Linear(sizes[i - 1], sizes[i]).apply(lastLayerOutput);
        }
        return new Model(inputs, lastLayerOutput);
    }
}
