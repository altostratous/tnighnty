package dataset;

public class DataPointDataSet implements IDataSet {
    private final DataPoint dataPoint;
    private boolean endOfDataSet = false;

    public DataPointDataSet(DataPoint dataPoint) {
        this.dataPoint = dataPoint;
    }


    @Override
    public DataPoint next() {
        if (endOfDataSet) {
            return null;
        } else {
            endOfDataSet = true;
            return dataPoint;
        }
    }

    @Override
    public void reset() {
        endOfDataSet = false;
    }

    @Override
    public DataPoint onlyReadNext() {
        if (endOfDataSet) {
            return null;
        } else {
            return dataPoint;
        }
    }
}
