package dataset;

import representation.IRepresentable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RobotDataSet implements IDataSet, Serializable {
    ArrayList<double[]> x = new ArrayList<double[]>();
    ArrayList<double[]> y = new ArrayList<double[]>();
    int index = 0;
    public RobotDataSet() {

    }

    public void addPattern(IRepresentable input, double[] output) {
        x.add(input.toVector());
        y.add(output);
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
        index = 0;
    }

    @Override
    public DataPoint onlyReadNext() {
        if (index < x.size()) {
            return new DataPoint(x.get(index), y.get(index));
        }
        return null;
    }

    public int getSize() {
        return x.size();
    }
}
