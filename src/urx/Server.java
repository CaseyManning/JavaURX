package urx;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * A simple TCP server. When a client connects, it sends the client the current
 * datetime, then closes the connection. This is arguably the simplest server
 * you can write. Beware though that a client has to be completely served its
 * date before the server will be able to handle another client.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(30002)) {
            System.out.println("The date server is running...");
            var socket = listener.accept();
            while(true) {
            	System.out.println();
            }
        }
    }
}