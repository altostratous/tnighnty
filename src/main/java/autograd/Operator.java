package autograd;

public abstract class Operator implements IOperator {
    @Override
    public IVariable apply(IVariable ...operands) {
        return new Operation(this, operands);
    }
}
