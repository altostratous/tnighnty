package representation;

import java.io.Serializable;

public class Concatenation implements IRepresentable, Serializable {
    private IRepresentable first;
    private IRepresentable second;

    public Concatenation(IRepresentable state, IRepresentable action) {
        this.first = state;
        this.second = action;
    }
    @Override
    public double[] toVector() {
        double[] stateVector = first.toVector();
        double[] actionVector = second.toVector();
        double[] result = new double[stateVector.length + actionVector.length];
        System.arraycopy(stateVector, 0, result, 0, stateVector.length);
        System.arraycopy(actionVector, 0, result, stateVector.length, actionVector.length);
        return result;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IRepresentable)) return false;
        var testRepr = ((IRepresentable) obj).toVector();
        double[] result = toVector();
        if (testRepr.length != result.length) return false;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != testRepr[i]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Concatenation{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
