package LeoMitinskyi;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveTask;

public class SendMessageTask extends RecursiveTask<String> {

    long from = 0;
    long to = 0;
    ServerLoop serverLoop;
    ByteBuffer byteBuffer;
    String message;
    DatagramChannel server;
    SocketAddress remoteAddr;

    public SendMessageTask(ServerLoop serverLoop, String message,ByteBuffer buffer, DatagramChannel server, SocketAddress remoteAddr){
        this.serverLoop = serverLoop;
        this.message = message;
        this.server = server;
        this.remoteAddr = remoteAddr;
        this.byteBuffer = buffer;
    }

    public SendMessageTask(long from, long to, ServerLoop serverLoop, String message, ByteBuffer buffer, DatagramChannel server, SocketAddress remoteAddr) {
        this.from = from;
        this.to = to;
        this.serverLoop = serverLoop;
        this.message = message;
        this.server = server;
        this.remoteAddr = remoteAddr;
        this.byteBuffer =buffer;
    }

    @Override
    protected String compute() {
        if (to - from <= (to / Runtime.getRuntime().availableProcessors())) {
            try {
                serverLoop.sendMessage(byteBuffer,message,server,remoteAddr);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            long middle = (to + from) / 2;
            SendMessageTask task1 = new SendMessageTask(from, middle, serverLoop, message, byteBuffer, server, remoteAddr);
            task1.fork();
            SendMessageTask task2 = new SendMessageTask(middle + 1, to, serverLoop, message, byteBuffer, server, remoteAddr);
            task2.fork();
            task1.join();
            task2.join();
        }
        return null;
    }
}
