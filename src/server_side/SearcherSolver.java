package server_side;

import server_side.algorithms.Searcher;
import server_side.dataStructures.Point;
import server_side.dataStructures.adjacencyMatrix;
import server_side.problems.Direction;
import server_side.problems.MatrixMaze;
import server_side.problems.Searchable;

public class SearcherSolver<Problem, Solution> implements Solver<Problem, Solution> {
    private Searcher<Problem, Solution> searcher;
    private Searchable<Problem> searchable;

    public SearcherSolver(Searcher<Problem, Solution> searcher) {
        this.searcher = searcher;

    }

    @Override
    public Solution solve(Problem problem) {
        interpertProblem(problem);
        Solution solution = searcher.search(searchable);
        try {
            Direction[] pathDirections = solutionToDirections(solution);
            StringBuilder sb = new StringBuilder();
            for (Direction direction : pathDirections) {
                sb.append(direction.toString() + ",");
            }
            sb.delete(sb.length() - 1, sb.length());

            return (Solution) sb.toString();
        } catch (Exception ex) {
            // TODO: handle exception
            System.out.println("Error occured while trying to solve problem" + ex);
            ex.printStackTrace();
        }
        return solution;

    }

	/*	input:
	actual input:
	10,15,2,8\r
	20,4,14,5\r
	1,4,100,3\r
	end\r
	0,0\r
	3,3\r
	
	cleaned input:
	matrix:
	10,15,2,8
	20,4,14,5
	1,4,100,3
	start point:
	0,2
	end point:
	2,1
	*/

    private Direction[] solutionToDirections(Solution solution) {
        Direction[] pathDirections = null;
        String solutionString = solution.toString();
        String[] pointsStrArr = solutionString.replaceAll("\\[|\\]", "").split(", |, ");
        Point[] pathPoints = new Point[pointsStrArr.length];
        pathDirections = new Direction[pathPoints.length - 1];
        for (int i = 0; i < pointsStrArr.length; i++) {
            String pointStr = pointsStrArr[i];
            pathPoints[i] = Point.strToPoint(pointStr);
        }

        int firstPointChangedValue, secondPointChangedValue;
        for (int i = 0; i < pathPoints.length - 1; i++) {
            if (pathPoints[i].x == pathPoints[i + 1].x) {
                // y change, up or down
                firstPointChangedValue = pathPoints[i].y;
                secondPointChangedValue = pathPoints[i + 1].y;
                if (firstPointChangedValue < secondPointChangedValue) {
                    // down
                    pathDirections[i] = Direction.DOWN;
                } else {
                    // up
                    pathDirections[i] = Direction.UP;

                }
            } else {
                // x change, left or right
                firstPointChangedValue = pathPoints[i].x;
                secondPointChangedValue = pathPoints[i + 1].x;
                if (firstPointChangedValue > secondPointChangedValue) {
                    // left
                    pathDirections[i] = Direction.LEFT;
                } else {
                    // right
                    pathDirections[i] = Direction.RIGHT;
                }
            }
        }
        return pathDirections;

    }

    private Searchable<Problem> interpertProblem(Problem problem) {
        String problemString = problem.toString();
        Point initialState = null, goalState = null;
        adjacencyMatrix matrix = null;

        // Split Matrix and Points by delimeter.
        String[] problemData = problemString.split("end\n");

        // Read Matrix number of rows and columns.
        long numOfRows = problemData[0].chars().filter(ch -> ch == '\n').count();
        long numOfCols = (long) Math.sqrt(problemData[0].chars().filter(ch -> ch == ',').count()) + 1;
        double[][] matrixValues = new double[(int) numOfRows][(int) numOfCols];

        // Input Values to matrix.
        String[] matrixValuesStringArr = problemData[0].split(",|\n");
        int ind = 0;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {

                matrixValues[i][j] = Double.parseDouble(matrixValuesStringArr[ind]);
                ind++;
            }
        }

        matrix = new adjacencyMatrix(matrixValues, (int) numOfRows, (int) numOfCols);

        // Read Points
        String[] problemPoints = problemData[1].split("\n|,");
        initialState = new Point(Integer.parseInt(problemPoints[0]), Integer.parseInt(problemPoints[1]));
        goalState = new Point(Integer.parseInt(problemPoints[2]), Integer.parseInt(problemPoints[3]));

        // create the searchable MatrixMaze
        searchable = new MatrixMaze<Problem>(initialState, goalState, matrix);

        return searchable;

    }

}
