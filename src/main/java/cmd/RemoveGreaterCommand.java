package cmd;

import client.Environment;
import collection.City;
import connection.NetPackage;
import ioManager.RequestElement;

import java.util.HashMap;

public class RemoveGreaterCommand implements ICommand {

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {

        return "remove_greater          | Удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public void execute(Environment env, String arg) {
        RequestElement reqEl = new RequestElement(env.getIn(), env.getOut(), !env.isScript());
        City o = reqEl.readElement();
        env.getCollectionManager().removeGreater(env.getOut(), o);
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        env.getCollectionManager().removeGreater(env.getOut(), netPackage.getCity());
    }

    @Override
    public boolean hasObject() {
        return true;
    }

    @Override
    public boolean hasArg() {
        return false;
    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new RemoveGreaterCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
