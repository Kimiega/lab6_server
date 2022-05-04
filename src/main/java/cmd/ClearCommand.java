package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class ClearCommand implements ICommand {


    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {

        return "clear                   | Очистить коллекцию";
    }

    @Override
    public void execute(Environment env, String arg) {
        env.getCollectionManager().clear(env.getOut());
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        env.getCollectionManager().clear(env.getOut());
    }

    @Override
    public boolean hasObject() {
        return false;
    }

    @Override
    public boolean hasArg() {
        return false;
    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new ClearCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
