package test;

import server_side.algorithms.AStar;
import server_side.clientHandling.MyClientHandler;
import server_side.server.MySerialServer;

public class TestSetter {

    static MySerialServer mySerialServer;

    public static void runServer(int port) {
        // put the code here that runs your server
        mySerialServer = new MySerialServer(port); // initialize
        mySerialServer.start(new MyClientHandler(new AStar<String, String>()));

    }

    public static void stopServer() {
        mySerialServer.stop();
    }
}
