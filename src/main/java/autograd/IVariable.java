package autograd;

import jdk.jshell.spi.ExecutionControl;

public interface IVariable {
    public double evaluate();

    public void backward(IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException;
    public Parameter[] getParameters();
}
