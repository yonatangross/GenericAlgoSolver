package server_side;

public class State<Problem> {
    private Problem state;
    private double cost;
    private Problem cameFrom;

    public State(Problem state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }

    public Problem getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Problem getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(Problem cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public String toString() {
        return "[state:" + state + ", cost:" + cost + ", cameFrom:" + cameFrom + "]\n";
    }

}
