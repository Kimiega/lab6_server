package ioManager;

import connection.CommunicationUDP;
import connection.NetResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseOut implements IWritable{
    CommunicationUDP communication;
    static Logger LOGGER = Logger.getLogger(ResponseOut.class.getName());
    public ResponseOut(CommunicationUDP communication){
        this.communication = communication;
    }
    @Override
    public void write(String s) {
        try {
            communication.send(new NetResponse(s,false));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"Ошибка во время отправки пакета");
        }
    }

    @Override
    public void writeln(String s) {
        write(s+"\n");
    }
}
