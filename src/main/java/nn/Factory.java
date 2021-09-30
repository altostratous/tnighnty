package nn;

import autograd.IInitializer;
import autograd.IVariable;
import autograd.Parameter;
import autograd.UniformInitializer;

public class Factory {
    public static Model createNeuralNetwork(int[] sizes, ILayer activation, IInitializer initializer) {
        if (sizes.length < 2) {
            throw new IllegalArgumentException("Sizes must at least contain 2 integers for the first and the second layer.");
        }
        var inputs = new Parameter[sizes[0]];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new Parameter(initializer.next());
        }
        IVariable[] lastLayerOutput = inputs;
        for (int i = 1; i < sizes.length; i++) {
            lastLayerOutput = new Linear(sizes[i - 1], sizes[i], initializer).apply(lastLayerOutput);
            lastLayerOutput = activation.apply(lastLayerOutput);
        }
        return new Model(inputs, lastLayerOutput);
    }
    public static Model createNeuralNetwork(int[] sizes, ILayer activation) {
        return createNeuralNetwork(sizes, activation, new UniformInitializer(-0.5, 0.5));
    }
}
