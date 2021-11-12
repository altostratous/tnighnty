package representation;

import robocode.Event;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

public class CoordinatesRepresentation implements IStateRepresentation {
    @Override
    public IState represent(IState state, Event event) {
        if (state == null) {
            state = new Coordinates(0, 0, 0);
        }
        Coordinates coordinates = (Coordinates) state.clone();
        if (event instanceof StatusEvent) {
            StatusEvent statusEvent = (StatusEvent) event;
            coordinates.setX((int) (statusEvent.getStatus().getX() / 100));
            coordinates.setY((int) (statusEvent.getStatus().getY() / 100));
            coordinates.setHeading((int) ((statusEvent.getStatus().getHeading() + 45) / 90));
        }
        return coordinates;
    }
}
