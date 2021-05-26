package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

public class HelpCommand extends Command {
    public HelpCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute(){
        StringBuilder information = new StringBuilder();
        for (Command command : engine.getAllCommands()) {
            information.append(command.getName()).append(" ").append(command.getDescription()).append("\n");
        }
        return information.toString();
    }

    @Override
    public String getDescription() {
        return ":вывести справку по доступным командам";
    }

    @Override
    public String getName() {
        return "help";
    }
}
