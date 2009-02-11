
import java.net.*;
import java.io.*;

public class NewClient {

    protected DatagramSocket client_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4448;
    protected final int BCAST_PORT = 7872;
    protected final int SERVER_PORT = 4449;
    protected final int expiration = 20;
    protected byte[] message;

    public static void main(String[] args) throws IOException {
        new NewClient();
    }

    


    public NewClient() throws IOException {

        listen();

        message = new byte[256];

        InetAddress address = InetAddress.getByName("localhost");


        // this will serve as the basic call for encoding a message
        message = encode("sample message - add anything here", "localhost", 0);
        
        // Then a packet will be create from the message
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


    protected byte[] encode(String msg, String host, int flag) {
        byte[] output = new byte[256];
        String port = Integer.toString(DEFAULT_PORT);

        try {
            InetAddress local_host = InetAddress.getLocalHost();
            String p = new String(local_host.getAddress());
            String out = p + " " + host + " " + msg +
                    " " + Integer.toString(flag);
            output = out.getBytes();
            System.out.println(p);
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();

        }
        return output;
    }


    // We may want to add the listening piece to the server. I suppose
    // it makes more sense that way.
    protected void listen()throws IOException {

        Boolean added = false;
        InetAddress address = null;
        MulticastSocket bsocket = null;

        while (!added) {

            // Create a broadcast range & socket and then bind them
            address = InetAddress.getByName("230.0.0.1");
            bsocket = new MulticastSocket(BCAST_PORT);
            bsocket.joinGroup(address);

            // Create an emtpy packet and call to recieve
            byte[] msg = new byte[256];
            packet = new DatagramPacket(msg, msg.length);
            bsocket.receive(packet);

            // Convert the byte[] to String and print it
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + received);

            // Check the message for a broadcast call
            if (received.equals("ip")) {
                added = true;
            }
        }

        if (true) {
            bsocket.leaveGroup(address);
            bsocket.close();
            
        }

    }



  

}
