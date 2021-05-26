package LeoMitinskyi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOManager {
    private final BufferedOutputStream bufferedOutputStream;

    public IOManager(){
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

    public String StringReader() throws IOException {
        String value = read();
        while (value.length() > 50) {
            writeLine("Слишком большое значение!");
            writeLine("Попробуйте ещё раз.");
            value = read();
        }
        return value;
    }
}
