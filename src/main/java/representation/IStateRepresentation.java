package representation;

import robocode.Event;

public interface IStateRepresentation extends IRepresentation {
    /**
     * Evolves the robot state given the last state and the event
     * @param state the previous state of the robot
     * @param event the robot event containing changes to the state
     * @return returns a new state expressing the changed state
     */
    IState represent(IState state, Event event);
}
