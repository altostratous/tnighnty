package representation;

public class States implements IState{

    private int distance;
    private int x;
    private int y;
    private int heading;
    public enum energy {LOW, MEDIUM, HIGH};
    private energy myEnergy;
    private energy enemyEnergy;

    public States(int distance, int x, int y, int heading, energy myEnergy, energy enemyEnergy) {
        setDistance(distance);
        setX(x);
        setY(y);
        setHeading(heading);
        setMyEnergy(myEnergy);
        setEnemyEnergy(enemyEnergy);
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
    public void setMyEnergy(energy myEnergy) {
        this.myEnergy = myEnergy;
    }
    public void setEnemyEnergy(energy enemyEnergy) {
        this.enemyEnergy = enemyEnergy;
    }

    @Override
    public IState clone() {
        return new States(this.distance, this.x, this.y, this.heading, this.myEnergy, this.enemyEnergy);
    }

    @Override
    public double[] toVector() {
        return new double[0];
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

    public energy getMyEnergy() {
        return myEnergy;
    }

    public energy getEnemyEnergy() {
        return enemyEnergy;
    }
}

