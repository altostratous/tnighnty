package nn;

import autograd.IVariable;

public interface ILayer {
    public IVariable[] apply(IVariable[] input);
}
