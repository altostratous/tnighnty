package autograd;

import jdk.jshell.spi.ExecutionControl;

public class Sigmoid extends Operator {

    public Sigmoid() {
        this.numberOfOperands = 1;
    }

    @Override
    public double evaluate(IVariable[] operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Sigmoid operator only accepts one operand");
        }
        return 1. / (1 + Math.exp(-operands[0].evaluate()));
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        validateOperands(operands);
        var x = operands[0];
        var y = evaluate(operands);
        x.backward(sources, gradient * y * (1 - y));
    }
}
