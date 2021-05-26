package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.IOException;

public class AddIfMaxCommand extends Command {
    public AddIfMaxCommand(Engine engine, ServerLoop serverLoop) {
        super(engine, serverLoop);
    }
    @Override
    public String execute() throws IOException, ClassNotFoundException {
        Integer maxHeight = collection.findMaxHeight();
        Integer height = ioManager.IntegerReader("Введите рост человека. Если он будет наибольшим, то вы сможете добавить нового человека");
        if (height > maxHeight) {
            AddCommand addCommand = new AddCommand(engine,serverLoop);
            return addCommand.execute();
        }
        else{
            return "Рост не является максимальным.";
        }
    }

    @Override
    public String getDescription() {
        return ":добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String getName() {
        return "add_if_max";
    }
}
