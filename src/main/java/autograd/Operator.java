package autograd;

public abstract class Operator implements IOperator {
    protected Integer numberOfOperands;

    public Operator() {
        this.numberOfOperands = null;
    }

    @Override
    public IVariable apply(IVariable ...operands) {
        return new Operation(this, operands);
    }

    protected void validateOperands(IVariable[] operands) {
        if (operands.length != this.numberOfOperands) {
            throw new IllegalArgumentException("Negation accepts only one operand.");
        }
    }
}
