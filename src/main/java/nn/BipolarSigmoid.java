package nn;

import autograd.IVariable;
import autograd.Parameter;

public class BipolarSigmoid implements ILayer {

    Parameter scale;

    public BipolarSigmoid(double scale) {
        this.scale = new Parameter(scale, false);
    }

    public BipolarSigmoid() {
        this.scale = new Parameter(1., false);;
    }

    @Override
    public IVariable[] apply(IVariable[] input) {
        var sigmoid = new autograd.Sigmoid();
        var scalar = new Parameter(2, false);
        var constant = new Parameter(-1, false);
        var addition = new autograd.Addition();
        var multiplication = new autograd.Multiplication();
        var result = new IVariable[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = multiplication.apply(
                    addition.apply(
                            multiplication.apply(
                                scalar,
                                sigmoid.apply(input[i])),
                        constant
                    ),
                    this.scale
            );
        }
        return result;
    }
}
