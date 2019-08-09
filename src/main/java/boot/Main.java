package boot;

import server_side.clientHandling.MyTestClientHandler;
import server_side.server.MySerialServer;

public class Main {

    public static void main(String[] args) {
        MyTestClientHandler mtch = new MyTestClientHandler();
        MySerialServer mss = new MySerialServer(61234);
        // start server with port.
        mss.start(mtch);
    }

}
