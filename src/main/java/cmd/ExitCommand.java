package cmd;

import client.Environment;
import connection.NetPackage;

import java.util.HashMap;

public class ExitCommand implements ICommand {


    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {

        return "exit                    | Завершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(Environment env, String arg) {
        env.getOut().writeln("Завершение работы клиентской части");
        env.turnOff();
    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        System.err.println("Нельзя так");
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
        ICommand cmd = new ExitCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
