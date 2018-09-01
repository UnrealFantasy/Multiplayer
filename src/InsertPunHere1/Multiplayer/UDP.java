package InsertPunHere1.Multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP {
    private static void send(String message) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();

        System.out.println("CLIENT --> " + message);

        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");

        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(), message.length(), inetAddress, 25565);

        datagramSocket.send(datagramPacket);

        datagramSocket.close();
    }

    private static String receive() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(25565, InetAddress.getByName("127.0.0.1"));

        byte[] buffer = new byte[2048];

        DatagramPacket datagramPacket = new DatagramPacket(buffer, 2048);

        datagramSocket.receive(datagramPacket);

        String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

        datagramSocket.close();

        return message;
    }
}
