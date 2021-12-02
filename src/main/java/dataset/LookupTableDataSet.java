package dataset;

import fa.LUT;

// TODO Christina and Husna
public class LookupTableDataSet implements IDataSet {
    public LookupTableDataSet(LUT lut) {

    }


    @Override
    public DataPoint next() {
        // return null when exhausted
        // keep a counter iterating over the hash map
        return new DataPoint(new double[0], new double[0]);
    }

    @Override
    public void reset() {
        // only resets the counter to zero
    }
}
