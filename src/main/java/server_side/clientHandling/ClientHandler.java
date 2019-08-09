package server_side.clientHandling;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException;
}

