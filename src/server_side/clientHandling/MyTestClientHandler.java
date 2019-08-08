package server_side.clientHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import server_side.Solver;
import server_side.cacheManager.CacheManager;
import server_side.cacheManager.FileCacheManager;

public class MyTestClientHandler implements ClientHandler {

    private Solver<String, String> solver = inputString -> new StringBuilder(inputString).reverse().toString();
    private CacheManager<String, String> cacheManager = new FileCacheManager<>();
    private BufferedReader in;
    private PrintWriter out;

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException {
        String eof = "end";
        String solution;
        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        try {
            String problem;
            while (!(problem = in.readLine()).equalsIgnoreCase(eof)) {
                if (cacheManager.checkSolutionExistence(problem)) {
                    solution = cacheManager.getSolution(problem);
                } else {
                    solution = solver.solve(problem);
                    cacheManager.saveSolution(problem, solution);
                }

                out.println(solution);
                out.flush();
                System.out.println("solution sent, waiting for client...");
            }
        } catch (IOException e) {
            System.out.println(e.getClass() + " " + e.getMessage());
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
        }
    }
}
