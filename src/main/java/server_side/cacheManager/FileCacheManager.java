package server_side.cacheManager;

import java.io.*;
import java.util.HashMap;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {

    private HashMap<Problem, Integer> hashMap = new HashMap<>();

    @Override
    public boolean checkSolutionExistence(Problem problem) {
        return hashMap.containsKey(problem);
    }

    @Override
    public Solution getSolution(Problem problem) throws IOException {
        ///TODO: read from file in hashcode
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("solutions/" + hashMap.get(problem).toString() + ".txt"));
        } catch (FileNotFoundException e) {
            System.out.println("file of " + problem.toString() + " is not found.");
            e.printStackTrace();
        }

        return (Solution) in.readLine();
    }

    @Override
    public void saveSolution(Problem problem, Solution solution) { // hashmap<key: problem, value: problem Hashcode>
        Integer problemCode = problem.hashCode();
        hashMap.put(problem, problemCode);
        writeToFile(problemCode, solution);

    }

    private void writeToFile(Integer problemCode, Solution solution) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("solutions/" + problemCode.toString() + ".txt"));

            out.write(solution.toString());
        } catch (IOException e) {
            System.out.println("Exception while writing to file.");
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (NullPointerException e) {
                System.out.println("NullPointerException while closing the file.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Exception while closing the file.");
                e.printStackTrace();
            }
        }

    }

}
