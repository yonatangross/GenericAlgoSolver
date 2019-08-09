package server_side.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import server_side.State;
import server_side.dataStructures.Point;
import server_side.problems.Searchable;

public class HillClimbing<Problem, Solution> extends CommonSearcher<Problem, Solution>
        implements Searcher<Problem, Solution> {
    /*
    Algorithm for Simple Hill Climbing:
        Step 1: Evaluate the initial state, if it is goal state then return success and Stop.
        Step 2: Loop Until a solution is found or there is no new operator left to apply.
        Step 3: Select and apply an operator to the current state.
        Step 4: Check new state:
            If it is goal state, then return success and quit.
            Else if it is better than the current state then assign new state as a current state.
            Else if not better than the current state, then return to step2.
        Step 5: Exit.
    */
    private Searchable<Problem> searchable = null;
    private HashSet<State<Problem>> closedSet = new HashSet<State<Problem>>();

    @Override
    public Solution search(Searchable<Problem> searchable) {
        this.searchable = searchable;

        try {
            // openList represents the possible states.
            // closedSet holds currentNodes that tried all possible operators but failed.
            openList = new LinkedList<State<Problem>>();
            addToOpenList(searchable.getInitialState(), null);

            State<Problem> initialState = searchable.getInitialState();

            if (searchable.isGoalState(initialState))
                return (Solution) initialState.getState();

            while (openList.size() > 0) {
                State<Problem> currentNode = popOpenList();
                State<Problem> newState;
                closedSet.add(currentNode);

                List<State<Problem>> possibleOperators = searchable.getAllPossibleStates(currentNode);
                possibleOperators.removeIf(state -> (closedSet.contains(state)));

                for (State<Problem> operator : possibleOperators) {
                    newState = operator;
                    newState.setCameFrom(currentNode.getState());
                    if (searchable.isGoalState(newState)) {
                        closedSet.add(newState);
                        return backTrace(newState, searchable.getInitialState());
                    } else if (isNewBetterThanCurrent(currentNode, newState)) {
                        addToOpenList(newState, currentNode);
                        break;

                    }
                }
            }
        } finally {
            closedSet.clear();
            openList.clear();
            System.out.println(getNumberOfNodesEvaluated());
        }

        return null; // unreachable
    }

    private boolean isNewBetterThanCurrent(State<Problem> currentNode, State<Problem> newState) {
        State<Problem> initState = searchable.getInitialState();
        double currentNodeGScore = computeGScore(initState, currentNode) + 1;
        double neighborGScore = computeGScore(initState, newState);
        if (currentNodeGScore >= neighborGScore)
            return true;
        return false;
    }

    private int computeGScore(State<Problem> initState, State<Problem> currentNode) {
        Point startPoint = Point.strToPoint(initState.getState().toString());
        Point currPoint = Point.strToPoint(currentNode.getState().toString());

        return Math.abs(startPoint.x - currPoint.x) + Math.abs(startPoint.y - currPoint.y);
    }

    private void addToOpenList(State<Problem> state, State<Problem> cameFromState) {
        double currentCost = state.getCost();
        if (cameFromState != null) {
            double newCost = currentCost + cameFromState.getCost();
            state.setCost(newCost);
        }
        try {
            openList.add(state);
        } catch (ClassCastException ex) {
            // TODO: handle exception
            System.out.println("ClassCastException while adding to list." + state.toString());
        }
    }

    private Solution backTrace(State<Problem> goalState, State<Problem> initialState) {
        ArrayList<State<Problem>> tempList = new ArrayList<>();
        goalPath = new LinkedList<>();
        State<Problem> currentState = goalState;
        int nodeIndex;
        Problem cameFromState = null;
        tempList.addAll(closedSet);
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
