package optimization;

import autograd.Parameter;

import java.util.HashMap;

public class GradientDescent implements IOptimizer {

    private final HashMap<Parameter, Double> lastDelta;
    private final double learningRate;
    private final double momentum;

    public GradientDescent(double learningRate, double momentum) {
        this.lastDelta = new HashMap<>();
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    @Override
    public void update(Parameter[] parameters) {
        for (Parameter p :
                parameters) {
            double delta = -p.getGradient() * learningRate + momentum * lastDelta.getOrDefault(p, 0.);
            p.setValue(p.getValue() + delta);
            p.zeroGradient();
            lastDelta.put(p, delta);
        }
    }
}
