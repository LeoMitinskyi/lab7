package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.IOException;

public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute() throws IOException, ClassNotFoundException {
        long id = ioManager.LongReader("Enter id");
        if (collection.get(id) == null) {
            return "Элемент с заданным ключём отстутствует.";
        } else if(!collection.get(id).getLogin().equals(user.getUsername())){
            return "Вы не можете удалить этого человека";
        }else {
            collection.remove(id);
            return "Object was deleted.";
        }
    }

    @Override
    public String getDescription() {
        return ":удалить элемент из коллекции по его id";
    }

    @Override
    public String getName() {
        return "remove";
    }
}
