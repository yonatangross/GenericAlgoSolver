package server_side.problems;

import java.util.List;

import server_side.State;

public interface Searchable<Problem> {
    public State<Problem> getInitialState();

    public boolean isGoalState(State<Problem> state);

    public List<State<Problem>> getAllPossibleStates(State<Problem> s); // maybe set instead of list because all states
    // appear only once.

    default State<Problem> getGoalState() {
        throw new UnsupportedOperationException("getGoalState is not supported in: " + this.getClass());
    }
}
