package autograd;

public class Operation implements IVariable {
    private IOperator operator;
    private IVariable[] operands;

    public Operation(IOperator operator, IVariable ...operands) {
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public double evaluate() {
        return operator.evaluate(operands);
    }
}
