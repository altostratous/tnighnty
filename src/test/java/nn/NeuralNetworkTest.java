package nn;

import autograd.Parameter;
import org.junit.Assert;
import org.junit.Test;

public class NeuralNetworkTest {

    @Test
    public void testNeuralNetworkFactory() {
        var model = Factory.createNeuralNetwork(new int[]{2, 4, 1});
        var result = model.evaluate(new double[]{0, 0});
        Assert.assertEquals(result.length, 1);
    }
}
