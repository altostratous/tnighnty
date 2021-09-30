package dataset;

public class DataPoint {
    private double[] x;
    private double[] y;

    public DataPoint(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[] getY() {
        return y;
    }

    public double[] getX() {
        return x;
    }
}
