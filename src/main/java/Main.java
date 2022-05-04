import client.Client;
import client.Environment;
import client.Server;
import cmd.*;
import collection.CollectionManager;
import connection.CommunicationUDP;
import ioManager.ConsoleManager;
import ioManager.IReadable;
import ioManager.IWritable;
import ioManager.ResponseOut;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        String path = "collection.json";
        Integer SERVER_PORT = null;
        try{
            ServerSocket servSoc = new ServerSocket(0);
            SERVER_PORT = servSoc.getLocalPort();
        }
        catch (IOException ex)
        {
            System.err.println("Не удалось найти свободный порт");
            System.exit(0);
        }
        InetAddress SERVER_ADDRESS = null;
        try {
            SERVER_ADDRESS = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            System.err.println("Не удалось задать адрес сервера");
            System.exit(0);
        }
        //start settings
        //console gui
        //offline online
        //path type
        // path
        /* for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg == "-p"){
               if (i!=args.length){
                   path = arg;
               }
            }
        }*/
        InetSocketAddress SERVER_SOCKET = null;
        CommunicationUDP communication = null;
        try {
            SERVER_SOCKET = new InetSocketAddress(SERVER_ADDRESS,SERVER_PORT);
            communication = new CommunicationUDP(SERVER_SOCKET);
        } catch (IOException e) {
            System.err.println("Не удалось установить адрес сервера");
            System.exit(0);
        }
        catch (IllegalArgumentException ex) {
            System.err.println("Порт указан неверно");
            System.exit(0);
        }

        CollectionManager myCollection = new CollectionManager();
        HashMap<String, ICommand> localCommandMap = new HashMap<String, ICommand>();
        {
            AddCommand.register(localCommandMap);
            AddIfMaxCommand.register(localCommandMap);
            AddIfMinCommand.register(localCommandMap);
            ClearCommand.register(localCommandMap);
            ExitCommand.register(localCommandMap);
            HelpCommand.register(localCommandMap);
            InfoCommand.register(localCommandMap);
            PrintAscendingCommand.register(localCommandMap);
            PrintDescendingCommand.register(localCommandMap);
            RemoveAllByTimezoneCommand.register(localCommandMap);
            RemoveByIdCommand.register(localCommandMap);
            RemoveGreaterCommand.register(localCommandMap);
            SaveCommand.register(localCommandMap);
            ShowCommand.register(localCommandMap);
            UpdateIdCommand.register(localCommandMap);
            LoadCommand.register(localCommandMap);
            ExecuteScriptCommand.register(localCommandMap);
        } //local commandMap
        HashMap<String, ICommand> remoteCommandMap = new HashMap<String, ICommand>();
        {
            AddCommand.register(remoteCommandMap);
            AddIfMaxCommand.register(remoteCommandMap);
            AddIfMinCommand.register(remoteCommandMap);
            ClearCommand.register(remoteCommandMap);
            InfoCommand.register(remoteCommandMap);
            PrintAscendingCommand.register(remoteCommandMap);
            PrintDescendingCommand.register(remoteCommandMap);
            RemoveAllByTimezoneCommand.register(remoteCommandMap);
            RemoveByIdCommand.register(remoteCommandMap);
            RemoveGreaterCommand.register(remoteCommandMap);
            ShowCommand.register(remoteCommandMap);
            UpdateIdCommand.register(remoteCommandMap);
        } //remote commandMap
        File file = new File(path);
        try {
            if (!file.createNewFile())
                myCollection.load(ConsoleManager.getInstance(), path);
            else
                System.out.println("Был создан новый файл коллекции");
        }
        catch (IOException ex){
            System.out.println("File can't be created\nFinishing of working program");
            //ex.printStackTrace();
            System.exit(1);
        }
        System.out.println("Server started on "+SERVER_SOCKET.getAddress().getHostAddress()+":"+SERVER_SOCKET.getPort());
        Environment serverEnv = new Environment(myCollection,remoteCommandMap,path,ConsoleManager.getInstance(),new ResponseOut(communication),false);
        Server server = new Server(serverEnv,communication);
        Thread serverConsole = new Thread(server::init);
        serverConsole.start();

        Environment localEnv = new Environment(myCollection,localCommandMap,path, ConsoleManager.getInstance(), ConsoleManager.getInstance(), false);
        Client client = new Client(localEnv);
        client.init();
        System.out.println("Завершение работы серверной части");
        serverEnv.turnOff();
        System.out.println("Завершение работы программы");
    }
}
