package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

public class InfoCommand extends Command {
    public InfoCommand(Engine engine, ServerLoop serverLoop) {
        super(engine, serverLoop);
    }
    @Override
    public String execute() {
        return "Тип коллекции: " + collection.getClass() + "\n" + "Дата инициализации: " + collection.getInitializationDate() + "\n" + "Количество элементов: " + collection.getSize();
    }

    @Override
    public String getDescription() {
        return ":вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String getName() {
        return "info";
    }
}
