package autograd;

import java.util.Random;

public class UniformInitializer implements IInitializer {

    double a;
    double b;
    Random random;

    public UniformInitializer(double a, double b) {
        this.a = a;
        this.b = b;
        this.random = new Random();
    }

    @Override
    public double next() {
        return random.nextDouble() * (b - a) + a;
    }
}
