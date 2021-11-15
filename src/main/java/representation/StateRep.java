package representation;
import robocode.ScannedRobotEvent;
import robocode.Event;

public class StateRep implements IStateRepresentation {
    public StateRep()  {}
    @Override
    // can i use this argument event as ScannedRobotEvent? how can i pass in both ScannedRobotEvent and StatusEvent?
    // Or do i need two classes for two event...
    public IState represent(IState state, Event enemyEvent) {
        //why would state be null?
        if (state == null) {
            state = new States(0, 0, 0, 0, 0 ,0);
        }
        States states = (States) state.clone();
        if (enemyEvent instanceof ScannedRobotEvent) {
            ScannedRobotEvent scannedEvent = (ScannedRobotEvent) enemyEvent;
            states.setDistance((int) (scannedEvent.getDistance()));
        }
        return states;
    }
//LEFT HERE: TEST IN ROBOCODE, add in enemyEnergy and energy
}
