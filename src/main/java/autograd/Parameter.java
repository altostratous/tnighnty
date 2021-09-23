package autograd;

import java.util.Arrays;

public class Parameter implements IVariable {
    private double value;
    private double gradient;

    public static IVariable[] createTensor(double[] desired) {
        var result = new Parameter[desired.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Parameter(desired[i]);
        }
        return result;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Parameter(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public void backward(IVariable[] sources, double gradient) {
        if (Arrays.stream(sources).anyMatch(x -> x == this)) {
            setGradient(gradient + getGradient());
        }
    }

    private void setGradient(double gradient) {
        this.gradient = gradient;
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[0];
    }

    public double getValue() {
        return this.value;
    }

    public double getGradient() {
        return gradient;
    }
}
