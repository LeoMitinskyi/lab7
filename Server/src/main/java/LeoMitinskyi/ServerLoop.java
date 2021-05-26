package LeoMitinskyi;

import LeoMitinskyi.Commands.SaveCommand;
import LeoMitinskyi.Database.DatabaseManager;
import LeoMitinskyi.Database.User;
import java.io.*;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerLoop {
    protected static ByteBuffer buffer;
    protected static InetSocketAddress iAddr;
    protected static SocketAddress remoteAddr;
    protected static int port;

    Engine engine;
    DatagramChannel server = DatagramChannel.open();
    IOManager ioManager = new IOManager(this);
    ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    ExecutorService executorService = Executors.newCachedThreadPool();

    protected ServerLoop(int port) throws IOException{
        ServerLoop.port = port;
    }

    public static volatile boolean flag = false;
    protected void StartLoop() throws IOException, SQLException {
        DatabaseManager.connect();
        //DatabaseManager.dropPeople();
        //DatabaseManager.CreateTable();
        iAddr = new InetSocketAddress(port);
        DatabaseManager.loadPeople();
        boolean StopFlag = true;
        System.out.println("Start Server!");
        server.bind(iAddr);
        buffer = ByteBuffer.allocate(65536);
        Runnable userInput = () -> {
            try {
                while (true) {
                    String input = ioManager.read();
                    if(input.equals("exit")){
                        SafeExit();
                    }else if(input.equals("save")){
                        SaveCommand saveCommand = new SaveCommand(engine,this);
                        ioManager.writeLine(saveCommand.execute());
                    }
                    else{
                        ioManager.writeLine("Сервер не может принять такую команду");
                    }
                }
            }catch (Exception exception){
                ioManager.writeLine("Что-то пошло не так!");
            }
        };
        Thread thread = new Thread(userInput);
        thread.start();

        Runnable userAuth = () -> {
            try {
                while (!flag) {
                    user = authentication();
                    flag = user.getAuth();
                }
            } catch (ExecutionException | InterruptedException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        };
        Thread thread2 = new Thread(userAuth);
        thread2.start();
        while (StopFlag) {
            if (flag){
                while (StopFlag){
                    StopFlag = processingClientRequest();
                }
            }

        }
    }

    public static User user;
    private boolean processingClientRequest(){
        String response = "";
        try {
                    do {
                        engine = new Engine(this);
                        String commandName = executorService.submit(new ReceiveMessageTask(this, buffer)).get();
                        ioManager.writeLine("Получено сообщение '" + commandName + "'");
                        response = forkJoinPool.invoke(new ExecuteCommandTask(engine, commandName));
                        forkJoinPool.invoke(new SendMessageTask(this, response, buffer, server, remoteAddr));
                    } while (!response.equals("exit"));
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при работе с клиентом!");
        }
        return true;
    }

    public static boolean stopFlag = false;
    private User authentication() throws ExecutionException, InterruptedException, NoSuchAlgorithmException {
        while (!stopFlag) {
                String login = executorService.submit(new ReceiveMessageTask(this,buffer)).get();
                System.out.println(login);
                if (DatabaseManager.users.containsKey(login)) {
                    user = DatabaseManager.users.get(login);
                    String cryptedPassword = passwordReader(user);
                    forkJoinPool.invoke(new SendMessageTask(this,"Success",buffer,server,remoteAddr));
                    return user = new User(login, cryptedPassword, true);
                } else {
                    forkJoinPool.invoke(new SendMessageTask(this,"Enter password",buffer,server,remoteAddr));
                    String password = executorService.submit(new ReceiveMessageTask(this,buffer)).get();
                    String cryptedPassword = cryptPassword(password);
                    user = new User(login, cryptedPassword, true);
                    DatabaseManager.users.put(login, user);
                    forkJoinPool.invoke(new SendMessageTask(this,"A new user have been created",buffer,server,remoteAddr));
                    stopFlag = true;
                    break;
                }
        }
        return user;
    }

    public String passwordReader(User user) throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        forkJoinPool.invoke(new SendMessageTask(this,"Enter password",buffer,server,remoteAddr));
        String password = executorService.submit(new ReceiveMessageTask(this,buffer)).get();
        String cryptedPassword = cryptPassword(password);
        while (!user.getPassword().equals(cryptedPassword)){
            forkJoinPool.invoke(new SendMessageTask(this, "Enter password", buffer, server, remoteAddr));
            password = executorService.submit(new ReceiveMessageTask(this,buffer)).get();
            cryptedPassword = cryptPassword(password);
        }
        return cryptedPassword;
    }

    public String cryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] cryptedPassword = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, cryptedPassword);
        return no.toString(16);
    }

    public User getUser(){
        return user;
    }

    private void SafeExit(){
        SaveCommand saveCommand = new SaveCommand(engine,this);
        saveCommand.execute();
        DatabaseManager.disconnect();
        System.exit(0);
    }

    Lock lock1 = new ReentrantLock();
    public void sendMessage(ByteBuffer buffer, String message,DatagramChannel server, SocketAddress remoteAddr) throws IOException {
        lock1.lock();
        Request request = new Request(message);
        buffer.clear();
        buffer.put(serialize(request));
        buffer.flip();
        server.send(buffer, remoteAddr);
        buffer.clear();
        lock1.unlock();
    }

    Lock lock2 = new ReentrantLock();
    public String receiveMessage(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        lock2.lock();
        remoteAddr = server.receive(buffer);
        buffer.flip();
        Request request = deserialize();
        lock2.unlock();
        return request.getRequestBody();
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

    private Request deserialize() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request response = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        buffer.clear();
        return response;
    }

    public SocketAddress getRemoteAddr(){
        return remoteAddr;
    }

    public ByteBuffer getByteBuffer(){
        return buffer;
    }

    public DatagramChannel getChannel(){
        return server;
    }

    public IOManager getIOManager() {
        return ioManager;
    }
}
