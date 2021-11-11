package representation;

public class Coordinates implements IState {
    private int x;
    private int y;
    private int enemyBearing;

    public Coordinates(int x, int y, int enemyBearing) {
        setX(x);
        setY(y);
        setEnemyBearing(enemyBearing);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEnemyBearing(int bearing) {
        this.enemyBearing = bearing;
    }

    @Override
    public IState clone() {
        return new Coordinates(this.x, this.y, this.enemyBearing);
    }

    public int getX() {
        return this.x;
    }
}
