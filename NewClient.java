
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
    protected InetAddress address;
    protected byte[] IP;
    protected String local_ip;

    public static void main(String[] args) throws IOException {
        new NewClient();
    }

    


    public NewClient() throws IOException {

        message = new byte[256];
        address = InetAddress.getLocalHost();
        IP = address.getAddress();

        // Get a string representation of the local ip address
        for (int index=0; index<IP.length; index++) {
            if (index > 0) {
                local_ip += ".";
                //System.out.print(".");
            }
            local_ip += ((int)IP[index]) & 0xff;
            //System.out.print(((int)IP[index])& 0xff);

        }
        System.out.println("My local IP address is: " + local_ip);
        listen();
        
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

        String out = local_ip + " " + host + " " + msg +
                " " + Integer.toString(flag);
        output = out.getBytes();
        
        return output;
    }


    // We may want to add the listening piece to the server. I suppose
    // it makes more sense that way.
    protected void listen()throws IOException {

        Boolean added = false;
        InetAddress b_address = null;
        MulticastSocket bsocket = null;

        while (!added) {

            // Create a broadcast range & socket and then bind them
            b_address = InetAddress.getByName("230.0.0.1");
            bsocket = new MulticastSocket(BCAST_PORT);
            bsocket.joinGroup(b_address);

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
                //setup a new socket and send ip back to the server
                //add code here
            }
        }

        if (true) {
            bsocket.leaveGroup(b_address);
            bsocket.close();
            
        }

    }



  

}
