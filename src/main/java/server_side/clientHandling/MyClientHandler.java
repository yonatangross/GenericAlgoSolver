package server_side.clientHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import server_side.SearcherSolver;
import server_side.algorithms.Searcher;
import server_side.cacheManager.CacheManager;
import server_side.cacheManager.FileCacheManager;

public class MyClientHandler implements ClientHandler {
    private SearcherSolver<String, String> solver;
    private CacheManager<String, String> cacheManager = new FileCacheManager<String, String>();
    private Scanner in;
    private PrintWriter out;

    // receives an Searcher Interface.
    public MyClientHandler(Searcher<String, String> searcher) {
        this.solver = new SearcherSolver<String, String>(searcher);
    }

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException {
        String endOfLineFlag = "\\n";
        String solution = null;
        in = new Scanner(new BufferedReader(new InputStreamReader(inputStream)));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));

        try {

            String problem = problemReader(endOfLineFlag);

            if (cacheManager.checkSolutionExistence(problem)) {
                try {
                    solution = cacheManager.getSolution(problem);
                } catch (Exception ex) {
                    System.out.println("Exception occured while fetching solution from cache.");
                    ex.printStackTrace();
                }
            } else {

                solution = solver.solve(problem);
                cacheManager.saveSolution(problem, solution);
            }
            out.println(solution);
            out.flush();
        } finally {
            in.close();
            out.close();
        }
    }

    private String problemReader(String endOfLineFlag) {
        StringBuilder sb = new StringBuilder();
        while (!in.hasNext("end")) {
            sb.append(in.next() + "\n");
        }
        for (int i = 0; i < 3; i++) {
            sb.append(in.next() + "\n");
        }
        String problem = sb.toString();
        return problem;
    }
}
