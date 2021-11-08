package representation;

public class StateActionRepresentation implements IRepresentation {
    private IStateRepresentation stateRepresentation;
    private IActionRepresentation actionRepresentation;

    public StateActionRepresentation(IStateRepresentation stateRepresentation, IActionRepresentation actionRepresentation) {
        this.stateRepresentation = stateRepresentation;
        this.actionRepresentation = actionRepresentation;
    }
}
