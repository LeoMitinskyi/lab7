package LeoMitinskyi.Commands;

import LeoMitinskyi.Database.User;
import LeoMitinskyi.Engine;
import LeoMitinskyi.IOManager;
import LeoMitinskyi.ServerLoop;
import LeoMitinskyi.types.People;

import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    protected final Engine engine;
    protected final IOManager ioManager;
    protected final People collection;
    protected final ServerLoop serverLoop;
    protected final User user;
    public Command(Engine engine, ServerLoop serverLoop) {
        this.engine = engine;
        this.serverLoop = serverLoop;
        this.ioManager = serverLoop.getIOManager();
        this.collection = engine.getCollection();
        this.user = serverLoop.getUser();
    }

    public abstract String execute() throws IOException, ClassNotFoundException;
    public abstract String getDescription();
    public abstract String getName();
}
