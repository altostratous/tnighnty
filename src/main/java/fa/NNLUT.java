package fa;

import representation.IRepresentable;

import java.io.IOException;

public class NNLUT implements IFunctionApproximation {

    NN nn = new NN("NNTNinetyRobot.NN", false);
    LUT lut = new LUT("NNTNinetyRobot.LUT", false);
    public NNLUT() {

    }

    @Override
    public void train(IRepresentable input, double[] output) {
        nn.train(input, output);
        lut.train(input, output);
    }

    @Override
    public double[] eval(IRepresentable input) {
        double first = nn.eval(input)[0];
        double second = lut.eval(input)[0];
        System.out.println("NN " +first + " LUT " + second);
        return new double[] {second};
    }

    @Override
    public void save() throws IOException {
        nn.save();
        lut.save();
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        nn.load();
        lut.load();
    }

    @Override
    public int getSize() {
        return 0;
    }
}
