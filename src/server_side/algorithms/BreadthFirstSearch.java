package server_side.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import server_side.State;
import server_side.problems.Searchable;

public class BreadthFirstSearch<Problem, Solution> extends CommonSearcher<Problem, Solution>
        implements Searcher<Problem, Solution> {
    private HashMap<State<Problem>, Boolean> visitedNodes = new HashMap<State<Problem>, Boolean>();

    @Override
    public Solution search(Searchable<Problem> searchable) {
        try {
            // with priority queue its the cheapest
            openList = new LinkedList<State<Problem>>();
            State<Problem> currentNode = searchable.getInitialState();
            openList.add(currentNode);
            visitedNodes.put(currentNode, true);
            while (!openList.isEmpty()) {
                currentNode = popOpenList();
                if (searchable.isGoalState(currentNode))
                    // backtrace to initstate. use the BestFS function.
                    return backTrace(currentNode, searchable.getInitialState());

                for (State<Problem> state : searchable.getAllPossibleStates(currentNode)) {
                    if (visitedNodes.get(state) == null || visitedNodes.get(state) == false) {
                        state.setCameFrom(currentNode.getState());
                        visitedNodes.put(state, true);
                        openList.add(state);
                    }
                }
            }

        } finally {
            visitedNodes.clear();
            openList.clear();
            System.out.println(getNumberOfNodesEvaluated());
        }

        return null; // unreachable
    }

    private Solution backTrace(State<Problem> goalState, State<Problem> initialState) {
        ArrayList<State<Problem>> tempList = new ArrayList<>();
        goalPath = new LinkedList<>();
        State<Problem> currentState = goalState;
        int nodeIndex;
        Problem cameFromState = null;
        tempList.addAll(visitedNodes.keySet());
        // adding goal state.
        goalPath.addFirst(goalState.getState());

        while (!currentState.equals(initialState)) {
            nodeIndex = tempList.indexOf(new State<Problem>(goalPath.getFirst()));
            currentState = tempList.get(nodeIndex);
            cameFromState = currentState.getCameFrom();
            if (cameFromState != null)
                goalPath.addFirst(cameFromState);
        }

        return convertStatePath(goalPath);

    }

    private Solution convertStatePath(LinkedList<Problem> goalPath) {
        return (Solution) goalPath.toString();
    }
}
