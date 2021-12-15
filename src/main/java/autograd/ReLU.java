package autograd;

import jdk.jshell.spi.ExecutionControl;

public class ReLU extends Operator {

    public ReLU() {
        this.numberOfOperands = 1;
    }

    @Override
    public double evaluate(IVariable[] operands) {
        if (operands.length != 1) {
            throw new IllegalArgumentException("Sigmoid operator only accepts one operand");
        }
        double result = Math.max(0., operands[0].evaluate());
//        System.out.println("ReLU " + result);
        return result;
    }

    @Override
    public void backwards(IVariable[] operands, IVariable[] sources, double gradient) throws ExecutionControl.NotImplementedException {
        validateOperands(operands);
        var x = operands[0];
        var y = evaluate(operands);
//        System.out.println(gradient);
        if (y > 0) {
//            System.out.println("Gradient " + y + " " + gradient);
            x.backward(sources, gradient);
        } else {
//            System.out.println("Gradient 0");
            x.backward(sources, 0.);
        }
    }
}
