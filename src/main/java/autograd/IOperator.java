package autograd;


public interface IOperator {
    public IVariable apply(IVariable ...operands);

    double evaluate(IVariable[] operands);
}
