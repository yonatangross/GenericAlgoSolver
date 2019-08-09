package server_side.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import server_side.State;
import server_side.dataStructures.Point;
import server_side.problems.Searchable;

public class AStar<Problem, Solution> extends CommonSearcher<Problem, Solution> implements Searcher<Problem, Solution> {
	private Searchable<Problem> searchable = null;
	private HashSet<State<Problem>> closedSet = new HashSet<>();

	@Override
	public Solution search(Searchable<Problem> searchable) {
		this.searchable = searchable;
		State<Problem> currentNode;

		try {
			openList = new PriorityQueue<>(new AStarComparator(searchable));
			openList.add(searchable.getInitialState());
			while (!openList.isEmpty()) {
				currentNode = popOpenList();
				closedSet.add(currentNode);

				if (searchable.isGoalState(currentNode))
					return backTrace(currentNode, searchable.getInitialState());
				List<State<Problem>> successors = searchable.getAllPossibleStates(currentNode);

				for (State<Problem> state : successors) {
					if (closedSet.contains(state) || !isNeighborCloserToStart(currentNode, state))
						continue;
					if (!openList.contains(state) || isNeighborCloserToStart(currentNode, state)) {
						state.setCameFrom(currentNode.getState());
						// maybe need to update g and f score for nodes here? need to create
						// data structure for nodes as well? and for cameFromLinkedList

						if (!openList.contains(state))
							addToOpenList(state, currentNode);
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

	private boolean isNeighborCloserToStart(State<Problem> currentNode, State<Problem> neighborNode) {
		State<Problem> initState = searchable.getInitialState();
		double currentNodeGScore = computeGScore(initState, currentNode) + 1;
		double neighborGScore = computeGScore(initState, neighborNode);
		if (currentNodeGScore >= neighborGScore)
			return true;
		return false;
	}

	private int computeGScore(State<Problem> initState, State<Problem> currentNode) {
		Point startPoint = Point.strToPoint(initState.getState().toString());
		Point currPoint = Point.strToPoint(currentNode.getState().toString());

		return Math.abs(startPoint.x - currPoint.x) + Math.abs(startPoint.y - currPoint.y);
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
