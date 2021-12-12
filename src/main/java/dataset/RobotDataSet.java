package dataset;

import representation.IRepresentable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RobotDataSet implements IDataSet, Serializable {
    ArrayList<double[]> x = new ArrayList<double[]>();
    ArrayList<double[]> y = new ArrayList<double[]>();
    int index = 0;
    int offset = 0;
    int windowSize;
    public RobotDataSet(int windowSize) {
        this.windowSize = windowSize;
    }

    public void addPattern(IRepresentable input, double[] output) {
        x.add(input.toVector());
        y.add(output);
        if (x.size() > windowSize) {
            offset = x.size() - windowSize;
        }
    }


    @Override
    public DataPoint next() {
        if (index < x.size()) {
            var result = new DataPoint(x.get(index), y.get(index));
            index++;
            return result;
        }
        return null;

    }

    @Override
    public void reset() {
        index = offset;
    }

    @Override
    public DataPoint onlyReadNext() {
        if (index < x.size()) {
            return new DataPoint(x.get(index), y.get(index));
        }
        return null;
    }

    public int getSize() {
        if (x.size() < windowSize) return x.size();
        return windowSize;
    }

    public ArrayList<double[]> getX() {
        return x;
    }

    public ArrayList<double[]> getY() {
        return y;
    }
}
