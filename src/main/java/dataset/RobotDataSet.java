package dataset;

import representation.IRepresentable;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class RobotDataSet implements IDataSet, Serializable {
    LinkedList<double[]> x = new LinkedList<>();
    LinkedList<double[]> y = new LinkedList<>();
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
            offset = 0;
            x.removeFirst();
            y.removeFirst();
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

    public LinkedList<double[]> getX() {
        return x;
    }

    public LinkedList<double[]> getY() {
        return y;
    }
}
