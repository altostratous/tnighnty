package nn;

import autograd.IVariable;

public interface ILayer {
    IVariable[] apply(IVariable[] input);
}
