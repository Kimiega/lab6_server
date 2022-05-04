package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class LoadCommand implements ICommand {

    @Override
    public String getName() {
        return "load";
    }

    @Override
    public String getDescription() {

        return "load                    | Загрузить коллекцию из файла";
    }

    @Override
    public void execute(Environment env, String arg) {
        env.getCollectionManager().load(env.getOut(), env.getPath());
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        System.err.println("nonono");
    }

    @Override
    public boolean hasObject() {
        return false;
    }

    @Override
    public boolean hasArg() {
        return true;
    }

    public static void register( HashMap<String, ICommand> commandMap) {
        ICommand cmd = new LoadCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
