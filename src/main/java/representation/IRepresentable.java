package representation;

import java.io.Serializable;

public interface IRepresentable extends Serializable {
    double[] toVector();
}
