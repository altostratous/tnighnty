package representation;
import robocode.*;

public class StateRep implements IStateRepresentation {
    public StateRep()  {}
    @Override
    // represent method will be called under two circumstances: either from onStatus or from onScannedRobot.
    // the event passed in can be of either StatusEvent or ScannedRobotEvent
    public IState represent(IState state, Event event) {
        //passed states are the last states, all null at first turn
        if (state == null) {
            state = new States(0, 0, 0, 0, 0 , 0, 0);
        }
        States states = (States) state.clone();//cast State to States

        if (event instanceof ScannedRobotEvent) {
            ScannedRobotEvent scannedEvent = (ScannedRobotEvent) event;
            states.setDistance((int) (scannedEvent.getDistance()));
            states.setEnemyEnergy((int) scannedEvent.getEnergy());
            states.setBearing((int)scannedEvent.getBearing());
        }
        if (event instanceof StatusEvent) {
            StatusEvent statusEvent = (StatusEvent) event;
            states.setX((int) statusEvent.getStatus().getX());
            states.setY((int) statusEvent.getStatus().getY());
            states.setHeading((int) statusEvent.getStatus().getHeading());
            states.setMyEnergy((int) statusEvent.getStatus().getEnergy());
        }
        if (event instanceof WinEvent) {
            states.setEnemyEnergy(0);
        }
        if (event instanceof DeathEvent) {
            states.setMyEnergy(0);
        }
        return states;
    }

}
