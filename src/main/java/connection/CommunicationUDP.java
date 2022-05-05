package connection;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationUDP {
    static Logger LOGGER = Logger.getLogger(CommunicationUDP.class.getName());

    private final int PACKET_MAX_LENGTH = 1400;
    private final DatagramChannel channel;
    private SocketAddress remote_address;
    public CommunicationUDP(InetSocketAddress SERVER_SOCKET) throws IOException {
        channel = DatagramChannel.open();
        channel.socket().bind(SERVER_SOCKET);
        channel.configureBlocking(false);
    }
    public NetPackage listening() throws IOException, IllegalArgumentException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_MAX_LENGTH);
        remote_address =  channel.receive(byteBuffer);
        byteBuffer.flip();
        NetPackage np = (NetPackage) SerializationUtils.deserialize(byteBuffer.array());
        LOGGER.log(Level.FINE,"Получен пакет от "+remote_address.toString()+"\nРазмер пакета = " + byteBuffer.array().length);
//        System.out.println("len of received packet = " + byteBuffer.array().length);
//        for (byte b : byteBuffer.array())
//            System.out.print(b);
//        System.out.println();

        return np;
    }

    public void send(Serializable obj) throws IOException {
//        System.out.println("len of sent packet = " + SerializationUtils.serialize(obj).length);
//        for (byte b : SerializationUtils.serialize(obj))
//            System.out.print(b);
//        System.out.println();
        ByteBuffer byteBuffer = ByteBuffer.wrap(SerializationUtils.serialize(obj));
        channel.send(byteBuffer, remote_address);
        LOGGER.log(Level.FINE,"Пакет отправлен клиенту "+remote_address.toString()+"\nРазмер пакета = "+byteBuffer.array().length);
//        System.out.println("Packet sent");
    }
    public void close() throws IOException {
        channel.close();
        LOGGER.log(Level.FINE,"Канал закрыт");
    }
}
