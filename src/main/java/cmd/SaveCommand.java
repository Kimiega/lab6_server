package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class SaveCommand implements ICommand {

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
        System.err.println("Ne ne, ne tak");
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
