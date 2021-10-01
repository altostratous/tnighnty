package autograd;

import jdk.jshell.spi.ExecutionControl;

public interface IVariable {
    double evaluate();

    void backward(IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException;

    Parameter[] getParameters();
}
