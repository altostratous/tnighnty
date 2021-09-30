package nn;

import autograd.IVariable;
import autograd.Parameter;

import java.util.*;

public class Model {
    private Parameter[] input;
    private IVariable[] output;

    public Model(Parameter[] input, IVariable[] output) {
        this.input = input;
        this.output = output;
    }


    public double[] evaluate(double[] input) {
        var result = new double[output.length];
        for (int i = 0; i < input.length; i++) {
            this.input[i].setValue(input[i]);
        }
        for (int i = 0; i < output.length; i++) {
            result[i] = output[i].evaluate();
        }
        return result;
    }

    public Parameter[] getParameters() {
        HashSet<Parameter> result = new HashSet<>();
        for (IVariable o :
                this.output) {
            result.addAll(Arrays.asList(o.getParameters()));
        }
        return result.toArray(new Parameter[0]);
    }

    public Parameter[] getTrainableParameters() {
        var results = new HashSet<Parameter>();
        for (Parameter p :
                getParameters()) {
            if (p.isTrainable()) {
                results.add(p);
            }
        }
        for (Parameter p: input) {
            results.remove(p);
        }

        return results.toArray(new Parameter[0]);
    }

    public IVariable[] getOutput() {
        return output;
    }
}
