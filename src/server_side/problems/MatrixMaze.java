package server_side.problems;

import java.util.ArrayList;
import java.util.List;

import server_side.State;
import server_side.dataStructures.Point;
import server_side.dataStructures.adjacencyMatrix;

public class MatrixMaze<Problem> implements Searchable<Problem> {
    private Point startPoint = null;
    private Point endPoint = null;
    private adjacencyMatrix matrix = null;
    private State<Point> initialState = null;
    private State<Point> goalState = null;

    public MatrixMaze(Point startPoint, Point endPoint, adjacencyMatrix matrix) {
        this.matrix = matrix;

        // init initialState.
        this.startPoint = startPoint;
        this.initialState = new State<Point>(startPoint);
        initialState.setCost(matrix.getWeight(startPoint));
        initialState.setCameFrom(null);

        // init goalState.
        this.endPoint = endPoint;
        this.goalState = new State<Point>(endPoint);
        goalState.setCost(matrix.getWeight(endPoint));
        goalState.setCameFrom(null);

    }

    @Override
    public List<State<Problem>> getAllPossibleStates(State<Problem> currentState) {
        Point currentPoint = (Point) currentState.getState();
        State<Point> newState;

        List<State<Problem>> possibleStates = new ArrayList<State<Problem>>();
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            switch (direction) {
                case DOWN:
                    // DOWN (x,y+1)=>  (y+1 <= numOfRows-1) => new point is inside the matrix
                    if (currentPoint.y + 1 <= matrix.getNumOfRows() - 1) {
                        newState = new State<Point>(new Point(currentPoint.x, currentPoint.y + 1));
                        addStateToList(currentPoint, newState, possibleStates);
                    }
                    break;
                case LEFT:
                    // LEFT (x-1,y)=> (x-1 >= 0) => new point is inside the matrix
                    if (currentPoint.x - 1 >= 0) {
                        newState = new State<Point>(new Point(currentPoint.x - 1, currentPoint.y));
                        addStateToList(currentPoint, newState, possibleStates);
                    }
                    break;
                case RIGHT:
                    // RIGHT (x+1,y)=> (x+1 <= numOfCols-1) => new point is inside the matrix
                    if (currentPoint.x + 1 <= matrix.getNumOfCols() - 1) {
                        newState = new State<Point>(new Point(currentPoint.x + 1, currentPoint.y));
                        addStateToList(currentPoint, newState, possibleStates);
                    }
                    break;
                case UP:
                    // UP (x,y-1)=> (y-1 >= 0) => new point is inside the matrix
                    if (currentPoint.y - 1 >= 0) {
                        newState = new State<Point>(new Point(currentPoint.x, currentPoint.y - 1));
                        addStateToList(currentPoint, newState, possibleStates);
                    }
                    break;
                default:
                    break;
            }
        }
        return possibleStates;

    }

    private void addStateToList(Point currentPoint, State<Point> newState, List<State<Problem>> possibleStates) {
        newState.setCost(matrix.getWeight(newState.getState()));
        possibleStates.add((State<Problem>) newState);
    }

    @Override
    public State<Problem> getInitialState() {
        return (State<Problem>) initialState;
    }

    @Override
    public boolean isGoalState(State<Problem> state) {
        return goalState.equals(state);
    }

    @Override
    public State<Problem> getGoalState() {
        return (State<Problem>) goalState;
    }
}
