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
        if (this.numberOfOperands == null) {
            return;
        }
        if (operands.length != this.numberOfOperands) {
            throw new IllegalArgumentException(String.format("%s accepts only one operand.", this.getClass().getName()));
        }
    }
}
