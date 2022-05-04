package ioManager;

import connection.CommunicationUDP;
import connection.NetResponse;

import java.io.IOException;

public class ResponseOut implements IWritable{
    CommunicationUDP communication;
    public ResponseOut(CommunicationUDP communication){
        this.communication = communication;
    }
    @Override
    public void write(String s) {
        try {
            communication.send(new NetResponse(s,false));
        } catch (IOException e) {
            System.err.println("Ошибка отправки пакета");
        }
    }

    @Override
    public void writeln(String s) {
        write(s+"\n");
    }
}
