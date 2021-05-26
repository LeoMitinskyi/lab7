package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

public class ClearCommand extends Command {
    public ClearCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute() {
        collection.clear();
        return "Коллекция очищена.";
    }

    @Override
    public String getDescription() {
        return ":очистить коллекцию";
    }

    @Override
    public String getName() {
        return "clear";
    }
}
