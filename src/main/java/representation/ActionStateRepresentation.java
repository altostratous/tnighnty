package representation;

public class ActionStateRepresentation implements IRepresentation {
    private IStateRepresentation stateRepresentation;
    private IActionRepresentation actionRepresentation;

    public ActionStateRepresentation(IStateRepresentation stateRepresentation, IActionRepresentation actionRepresentation) {
        this.stateRepresentation = stateRepresentation;
        this.actionRepresentation = actionRepresentation;
    }
}
