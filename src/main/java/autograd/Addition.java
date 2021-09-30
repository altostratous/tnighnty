package autograd;

import jdk.jshell.spi.ExecutionControl;

public class Addition extends Operator {
    @Override
    public double evaluate(IVariable[] operands) {
        double result = 0.;
        for (IVariable operand :
                operands) {
            result += operand.evaluate();
        }
        return result;
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        for (IVariable o :
                operands) {
            o.backward(sources, gradient);
        }
    }
}
