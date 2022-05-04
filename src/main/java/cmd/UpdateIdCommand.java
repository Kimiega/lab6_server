package cmd;

import client.Environment;
import collection.City;
import connection.NetPackage;
import ioManager.RequestElement;

import java.util.HashMap;

public class UpdateIdCommand implements ICommand {

    @Override
    public String getName() {
        return "update_id";
    }

    @Override
    public String getDescription() {

        return "update_id               | Обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(Environment env, String arg) {
        int id = 0;
        if (!arg.isEmpty())
            try{
                id = Integer.parseInt(arg);
            }
            catch (Exception ex){
                env.getOut().writeln("Wrong arg");
                return;
            }
        else{
            System.err.println("Wrong arg");
            return;
        }
        RequestElement reqEl = new RequestElement(env.getIn(), env.getOut(), !env.isScript());
        City o = reqEl.readElement();
        env.getCollectionManager().updateById(env.getOut(), id,o);
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
    env.getCollectionManager().updateById(env.getOut(), Integer.parseInt(netPackage.getArg()),netPackage.getCity());
    }

    @Override
    public boolean hasObject() {
        return true;
    }

    @Override
    public boolean hasArg() {
        return true;
    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new UpdateIdCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
