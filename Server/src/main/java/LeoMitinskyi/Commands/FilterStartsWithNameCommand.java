package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.IOException;

public class FilterStartsWithNameCommand extends Command {
    public FilterStartsWithNameCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute() throws IOException, ClassNotFoundException {
        String substring = ioManager.StringReader("Введите подстроку:");
        return collection.subStringSearcher(substring);
    }

    @Override
    public String getDescription() {
        return ":вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String getName() {
        return "filter_starts_with_name";
    }
}
