package server_side.algorithms;

import server_side.problems.Searchable;

// TODO: need to check generic types.
public interface Searcher<Problem, Solution> {
    public Solution search(Searchable<Problem> searchable);

    public int getNumberOfNodesEvaluated();
}