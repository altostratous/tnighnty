package autograd;

import jdk.jshell.spi.ExecutionControl;

public class Exponentiation extends Operator {
    @Override
    public double evaluate(IVariable[] operands) {
        if (operands.length != 2) {
            throw new IllegalArgumentException("Exponentiation accepts 2 arguments.");
        }
        return Math.pow(operands[0].evaluate(), operands[1].evaluate());
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        IVariable baseVariable = operands[0];
        var baseValue = baseVariable.evaluate();
        IVariable exponentVariable = operands[1];
        var exponentValue = exponentVariable.evaluate();
        if (exponentVariable.getParameters().length > 1) {
            throw new ExecutionControl.NotImplementedException("Back propagation to the exponent is not implemented.");
        }
        var gradientToPropagate = Math.pow(gradient * baseValue * exponentValue, exponentValue - 1);
        baseVariable.backward(sources, gradientToPropagate);
    }
}
