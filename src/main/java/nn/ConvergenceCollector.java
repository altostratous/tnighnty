package nn;

import java.util.ArrayList;

public class ConvergenceCollector implements IFitCallback {
    ArrayList<Double> loss;

    public ConvergenceCollector() {
        this.loss = new ArrayList<>();
    }

    @Override
    public void collect(int epoch, double loss) {
        this.loss.add(loss);
    }

    public int getEpochs() {
        return loss.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < loss.size(); i++) {
            sb.append(+i + " " + loss.get(i) + "\n");
        }
        return sb.toString();
    }
}
