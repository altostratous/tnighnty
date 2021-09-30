package dataset;

public class BinaryToBipolarWrapper implements IDataSet {

    IDataSet binaryDataSet;

    public BinaryToBipolarWrapper(IDataSet binaryDataSet) {
        this.binaryDataSet = binaryDataSet;
    }

    @Override
    public DataPoint next() {
        DataPoint result = binaryDataSet.next();
        if (result == null) return null;
        double[] x = result.getX().clone();
        double[] y = result.getY().clone();
        for (int i = 0; i < x.length; i++) {
            x[i] = 2 * x[i] - 1;
        }
        for (int i = 0; i < y.length; i++) {
            y[i] = 2 * y[i] - 1;
        }
        return new DataPoint(x, y);
    }

    @Override
    public void reset() {
        binaryDataSet.reset();
    }
}
