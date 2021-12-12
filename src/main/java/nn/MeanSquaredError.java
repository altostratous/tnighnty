package nn;

import autograd.*;
import jdk.jshell.spi.ExecutionControl;
import optimization.ILoss;

public class MeanSquaredError implements IVariable, ILoss {

    private final IVariable operation;
    private final Parameter[] desired;

    public MeanSquaredError(IVariable[] output) {
        var negation = new Negation();
        var addition = new Addition();
        var multiplication = new Multiplication();
        var exponentiation = new Exponentiation();
        Parameter two = new Parameter(2, false);
        Parameter half = new Parameter(0.5, false);
        int length = output.length;
        desired = new Parameter[output.length];
        var summationTerms = new IVariable[length];
        for (int i = 0; i < length; i++) {
            desired[i] = new Parameter();
            summationTerms[i] = exponentiation.apply(
                    addition.apply(output[i], negation.apply(desired[i])),
                    two
            );
        }
        this.operation = multiplication.apply(addition.apply(summationTerms), half);
    }

    @Override
    public double evaluate() {
        return operation.evaluate();
    }

    @Override
    public void backward(IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        operation.backward(sources, gradient);
    }

    @Override
    public Parameter[] getParameters() {
        return this.operation.getParameters();
    }

    @Override
    public void setDesired(double[] desired) {
        for (int i = 0; i < this.desired.length; i++) {
            this.desired[i].setValue(desired[i]);
        }
    }
}
