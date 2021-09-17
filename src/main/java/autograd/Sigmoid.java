package autograd;

public class Sigmoid extends Operator{
    @Override
    public double evaluate(IVariable[] operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Sigmoid operator only accepts one operand");
        }
        return 1. / (1 + Math.exp(-operands[0].evaluate()));
    }
}
