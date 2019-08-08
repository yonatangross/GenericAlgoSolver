package server_side.server;

import server_side.clientHandling.ClientHandler;

public interface Server {
    public void open(int port, ClientHandler clientHandler);

    public void stop();
}
