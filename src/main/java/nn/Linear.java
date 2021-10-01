package nn;

import autograd.*;

public class Linear implements ILayer {
    private final IVariable[][] weight;
    private final IVariable[] bias;

    public Linear(int inFeatures, int outFeatures, IInitializer initializer) {
        this.weight = new Parameter[outFeatures][inFeatures];
        this.bias = new Parameter[outFeatures];
        for (int i = 0; i < outFeatures; i++) {
            for (int j = 0; j < inFeatures; j++) {
                this.weight[i][j] = new Parameter(initializer.next());
            }
            this.bias[i] = new Parameter(initializer.next());
        }
    }

    @Override
    public IVariable[] apply(IVariable[] input) {
        var result = new IVariable[this.weight.length];
        for (int i = 0; i < this.weight.length; i++) {
            int inputSize = this.weight[i].length;
            IVariable[] muls = new IVariable[inputSize + 1];
            for (int j = 0; j < inputSize; j++) {
                muls[j] = new Multiplication().apply(this.weight[i][j], input[j]);
            }
            muls[inputSize] = this.bias[i];
            result[i] = new Addition().apply(muls);
        }
        return result;
    }


    private int getWidth() {
        return this.weight.length;
    }
}
