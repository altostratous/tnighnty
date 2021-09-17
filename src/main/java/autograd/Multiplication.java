package autograd;

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
}
