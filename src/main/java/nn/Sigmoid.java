package nn;

import autograd.IVariable;

public class Sigmoid implements ILayer{

    @Override
    public IVariable[] apply(IVariable[] input) {
        var operator = new autograd.Sigmoid();
        var result = new IVariable[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = operator.apply(input[i]);
        }
        return result;
    }
}
