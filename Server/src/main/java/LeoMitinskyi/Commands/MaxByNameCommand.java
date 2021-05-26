package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

public class MaxByNameCommand extends Command {
    public MaxByNameCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute() {
        if (collection.MaxName().getName() != null ){
            return collection.MaxName().toString();
        }else{
            return "Коллекция пуста";
        }
    }

    @Override
    public String getDescription() {
        return ":вывести любой объект из коллекции, значение поля name которого является максимальным";
    }

    @Override
    public String getName() {
        return "max_by_name";
    }
}
