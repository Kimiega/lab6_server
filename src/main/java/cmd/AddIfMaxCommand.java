package cmd;

import client.Environment;
import collection.City;
import connection.NetPackage;
import ioManager.RequestElement;

import java.util.HashMap;

public class AddIfMaxCommand implements ICommand {

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "add_if_max              | Добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public void execute(Environment env, String arg) {
        RequestElement reqEl = new RequestElement(env.getIn(), env.getOut(), !env.isScript());
        City o = reqEl.readElement();
        env.getCollectionManager().addIfMax(env.getOut(), o);
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        env.getCollectionManager().addIfMax(env.getOut(), netPackage.getCity());
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
        ICommand cmd = new AddIfMaxCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
