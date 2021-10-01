package nn;

public interface IFitCallback {
    void collect(int epoch, double loss);
}
