package server_side.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import server_side.clientHandling.ClientHandler;

public class MySerialServer implements Server {
    private static final int timeOut = 1000;
    private int port;
    private volatile boolean stop;
    private ServerSocket server;

    public MySerialServer(int port) {
        this.port = port;
        stop = false;
    }

    @Override
    public void open(int port, ClientHandler clientHandler) {
    }

    @Override
    public void stop() {
        stop = true;

        try {
            server.close();
        } catch (Exception ex) {
            System.out.println("Exception occurred while closing server: " + ex);
            ex.printStackTrace();
        }
    }

    public void start(ClientHandler clientHandler) {
        try {
            new Thread(() -> {
                try {
                    runServer(clientHandler);
                } catch (Exception e) {
                    System.out.println("sysos");
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            System.out.println("from start method.");
            System.out.println(e.getClass() + " " + e.getMessage());

            e.printStackTrace();
        }
    }

    public void runServer(ClientHandler clientHandler) throws Exception {
        server = new ServerSocket(port);
        server.setSoTimeout(timeOut);
        while (!stop) {
            try {
                Socket clientSocket = server.accept(); // blocking call
                try {
                    clientHandler.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
                    clientSocket.close();

                } catch (IOException e) {
                    System.out.println(e.getClass() + " " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SocketException e) {
                if (!stop) {
                    System.out.println("brute stop to server. SocketTimeoutException: " + e);
                    e.printStackTrace();
                }
            }
        }
    }

}