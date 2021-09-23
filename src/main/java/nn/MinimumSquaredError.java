package nn;

import autograd.*;
import jdk.jshell.spi.ExecutionControl;

public class MinimumSquaredError implements IVariable {

    private final IVariable operation;

    public MinimumSquaredError(IVariable[] output, IVariable[] desired) {
        var negation = new Negation();
        var addition = new Addition();
        var exponentiation = new Exponentiation();
        int length = output.length;
        if (desired.length != length) {
            throw new IllegalArgumentException("Dimensions mismatch.");
        }
        var summationTerms = new IVariable[length];
        for (int i = 0; i < length; i++) {
            summationTerms[i] = exponentiation.apply(
                    addition.apply(output[i], negation.apply(desired[i])),
                    new Parameter(2)
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
}
