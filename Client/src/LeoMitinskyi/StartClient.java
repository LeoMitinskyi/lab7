package LeoMitinskyi;

import java.io.IOException;

public class StartClient {
    public static final int port = 5688;
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ClientLoop clientLoop = new ClientLoop(port);
        clientLoop.startLoop();
    }
}
