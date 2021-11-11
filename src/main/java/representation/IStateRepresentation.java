package representation;

import robocode.Event;

public interface IStateRepresentation extends IRepresentation {
    IState represent(IState state, Event event);
}
