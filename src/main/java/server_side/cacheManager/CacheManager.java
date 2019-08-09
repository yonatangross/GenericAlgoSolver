package server_side.cacheManager;

import java.io.IOException;

public interface CacheManager<Problem, Solution> {

    public boolean checkSolutionExistence(Problem problem);

    public Solution getSolution(Problem problem) throws IOException;

    public void saveSolution(Problem problem, Solution solution);
}
