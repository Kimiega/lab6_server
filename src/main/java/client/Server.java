package client;

import cmd.ICommand;
import connection.CommandPackage;
import connection.CommunicationUDP;
import connection.NetPackage;
import connection.NetResponse;

import java.io.IOException;

public class Server {
    private CommunicationUDP communication;
    private Environment env;
    public Server(Environment env, CommunicationUDP communication){
        this.env = env;
        this.communication = communication;
    }

    public void init(){
        while (env.isRunning()){
            NetPackage netPackage;
            try {
                netPackage = communication.listening();
               // System.out.println("Packet received");
            } catch (IOException e) {
                System.err.println("Ошибка прослушки порта");
                continue;
            }
            catch (IllegalArgumentException ex){
                //System.err.println("Received wrong packet");
                continue;
            }
            catch (IllegalStateException ex){
                System.err.println("Получен поврежденный пакет");
                continue;
            }
            String cmd = netPackage.getCmd();
            if (env.getCommandMap().containsKey(cmd)) {
                env.getCommandMap().get(cmd).execute(env, netPackage);
            }
            else if(cmd.equals("connect")){
                try {
                    int it = 0;
                    for (ICommand c : env.getCommandMap().values()) {
                        if (it+1==env.getCommandMap().size())
                            communication.send(new CommandPackage(c.getName(), c.getDescription(), c.hasArg(), c.hasObject(),true));
                        else communication.send(new CommandPackage(c.getName(), c.getDescription(), c.hasArg(), c.hasObject(),false));
                        it++;
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка отправки списка разрешенных команд");
                }
            }
            else {
                env.getOut().writeln("Command not found (type \"help\" to get information about available commands)");
            }
            try {
                communication.send(new NetResponse("",true)); //Отправка завершающего пакета
            } catch (IOException e) {
                System.err.println("Ошибка отправки завершающего пакета");
            }
        }
        try {
            communication.close();
        } catch (IOException e) {
            System.err.println("Ошибка закрытия сетевого канала");
        }
    }
}
