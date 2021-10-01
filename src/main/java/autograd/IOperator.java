package autograd;


import jdk.jshell.spi.ExecutionControl;

public interface IOperator {
    IVariable apply(IVariable... operands);

    double evaluate(IVariable[] operands);

    void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException;
}
