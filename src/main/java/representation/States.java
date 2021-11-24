package representation;

import java.util.Arrays;
import java.util.Objects;

public class States implements IState{

    private int distance;
    private int x;
    private int y;
    private int heading;
    private int bearing;

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }

    public int getBearing() {
        return bearing;
    }

    public enum energy {LOW, MEDIUM, HIGH};
    private int myEnergy;
    private int enemyEnergy;

    public States(int distance, int x, int y, int heading, int myEnergy, int enemyEnergy, int bearing) {
        setDistance(distance);
        setX(x);
        setY(y);
        setHeading(heading);
        setMyEnergy(myEnergy);
        setEnemyEnergy(enemyEnergy);
        setBearing(bearing);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {this.y = y;}
    public void setHeading(int heading) {
        this.heading = heading;
    }
    public void setMyEnergy(int myEnergy) {
        this.myEnergy = myEnergy;
    }
    public void setEnemyEnergy(int enemyEnergy) {
        this.enemyEnergy = enemyEnergy;
    }

    @Override
    public IState clone() {
        return new States(this.distance, this.x, this.y, this.heading, this.myEnergy, this.enemyEnergy, this.bearing);
    }

    @Override
    public double[] toVector() {
        return new double[]{
//                this.x / 50,
//                this.y / 50,
//                (this.heading + 45) / 90,
//                (this.bearing + 45) / 90,
//                this.myEnergy / 40,
//                this.enemyEnergy / 40,
                this.distance / 100,
        };
    }

    public int getDistance() {
        return distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeading() {
        return heading;
    }

    public int getMyEnergy() {
        return myEnergy;
    }

    public int getEnemyEnergy() {
        return enemyEnergy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        States states = (States) o;
        double[] mine = toVector();
        double[] theirs = states.toVector();
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

    @Override
    public String toString() {
        return "States{" +
                "distance=" + distance +
                ", x=" + x +
                ", y=" + y +
                ", heading=" + heading +
                ", bearing=" + bearing +
                ", myEnergy=" + myEnergy +
                ", enemyEnergy=" + enemyEnergy +
                '}';
    }
}

