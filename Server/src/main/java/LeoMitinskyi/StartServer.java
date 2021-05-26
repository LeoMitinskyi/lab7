package LeoMitinskyi;

import java.io.IOException;
import java.sql.SQLException;

public class StartServer {
    public static final int port = 5688;
    public static void main(String[] args) throws IOException, SQLException {
            ServerLoop serverLoop = new ServerLoop(port);
            serverLoop.StartLoop();
    }
}
