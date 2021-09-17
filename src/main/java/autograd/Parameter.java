package autograd;

public class Parameter implements IVariable {
    private double value;

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

    public double getValue() {
        return this.value;
    }
}
