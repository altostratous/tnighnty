package robot;

import robocode.Robot;
import robocode.StatusEvent;

public class TopLeftCornerRobot extends Robot {
    @Override
    public void run() {
        super.run();
        ahead(100);
    }

    @Override
    public void onStatus(StatusEvent e) {
        super.onStatus(e);
        var yDistance = getBattleFieldHeight() - getY();
        var xDistance = getX();
        var angle = Math.atan(yDistance / xDistance);
        angle = 270 + Math.toDegrees(angle);
        System.out.printf("%f, %f, %f, %f %n", getY(), getX(), getHeading(), angle);
        double absHeadingDiff = Math.abs(getHeading() - angle);
        if (getHeading() < angle) {
            if (absHeadingDiff > 180) {
                turnLeft(360 - absHeadingDiff);
            } else {
                turnRight(absHeadingDiff);
            }
        } else {
            if (absHeadingDiff > 180) {
                turnRight(360 - absHeadingDiff);
            } else {
                turnLeft(absHeadingDiff);
            }
        }
        if (absHeadingDiff < 0.01) {
            ahead(100);
        }
    }
}
