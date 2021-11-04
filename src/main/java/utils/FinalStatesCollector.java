package utils;

import nn.IFitCallback;
import state.Coordinates;
import state.IState;

import java.util.ArrayList;

public class FinalStatesCollector implements ICollector {

    private final ArrayList<IState> finalStates = new ArrayList<>();

    public ArrayList<IState> getFinalStates() {
        return finalStates;
    }
}
