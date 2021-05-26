package LeoMitinskyi;

import LeoMitinskyi.types.Color;
import LeoMitinskyi.types.Country;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static LeoMitinskyi.IsDigit.*;

public class IOManager {
    private final BufferedOutputStream bufferedOutputStream;
    private final ServerLoop serverLoop;
    public IOManager(ServerLoop serverLoop) {
        this.serverLoop = serverLoop;
        bufferedOutputStream = new BufferedOutputStream(System.out);
    }

    public void write(String s) {
        try {
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String s) {
        write(s + "\n");
    }

    public String read() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    public String StringReader(String question) throws IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty()) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return value;
    }

    public Integer IntegerReader(String question) throws NumberFormatException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || !isInteger(value) || !(Integer.parseInt(value) > 0)) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Integer.valueOf(value);
    }

    public Float FloatReader(String question, Float maxValue) throws NumberFormatException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || !isFloat(value) || (Float.parseFloat(value) > maxValue)) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Float.valueOf(value);
    }

    public Float FloatReader(String question) throws NumberFormatException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || !isFloat(value)) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Float.valueOf(value);
    }

    public Double DoubleReader(String question) throws NumberFormatException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || !isDouble(value)) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Double.valueOf(value);
    }

    public Long LongReader(String question) throws NumberFormatException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || !isLong(value)) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Long.valueOf(value);
    }


    public Color ColorReader(String question) throws IllegalArgumentException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || (!value.equals("GREEN") && !value.equals("RED") && !value.equals("BROWN") && !value.equals("YELLOW") && !value.equals("BLACK"))) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Color.valueOf(value);
    }

    public Country CountryReader(String question) throws IllegalArgumentException, IOException, ClassNotFoundException {
        serverLoop.sendMessage(serverLoop.getByteBuffer(),question,serverLoop.getChannel(),serverLoop.getRemoteAddr());
        String value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        while (value.trim().isEmpty() || (!value.equals("USA") && !value.equals("SPAIN") && !value.equals("INDIA") && !value.equals("ITALY") && !value.equals("JAPAN"))) {
            serverLoop.sendMessage(serverLoop.getByteBuffer(),"Invalid value" + "\n" + question, serverLoop.getChannel(), serverLoop.getRemoteAddr());
            value = serverLoop.receiveMessage(serverLoop.getByteBuffer());
        }
        return Country.valueOf(value);
    }
}
