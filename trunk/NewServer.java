import java.net.*;
import java.io.*;


public class NewServer extends Thread {

    protected DatagramSocket serv_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4449;
    protected final int expiration = 20;


    public static void main(String[] args) throws IOException {
        new NewServer("s1");
    }



    

    public NewServer(String n) throws IOException {
        super(n);

        byte[] message = new byte[3];

        packet = new DatagramPacket(message, message.length);

        InetAddress local_addr;
        serv_socket = new DatagramSocket(DEFAULT_PORT);

        try {
            local_addr = InetAddress.getLocalHost();
            System.out.println(local_addr);
            serv_socket.receive(packet);
            System.out.println(packet.toString());

        } catch (SocketException se) {
            se.printStackTrace();
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        }

    }





}
