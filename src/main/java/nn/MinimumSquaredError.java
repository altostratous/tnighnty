package nn;

import autograd.*;
import jdk.jshell.spi.ExecutionControl;
import optimization.ILoss;

public class MinimumSquaredError implements IVariable, ILoss {

    private final IVariable operation;
    private final Parameter[] desired;

    public MinimumSquaredError(IVariable[] output) {
        var negation = new Negation();
        var addition = new Addition();
        var exponentiation = new Exponentiation();
        int length = output.length;
        desired = new Parameter[output.length];
        var summationTerms = new IVariable[length];
        for (int i = 0; i < length; i++) {
            desired[i] = new Parameter();
            summationTerms[i] = exponentiation.apply(
                    addition.apply(output[i], negation.apply(desired[i])),
                    new Parameter(2, false)
            );
        }
        this.operation = addition.apply(summationTerms);
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
