package LeoMitinskyi;

import java.io.*;
import java.net.*;

public class ClientLoop {
    private int port;
    private final DatagramSocket socket = new DatagramSocket(port);
    private InetAddress address;
    IOManager ioManager = new IOManager();
    protected ClientLoop(int port) throws SocketException {
        this.port = port;
    }

    protected void startLoop() throws InterruptedException, IOException, ClassNotFoundException {
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(),port);
        authentication(socketAddress);
        while (true){
            try {
                String message = ioManager.StringReader();
                if (message.equals("exit")) {
                    exit();
                }
                sendMessage(message, socketAddress);
                ioManager.writeLine(receiveMessage());
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
                ioManager.writeLine("Что-то пошло не так");
                System.exit(0);
            }
        }
    }

    private void authentication(InetSocketAddress socketAddress) throws IOException, ClassNotFoundException, InterruptedException {
        ioManager.writeLine("Enter login");
        String login = ioManager.StringReader();
        sendMessage(login, socketAddress);
        String response = receiveMessage();
        ioManager.writeLine(response);
    }

    public void exit(){
        ioManager.writeLine("Прекращение работы...");
        ioManager.writeLine("Работа завершена!");
        System.exit(0);
    }

    public void sendMessage(String message, InetSocketAddress socketAddress) throws IOException {
        Request request = new Request(message);
        DatagramPacket datagramPacket = new DatagramPacket(serialize(request),serialize(request).length, socketAddress);
        socket.send(datagramPacket);
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private String receiveMessage() throws IOException, ClassNotFoundException, InterruptedException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        ConnectionChecker connectionChecker = new ConnectionChecker();
        new Thread(connectionChecker).start();
        socket.receive(getPacket);
        connectionChecker.disable();
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket).getRequestBody();
    }

    private Request deserialize(DatagramPacket getPacket) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }
}
