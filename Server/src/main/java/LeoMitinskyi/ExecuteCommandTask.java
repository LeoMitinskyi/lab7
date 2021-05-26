package LeoMitinskyi;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class ExecuteCommandTask extends RecursiveTask<String> {

    long from = 0;
    long to = 0;
    Engine engine;
    String commandName;

    public ExecuteCommandTask(Engine engine, String commandName){
        this.engine = engine;
        this.commandName = commandName;
    }

    public ExecuteCommandTask(long from, long to, Engine engine, String commandName){
        this.from = from;
        this.to = to;
        this.engine = engine;
        this.commandName = commandName;
    }

    @Override
    protected String compute() {
        if (to - from <= (to / Runtime.getRuntime().availableProcessors())) {
            try { ;
                return engine.readAndExecuteCommand(commandName);
            }catch (IOException | ClassNotFoundException exception){
                exception.printStackTrace();
            }
        } else {
            long middle = (to + from) / 2;
            ExecuteCommandTask task1 = new ExecuteCommandTask(from, middle, engine, commandName);
            task1.fork();
            ExecuteCommandTask task2 = new ExecuteCommandTask(middle + 1, to, engine, commandName);
            task2.fork();
            task1.join();
            task2.join();
        }
        return null;
    }
}
