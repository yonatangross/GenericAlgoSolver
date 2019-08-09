package server_side.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import server_side.State;
import server_side.problems.Searchable;

public class BestFirstSearch<Problem, Solution> extends CommonSearcher<Problem, Solution>
        implements Searcher<Problem, Solution> {
    private HashSet<State<Problem>> closedSet = new HashSet<State<Problem>>();

    @Override
    public Solution search(Searchable<Problem> searchable) {
        try {
            openList = new PriorityQueue<State<Problem>>(new CostComparator());
            addToOpenList(searchable.getInitialState(), null);

            while (openList.size() > 0) {
                State<Problem> currentNode = popOpenList(); // Remove the best node from openList.
                closedSet.add(currentNode); // so we won't check the current Node again.

                if (searchable.isGoalState(currentNode))
                    return backTrace(currentNode, searchable.getInitialState()); // back traces
                // through the
                // parents.
                List<State<Problem>> successors = searchable.getAllPossibleStates(currentNode);
                // Remove came from neighbor from successors list. (already in closedList)
                successors.removeIf(state -> (state.getState().equals(currentNode.getCameFrom())));

                for (State<Problem> state : successors) {
                    boolean inOpenListFlag = openList.contains(state), inClosedListFlag = closedSet.contains(state);
                    if (!inClosedListFlag && !inOpenListFlag) {
                        state.setCameFrom(currentNode.getState());
                        addToOpenList(state, currentNode); // adds to openList and updates state's cost to accumalted
                        // cost.
                    } else {
                        if (isPathCheapest(state, currentNode, inOpenListFlag)) { // if path is cheaper (from
                            // currentNode to
                            // state)
                            // if not in open List, add the state node.
                            if (!openList.contains(state))
                                addToOpenList(state, currentNode);
                            else {// adjust the state node's priority (dequeue and insert again)
                                openList.remove(state);
                                addToOpenList(state, currentNode);
                            }
                        }
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

    private boolean isPathCheapest(State<Problem> toState, State<Problem> fromState, boolean inOpenListFlag) {
        try {
            Iterator<State<Problem>> itr = openList.iterator();
            boolean found = false;
            State<Problem> currentState = null;
            while (itr.hasNext() && !found) {
                currentState = itr.next();
                if (currentState.equals(toState))
                    found = true;
            }
            if (currentState != null)
                if (fromState.getCost() + toState.getCost() < currentState.getCost()) {
                    return true;
                }

        } catch (Exception ex) {
            System.out.println("null pointer exception" + ex);
            ex.printStackTrace();
        }
        return false;

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
            nodeIndex = tempList.indexOf(new State(goalPath.getFirst()));
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