package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class RemoveByIdCommand implements ICommand {

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {

        return "remove_by_id            | Удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(Environment env, String arg) {
        int id = 0;
        if (!arg.isEmpty()) {
            try {
                id = Integer.parseInt(arg);
            } catch (Exception ex) {
                env.getOut().writeln("Wrong arg");
                return;
            }
            env.getCollectionManager().removeById(env.getOut(), id);
        }
        else {
            env.getOut().writeln("Arg is missing");
        }
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        env.getCollectionManager().removeById(env.getOut(), Integer.parseInt(netPackage.getArg()));
    }

    @Override
    public boolean hasObject() {
        return false;
    }

    @Override
    public boolean hasArg() {
        return true;
    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new RemoveByIdCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
