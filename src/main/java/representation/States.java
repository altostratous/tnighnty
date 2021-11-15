package representation;

public class States implements IState{
    private int distance;
    private int x;
    private int y;
    private int heading;
    private int energy;
    private int enemyEnergy;

    public States(int distance, int x, int y, int heading, int energy, int enemyEnergy) {
        setDistance(distance);
        setX(x);
        setY(y);
        setHeading(heading);
        setEnergy(energy);
        setEnemyEnergy(enemyEnergy);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setHeading(int heading) {
        this.heading = heading;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public void setEnemyEnergy(int enemyEnergy) {
        this.enemyEnergy = enemyEnergy;
    }

    @Override
    public IState clone() {
        return new States(this.distance, this.x, this.y, this.heading, this.energy, this.enemyEnergy);
    }

    @Override
    public double[] toVector() {
        return new double[0];
    }
}

