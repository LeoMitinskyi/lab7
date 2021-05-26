package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.io.IOException;

public class CountByLocationCommand extends Command {
    public CountByLocationCommand(Engine engine, ServerLoop serverLoop) {
        super(engine, serverLoop);
    }
    @Override
    public String execute() throws IOException, ClassNotFoundException {
        Long X = ioManager.LongReader("Enter X:");
        double Y = ioManager.DoubleReader("Enter Y");
        Float Z = ioManager.FloatReader("Enter Z");
        return String.valueOf(collection.LocationCounter(X,Y,Z));
    }

    @Override
    public String getDescription() {
        return ":вывести количество элементов, значение поля location которых равно заданному";
    }

    @Override
    public String getName() {
        return "count_by_location";
    }
}
