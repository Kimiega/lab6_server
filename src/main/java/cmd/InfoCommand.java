package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class InfoCommand implements ICommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {

        return "info                    | Вывести в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public void execute(Environment env, String arg) {
        env.getCollectionManager().info(env.getOut());
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        env.getCollectionManager().info(env.getOut());
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
        ICommand cmd = new InfoCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
