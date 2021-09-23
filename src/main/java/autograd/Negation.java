package autograd;

import jdk.jshell.spi.ExecutionControl;

public class Negation extends Operator {

    public Negation() {
        this.numberOfOperands = 1;
    }

    @Override
    public double evaluate(IVariable[] operands) {
        validateOperands(operands);
        return -operands[0].evaluate();
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        operands[0].backward(sources, -gradient);
    }
}
