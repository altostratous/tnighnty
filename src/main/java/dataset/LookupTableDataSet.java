package dataset;

import fa.LUT;
import representation.IRepresentable;

import javax.naming.InsufficientResourcesException;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

// TODO Christina and Husna
public class LookupTableDataSet implements IDataSet {

    ArrayList<DataPoint> points = new ArrayList<>();
    int index = 0;

    public LookupTableDataSet(LUT lut) {
        HashMap map = lut.getHashMap();
        for (Object key :
                map.keySet()) {
            IRepresentable representable = (IRepresentable) key;
            points.add(new DataPoint(
                    representable.toVector(),
                    lut.eval(representable)));

        }
    }

    @Override
    public DataPoint next() {
        if (index < points.size())
            return points.get(index++);
        return null;
    }

    @Override
    public void reset() {
        index = 0;
    }

    @Override
    public DataPoint onlyReadNext() {
        if (index < points.size())
            return points.get(index);
        return null;
    }
}
