package representation;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements IState, Serializable {
    private int x;
    private int y;
    private int heading;

    public Coordinates(int x, int y, int heading) {
        setX(x);
        setY(y);
        setHeading(heading);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeading(int bearing) {
        this.heading = bearing;
    }

    @Override
    public IState clone() {
        return new Coordinates(this.x, this.y, this.heading);
    }

    public int getX() {
        return this.x;
    }

    @Override
    public double[] toVector() {
        return new double[] {x, y, heading};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y && heading == that.heading;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, heading);
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                ", heading=" + heading +
                '}';
    }
}
