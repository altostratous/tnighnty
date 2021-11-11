package representation;

public class ConcatenationRepresentation implements IStateActionRepresentation {



    @Override
    public IRepresentable represent(IState state, IAction action) {
        return new Concatenation(state, action);
    }
}
