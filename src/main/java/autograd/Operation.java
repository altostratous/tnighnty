package autograd;

import jdk.jshell.spi.ExecutionControl;

import java.util.Arrays;
import java.util.HashSet;

public class Operation implements IVariable {
    private final IOperator operator;
    private final IVariable[] operands;

    public Operation(IOperator operator, IVariable... operands) {
        this.operator = operator;
        this.operands = operands;
    }

    @Override
    public double evaluate() {
        return operator.evaluate(operands);
    }

    @Override
    public void backward(IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        operator.backwards(operands, sources, gradient);
    }


    @Override
    public Parameter[] getParameters() {
        HashSet<Parameter> result = new HashSet<>();
        for (IVariable o :
                this.operands) {
            result.addAll(Arrays.asList(o.getParameters()));
        }
        return result.toArray(new Parameter[0]);
    }

    public IVariable[] getOperands() {
        return operands;
    }
}
