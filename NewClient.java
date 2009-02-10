
import java.net.*;
import java.io.*;

public class NewClient {

    protected DatagramSocket client_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4448;
    protected final int SERVER_PORT = 4449;
    protected final int expiration = 20;


    public static void main(String[] args) throws IOException {
        new NewClient();
    }

    


    public NewClient() throws IOException {

        byte[] message = new byte[256];
        for (byte i : message) {
            i = 5;
        }

        InetAddress address = InetAddress.getByName("localhost");
        packet = new DatagramPacket(message, message.length, address, SERVER_PORT);
        
        try {
            client_socket = new DatagramSocket();
            client_socket.setSoTimeout(expiration);
            client_socket.send(packet);
            System.out.println("message sent successfully");

        } catch (SocketException se) {
            se.printStackTrace();
        }

    }



    public void encode_packet() {

        
    }




  

}
