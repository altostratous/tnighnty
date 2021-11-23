package representation;

import java.util.Arrays;

public class Memento implements IState {

    IState s0, s1 ,s2;

    public Memento(IState s0, IState s1, IState s2) {
        this.s0 = s0;
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public double[] toVector() {
        double[] s0Vector, s1Vector, s2Vector;
        s2Vector = s2.toVector();
        if (s0 == null) {
            s0Vector = s2Vector;
        } else {
            s0Vector = s0.toVector();
        }
        if (s1 == null) {
            s1Vector = s2Vector;
        } else {
            s1Vector = s1.toVector();
        }
        double[] result = new double[3 * s0Vector.length];
        for (int i = 0; i < s0Vector.length; i++) {
            result[i] = s0Vector[i];
            result[i + s0Vector.length] = s1Vector[i];
            result[i + s0Vector.length + s1Vector.length] = s2Vector[i];
        }
        return result;
    }

    @Override
    public IState clone() {
        return new Memento(s0.clone(), s1.clone(), s2.clone());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memento memento = (Memento) o;
        double[] mine = toVector();
        double[] theirs = memento.toVector();
        for (int i = 0; i < mine.length; i++) {
            if (mine[i] != theirs[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toVector());
    }

    public IState getS2() {
        return s2;
    }

    public IState getS1() {
        return s1;
    }

    public IState getS0() {
        return s0;
    }

}
