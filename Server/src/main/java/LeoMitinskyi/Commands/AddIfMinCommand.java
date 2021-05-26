package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.IOException;

public class AddIfMinCommand extends Command {
    public AddIfMinCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute() throws IOException, ClassNotFoundException {
        Integer minHeight = collection.findMinHeight();
        Integer height = ioManager.IntegerReader("Введите рост человека. Если он будет наименьшим, то вы сможете добавить нового человека");

        if (height < minHeight) {
            AddCommand addCommand = new AddCommand(engine,serverLoop);
            return addCommand.execute();
        }
        else{
            return "Рост не является минимальным.";
        }
    }

    @Override
    public String getDescription() {
        return ":добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String getName() {
        return "add_if_min";
    }
}
