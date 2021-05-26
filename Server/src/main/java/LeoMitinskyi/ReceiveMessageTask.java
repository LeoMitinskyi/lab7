package LeoMitinskyi;

import java.nio.ByteBuffer;
import java.util.concurrent.Callable;

public class ReceiveMessageTask implements Callable<String> {

    ServerLoop serverLoop;
    ByteBuffer buffer;

    public ReceiveMessageTask(ServerLoop serverLoop, ByteBuffer buffer){
        this.serverLoop = serverLoop;
        this.buffer = buffer;
    }

    @Override
    public String call() throws Exception {
        return serverLoop.receiveMessage(buffer);
    }
}
