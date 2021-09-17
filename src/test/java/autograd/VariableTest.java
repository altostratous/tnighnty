package autograd;


import org.junit.Assert;
import org.junit.Test;

public class VariableTest {

    @Test
    public void testAddition() {
        Assert.assertEquals(new Addition().apply(new Parameter(12), new Parameter(2.)).evaluate(), 14., 0.);
    }

    @Test
    public void testVariableEvaluation() {
        Assert.assertEquals(new Parameter(250).evaluate(), 250., 0);
    }

}
