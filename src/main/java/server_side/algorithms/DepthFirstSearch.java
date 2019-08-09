package server_side.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import server_side.State;
import server_side.problems.Searchable;

public class DepthFirstSearch<Problem, Solution> extends CommonSearcher<Problem, Solution>
        implements Searcher<Problem, Solution> {
    private HashMap<State<Problem>, Boolean> visitedMap = new HashMap<State<Problem>, Boolean>();
    private Searchable<Problem> searchable = null;

    @Override
    public Solution search(Searchable<Problem> searchable) {
        try {
            this.searchable = searchable;
            openList = new LinkedList<State<Problem>>();
            State<Problem> currentNode = searchable.getInitialState();
            openList.add(currentNode);
            visitedMap.put(currentNode, true);

            DFSUtil(currentNode);
            return convertStatePath(goalPath);

        } finally {
            visitedMap.clear();
            openList.clear();
            System.out.println(getNumberOfNodesEvaluated());
        }
    }

    private void DFSUtil(State<Problem> currentNode) {
        visitedMap.put(currentNode, true);

        while (!openList.isEmpty()) {
            currentNode = popOpenList();
            if (searchable.isGoalState(currentNode)) {
                // backtrace to initstate. use the BestFS function.
                backTrace(currentNode, this.searchable.getInitialState());
            }
            for (State<Problem> state : searchable.getAllPossibleStates(currentNode)) {
                if (visitedMap.get(state) == null || visitedMap.get(state) == false) {
                    state.setCameFrom(currentNode.getState());
                    visitedMap.put(state, true);
                    openList.add(state);
                }
            }
        }
    }

    private void backTrace(State<Problem> goalState, State<Problem> initialState) {
        ArrayList<State<Problem>> tempList = new ArrayList<>();
        goalPath = new LinkedList<>();
        State<Problem> currentState = goalState;
        int nodeIndex;
        Problem cameFromState = null;
        tempList.addAll(visitedMap.keySet());
        // adding goal state.
        goalPath.addFirst(goalState.getState());

        while (!currentState.equals(initialState)) {
            nodeIndex = tempList.indexOf(new State<Problem>(goalPath.getFirst()));
            currentState = tempList.get(nodeIndex);
            cameFromState = currentState.getCameFrom();
            if (cameFromState != null)
                goalPath.addFirst(cameFromState);
        }
    }

    private Solution convertStatePath(LinkedList<Problem> goalPath) {
        return (Solution) goalPath.toString();
    }
}
