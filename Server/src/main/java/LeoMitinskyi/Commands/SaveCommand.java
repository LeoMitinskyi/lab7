package LeoMitinskyi.Commands;

import LeoMitinskyi.Engine;
import LeoMitinskyi.ServerLoop;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SaveCommand extends Command {
    public SaveCommand(Engine engine, ServerLoop serverLoop) {
        super(engine,serverLoop);
    }
    @Override
    public String execute(){
        try {
            collection.save();
        } catch (SQLException | NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        return "Коллекция успешно сохранена";
    }

    @Override
    public String getDescription() {
        return ":сохранить коллекцию в файл";
    }

    @Override
    public String getName() {
        return "save";
    }
}
