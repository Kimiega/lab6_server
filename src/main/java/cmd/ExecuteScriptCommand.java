package cmd;

import client.Client;
import client.Environment;
import connection.CommunicationUDP;
import connection.NetPackage;
import ioManager.EmptyOut;
import ioManager.IReadable;
import ioManager.ReaderFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuteScriptCommand implements ICommand {
    static Logger LOGGER = Logger.getLogger(ExecuteScriptCommand.class.getName());
    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {

        return "execute_script          | Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public void execute(Environment env, String arg) {
        if (!arg.isEmpty()){
            IReadable readerFile;
            try{
                readerFile = new ReaderFile(arg);
            }
            catch (IOException ex){
                env.getOut().writeln("Файл скрипта не может быть прочитан");
                LOGGER.log(Level.FINE,"Файл скрипта не может быть прочитан");
                return;
            }
           Environment envScript = new Environment(
                   env.getCollectionManager(),
                   env.getCommandMap(),
                   env.getPath(),
                   readerFile,
                   new EmptyOut(),
                   true
           );
            envScript.getCommandMap().remove(getName());
            Client clientScript = new Client(envScript);
            try{clientScript.init();}
            catch(Exception ex){
                env.getOut().writeln("Ошибка во время выполнения скрипта");
                LOGGER.log(Level.FINE,"Ошибка во время выполнения скрипта");
            }
            env.getOut().writeln("Чтение скрипта завершено");
            LOGGER.log(Level.FINE,"Чтение скрипта завершено");
            }
        else {
            env.getOut().writeln("Аргумент должен содержать путь к файлу");
            LOGGER.log(Level.FINE,"Аргумент должен содержать путь к файлу");
        }

    }

    @Override
    public void execute(Environment env, NetPackage netPackage) {
        LOGGER.log(Level.WARNING,"Был вызван execute_script клиентом, что запрещено");
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
        ICommand cmd = new ExecuteScriptCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
