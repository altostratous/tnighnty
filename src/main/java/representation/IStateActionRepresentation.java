package representation;

public interface IStateActionRepresentation {
    IRepresentable represent(IState state, IAction action);
}
