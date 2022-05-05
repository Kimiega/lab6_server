package cmd;

import client.Environment;
import connection.CommunicationUDP;
import connection.NetPackage;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveCommand implements ICommand {
    static Logger LOGGER = Logger.getLogger(SaveCommand.class.getName());
    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {

        return "save                    | Сохранить коллекцию в файл";
    }

    @Override
    public void execute(Environment env, String arg) {
        env.getCollectionManager().save(env.getOut(), env.getPath());
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        LOGGER.log(Level.WARNING,"Был вызван save клиентом, что запрещено");
    }

    @Override
    public boolean hasObject() {
        return false;
    }

    @Override
    public boolean hasArg() {
        return false;
    }

    public static void register( HashMap<String, ICommand> commandMap) {
        ICommand cmd = new SaveCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
