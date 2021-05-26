package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(Engine engine, ServerLoop serverLoop) {
        super(engine, serverLoop);
    }

    @Override
    public String execute() throws IOException, ClassNotFoundException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("text.txt"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String str;
        ArrayList<String> list = new ArrayList<>();
        while((str = reader.readLine()) != null ){
            if(!str.isEmpty()){
                list.add(str);
            }}
        String[] commands = list.toArray(new String[0]);
        return engine.run(commands);
    }

    @Override
    public String getDescription() {
        return ":считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
