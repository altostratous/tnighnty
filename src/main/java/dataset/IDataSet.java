package dataset;

public interface IDataSet {
    DataPoint next();

    void reset();

    DataPoint onlyReadNext();
}
