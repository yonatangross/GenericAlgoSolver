package server_side.algorithms;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import server_side.State;
import server_side.dataStructures.Point;
import server_side.problems.Searchable;

public abstract class CommonSearcher<Problem, Solution> implements Searcher<Problem, Solution> {
    protected Queue<State<Problem>> openList; // contains the states with the accumulated cost.
    private int evaluatedNodes;

    protected LinkedList<Problem> goalPath = new LinkedList<>();

    class AStarComparator implements Comparator<State<Problem>> {
        private Point endPoint = null;
        private int firstNodeHeuristicValue, secondNodelHeuristicValue;

        public AStarComparator(Searchable<Problem> searchable) {
            Problem goalState = searchable.getGoalState().getState();
            this.endPoint = Point.strToPoint(goalState.toString());
        }

        private int computeHeuristicValue(Point currentPoint) { // Using Manahattan Distance equation.
            return Math.abs(currentPoint.x - endPoint.x) + Math.abs(currentPoint.y - endPoint.y);
        }

        public int compare(State<Problem> s1, State<Problem> s2) {
            /* computes for each state: f(x) == g(x)+h(x)
             * g(x): the accumulated cost from startPoint to x.
             * h(x): sum of absolute values of differences in the goal�s x and y coordinates
             * 		 and the current cell�s x and y coordinates respectively.
             */

            firstNodeHeuristicValue = computeHeuristicValue((Point) s1.getState());
            secondNodelHeuristicValue = computeHeuristicValue((Point) s2.getState());
			/*			System.out.println("State: " + s1.getState().toString()+" cost is "+s1.getCost()+" and heuristic value is "+firstNodeHeuristicValue);
						System.out.println("State: " + s2.getState().toString()+" cost is "+s2.getCost()+" and heuristic value is "+secondNodelHeuristicValue);
			*/
            if (s1.getCost() + firstNodeHeuristicValue > s2.getCost() + secondNodelHeuristicValue)
                return 1;
            else if (s1.getCost() + firstNodeHeuristicValue < s2.getCost() + secondNodelHeuristicValue)
                return -1;
            return 0;
        }
    }

    class DistanceComparator implements Comparator<State<Problem>> {
        private Point endPoint = null;
        private int firstNodeHeuristicValue, secondNodelHeuristicValue;

        public DistanceComparator(Searchable<Problem> searchable) {
            Problem goalState = searchable.getGoalState().getState();
            this.endPoint = Point.strToPoint(goalState.toString());
        }

        private int computeHeuristicValue(Point currentPoint) { // Using Manahattan Distance equation.
            return Math.abs(currentPoint.x - endPoint.x) + Math.abs(currentPoint.y - endPoint.y);
        }

        public int compare(State<Problem> s1, State<Problem> s2) {
            firstNodeHeuristicValue = computeHeuristicValue((Point) s1.getState());
            secondNodelHeuristicValue = computeHeuristicValue((Point) s2.getState());

            if (firstNodeHeuristicValue > secondNodelHeuristicValue)
                return 1;
            else if (firstNodeHeuristicValue < secondNodelHeuristicValue)
                return -1;
            return 0;
        }
    }

    class CostComparator implements Comparator<State<Problem>> {

        // Overriding compare() method of Comparator for descending order of cost
        public int compare(State<Problem> s1, State<Problem> s2) {
            if (s1.getCost() > s2.getCost())
                return 1;
            else if (s1.getCost() < s2.getCost())
                return -1;
            return 0;
        }
    }

    public CommonSearcher() {
        evaluatedNodes = 0;
    }

    protected State<Problem> popOpenList() {
        evaluatedNodes++;
        return openList.poll();
    }

    @Override
    public abstract Solution search(Searchable<Problem> searchable);

    @Override
    public int getNumberOfNodesEvaluated() {
        // TODO: need to change, every time called evaluatedNodes should be reseted.
        int result = evaluatedNodes;
        evaluatedNodes = 0;
        return result;
    }

}
