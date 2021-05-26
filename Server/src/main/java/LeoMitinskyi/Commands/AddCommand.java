package LeoMitinskyi.Commands;

import LeoMitinskyi.Database.DatabaseManager;
import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;
import LeoMitinskyi.types.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddCommand extends Command {
    public AddCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }

    @Override
    public String execute() throws IOException, ClassNotFoundException {
        String name = ioManager.StringReader("Enter name:");
        Integer height = ioManager.IntegerReader("Enter height(it's should be more than zero):");

        float x = ioManager.FloatReader("Enter x(962 is maximum):", 962f);
        float y = ioManager.FloatReader("Enter y(214 is maximum)", 214f);

        LocalDate creationDate = LocalDate.now();

        Color eyeColor = ioManager.ColorReader("Enter eyeColor: {GREEN, " + "RED, " + "BLACK, " + "YELLOW, " + "BROWN;}");
        Color hairColor = ioManager.ColorReader("Enter eyeColor: {GREEN, " + "RED, " + "BLACK, " + "YELLOW, " + "BROWN;}");
        Country nationality = ioManager.CountryReader("Enter country: {USA," + "SPAIN," + "INDIA," + "ITALY," + "JAPAN;}");

        Long X = ioManager.LongReader("Enter X:");
        double Y = ioManager.DoubleReader("Enter Y");
        Float Z = ioManager.FloatReader("Enter Z");

        Coordinates coordinates = new Coordinates(x,y);
        Location location = new Location(X,Y,Z);

        Person p = new Person(name,coordinates,creationDate,height,eyeColor,hairColor,nationality,location,user.getUsername(),user.getPassword());
        collection.add(p);
        try {
            DatabaseManager.savePeople();
            DatabaseManager.loadPeople();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return "Object was added: ";
    }

    @Override
    public String getDescription() {
        return ":добавить новый элемент в коллекцию";
    }

    @Override
    public String getName() {
        return "add";
    }
}