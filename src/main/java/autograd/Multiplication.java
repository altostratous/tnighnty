package autograd;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class Multiplication extends Operator{

    @Override
    public double evaluate(IVariable[] operands) {
        double result = 1.;
        for (IVariable operand :
                operands) {
            result *= operand.evaluate();
        }
        return result;
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        validateOperands(operands);
        var multiplier = operands[0];
        var multiplicand = operands[1];
        var multiplierValue = multiplier.evaluate();
        var multiplicandValue = multiplicand.evaluate();
        multiplier.backward(sources, gradient * multiplicandValue);
        multiplicand.backward(sources, gradient * multiplierValue);
    }
}
