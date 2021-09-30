package optimization;

import autograd.Parameter;

public interface IOptimizer {
    void update(Parameter[] parameters);
}
