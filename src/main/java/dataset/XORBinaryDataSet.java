package dataset;

public class XORBinaryDataSet implements IDataSet {

    protected double[][] x;
    protected double[] y;
    private int index;

    public XORBinaryDataSet() {
        index = 0;
        x = new double[][]{
                {0., 0.},
                {0., 1.},
                {1., 0.},
                {1., 1.},
        };
        y = new double[]{
                0.,
                1.,
                1.,
                0.,
        };
    }

    @Override
    public DataPoint next() {
        if (index < x.length) {
            var result = new DataPoint(x[index], new double[]{y[index]});
            index++;
            return result;
        }
        return null;
    }

    @Override
    public void reset() {
        index = 0;
    }
}
