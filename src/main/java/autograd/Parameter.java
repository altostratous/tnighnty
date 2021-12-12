package autograd;

import java.io.Serializable;
import java.util.Arrays;

public class Parameter implements IVariable, Serializable {
    private double value;
    private double gradient;
    private boolean trainable;
    private int layer;

    public Parameter() {

    }

    public Parameter(double value) {
        this.value = value;
        trainable = true;
    }

    public Parameter(double value, boolean trainable) {
        this.value = value;
        this.trainable = trainable;
    }

    public static IVariable[] createTensor(double[] desired) {
        var result = new Parameter[desired.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Parameter(desired[i]);
        }
        return result;
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

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{this};
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getGradient() {
        return gradient;
    }

    private void setGradient(double gradient) {
        this.gradient = gradient;
    }

    public boolean isTrainable() {
        return this.trainable;
    }

    public void zeroGradient() {
        this.setGradient(0);
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
}
