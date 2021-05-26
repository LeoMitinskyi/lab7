package LeoMitinskyi;

import LeoMitinskyi.Commands.*;
import LeoMitinskyi.types.People;

import java.io.IOException;
import java.util.*;

public class Engine {
    private final Map<String, Command> commands = new HashMap<>();

    private final List<String> history = new LinkedList<>();
    private final People collection = new People();

    public Engine(ServerLoop serverLoop){
        registerCommand(new HelpCommand(this,serverLoop));
        registerCommand(new InfoCommand(this,serverLoop));
        registerCommand(new ShowCommand(this,serverLoop));
        registerCommand(new AddCommand(this,serverLoop));
        registerCommand(new UpdateCommand(this,serverLoop));
        registerCommand(new RemoveByIdCommand(this,serverLoop));
        registerCommand(new ClearCommand(this,serverLoop));
        registerCommand(new ExecuteScriptCommand(this,serverLoop));
        registerCommand(new AddIfMaxCommand(this,serverLoop));
        registerCommand(new AddIfMinCommand(this,serverLoop));
        registerCommand(new HistoryCommand(this,serverLoop));
        registerCommand(new MaxByNameCommand(this,serverLoop));
        registerCommand(new CountByLocationCommand(this,serverLoop));
        registerCommand(new FilterStartsWithNameCommand(this,serverLoop));
    }

    public String readAndExecuteCommand(String commandName) throws IOException, ClassNotFoundException {
        Command command = getCommandByName(commandName);
        if (command == null) {
            return "Команда с указанным именем не найдена!";
        } else {
            history.add(command.getName());
            return command.execute();
        }
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public People getCollection() {
        return collection;
    }

    public List<String> getHistory() {
        return history;
    }

    public Command getCommandByName(String name) {
        return commands.get(name.trim());
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }

    public String run(String[] commands) throws IOException, ClassNotFoundException {
        return readAndExecuteCommands(commands);
    }

    public String readAndExecuteCommands(String[] commands) throws IOException, ClassNotFoundException {
        StringBuilder value = new StringBuilder();
        for (String commandName : commands) {
            value.append(readAndExecuteCommand(commandName)).append('\n');
        }
        return value.toString();
    }
}