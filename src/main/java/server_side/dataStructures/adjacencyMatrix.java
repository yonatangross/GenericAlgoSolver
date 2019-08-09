package server_side.dataStructures;

public class adjacencyMatrix {
    private double[][] matrix; // weighted Graph represented as Adjacency Matrix.
    private int numOfRows;
    private int numOfCols;

    public adjacencyMatrix(double[][] matrix, int numOfRows, int numOfCols) {
        this.matrix = matrix;
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
    }

    public void setWeight(int row, int col, int weight) {
        this.matrix[row][col] = weight;
    }

    public double getWeight(int row, int col) {
        return this.matrix[row][col];
    }

    public double getWeight(Point point) {
        return getWeight(point.y, point.x);
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumOfCols() {
        return numOfCols;
    }

    @Override
    public String toString() {
        StringBuffer results = new StringBuffer();
        results.append("Rows: " + numOfRows + " Cols: " + numOfCols + "\n");
        for (int row = 0; row < numOfRows; row++) {
            results.append("[");
            for (int col = 0; col < numOfCols; col++) {
                if (col == numOfCols - 1) {
                    results.append(matrix[row][col]);
                } else {
                    results.append(matrix[row][col] + ", ");
                }
            }
            results.append("]");
            results.append("\r\n");
        }
        return results.toString();
    }
}
