package server_side.problems;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    @Override
    public String toString() {
        switch (this) {
            case DOWN:
                return "Down";
            case LEFT:
                return "Left";

            case RIGHT:
                return "Right";

            case UP:
                return "Up";

            default:
                throw new IllegalArgumentException();
        }
    }

}
