package representation;
import robocode.ScannedRobotEvent;
import robocode.Event;
import robocode.StatusEvent;

public class StateRep implements IStateRepresentation {
    public StateRep()  {}
    @Override
    // represent method will be called under two circumstances: either from onStatus or from onScannedRobot.
    // the event passed in can be of either StatusEvent or ScannedRobotEvent
    public IState represent(IState state, Event event) {
        //passed states are the last states, all null at first turn
        if (state == null) {
            state = new States(0, 0, 0, 0, States.energy.LOW ,States.energy.LOW);
        }
        States states = (States) state.clone();//cast State to States

        if (event instanceof ScannedRobotEvent) {
            ScannedRobotEvent scannedEvent = (ScannedRobotEvent) event;
            states.setDistance((int) (scannedEvent.getDistance()) / 100);
            if ((scannedEvent.getEnergy()) < 300) {
                states.setEnemyEnergy(States.energy.LOW);
            }
            else if ((scannedEvent.getEnergy()) < 600) {
                states.setEnemyEnergy(States.energy.MEDIUM);
            }
            else {
                states.setEnemyEnergy(States.energy.HIGH);
            } // does this return energy of the scanned robot or myself?
        }
        if (event instanceof StatusEvent) {
            StatusEvent statusEvent = (StatusEvent) event;
            states.setX((int) (statusEvent.getStatus().getX() / 100));
            states.setY((int) (statusEvent.getStatus().getY() / 100));
            states.setHeading((int) ((statusEvent.getStatus().getHeading() + 45) / 90));
            if ((statusEvent.getStatus().getEnergy()) < 300) {
                states.setMyEnergy(States.energy.LOW);
            }
            else if ((statusEvent.getStatus().getEnergy()) < 600) {
                states.setMyEnergy(States.energy.MEDIUM);
            }
            else {
                states.setMyEnergy(States.energy.HIGH);
            }

        }
        return states;
    }

}
