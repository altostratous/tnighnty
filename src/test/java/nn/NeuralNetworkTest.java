package nn;

import autograd.Parameter;
import jdk.jshell.spi.ExecutionControl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class NeuralNetworkTest {

    @Test
    public void testNeuralNetworkFactory() {
        var model = Factory.createNeuralNetwork(new int[]{2, 4, 1});
        var result = model.evaluate(new double[]{0, 0});
        Assert.assertEquals(result.length, 1);
    }

    @Test
    public void testNeuralNetworkGradient() throws ExecutionControl.NotImplementedException {
        var model = Factory.createNeuralNetwork(new int[]{2, 4, 1});
        Parameter[] parameters = model.getTrainableParameters();
        for (Parameter parameter :
                parameters) {
            parameter.setValue(1);
        }
        var result = model.evaluate(new double[]{1, 0});
        double[] desired = new double[] {1};
        Assert.assertEquals(result.length, 1);
        double delta = 1e-5;
        double expected = 0.9892621636390686; // obtained by pytorch
        Assert.assertEquals(result[0], expected, delta);
        var loss = new MinimumSquaredError(model.getOutput(), Parameter.createTensor(desired));
        loss.backward(parameters, 1);
        var gradients = new double[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            gradients[i] = parameters[i].getGradient();
        }

        Assert.assertArrayEquals(Arrays.stream(new double[] { // calculated by pytorch
                -0.00022812609677203,
                -0.00020093278726562858,
                -0.00020093278726562858,
                -0.00020093278726562858,
                -0.00020093278726562858,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                -2.395178671577014e-05,
                0.,
                0.,
                0.,
                0.,
        }).sorted().toArray(), Arrays.stream(gradients).sorted().toArray(), delta);
    }
}
