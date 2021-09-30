package optimization;

import autograd.IVariable;

public interface ILoss extends IVariable {
    void setDesired(double[] desired);
}
