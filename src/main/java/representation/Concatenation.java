package representation;

import javax.swing.text.TableView;

public class Concatenation implements IStateActionRepresentation {

    double[] result;

    @Override
    public IRepresentable represent(IState state, IAction action) {
        double[] stateVector = state.toVector();
        double[] actionVector = state.toVector();
        result = new double[stateVector.length + actionVector.length];
        System.arraycopy(stateVector, 0, result, 0, stateVector.length);
        System.arraycopy(actionVector, 0, result, stateVector.length, actionVector.length);
        return new IRepresentable() {
            @Override
            public double[] toVector() {
                return result;
            }

            @Override
            public int hashCode() {
                return state.hashCode() + action.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof Concatenation)) return false;
                Concatenation other = (Concatenation) obj;
                if (other.result.length != result.length) return false;
                for (int i = 0; i < result.length; i++) {
                    if (result[i] != other.result[i]) return false;
                }
                return true;
            }
        };
    }
}
