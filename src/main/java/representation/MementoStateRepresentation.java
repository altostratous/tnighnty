package representation;

import robocode.Event;

public class MementoStateRepresentation implements IStateRepresentation{

    IStateRepresentation internalRepresentation;

    public MementoStateRepresentation(IStateRepresentation internalRepresentation) {
        this.internalRepresentation = internalRepresentation;
    }

    @Override
    public IState represent(IState state, Event event) {
        Memento lastState = (Memento) state;
        if (lastState == null) {
            lastState = new Memento(null, null, null);
        }
        IState newestState = internalRepresentation.represent(lastState.s2, event);
        return new Memento(lastState.s1, lastState.s2, newestState);
    }
}
