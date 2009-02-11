import java.net.*;
import java.io.*;


public class NewServer extends Thread {

    protected DatagramSocket serv_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4449;
    protected final int BCAST_PORT = 7872;
    protected final int expiration = 2000;
    protected final int THREE_SECONDS = 3000;

    public static void main(String[] args) throws IOException {

        new NewServer("s1");
    }



    
    public NewServer(String n) throws IOException {

        super(n);
        callForClients();

        byte[] message = new byte[256];

        packet = new DatagramPacket(message, message.length);
        serv_socket = new DatagramSocket(DEFAULT_PORT);
        
        InetAddress local_addr = InetAddress.getLocalHost();
        System.out.println(local_addr);
        serv_socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received + "\t" + packet.getLength() + "\t" + packet.getOffset()
                + "\t" + packet.getPort());
        
        serv_socket.close();

    }


    protected void callForClients() throws IOException {


        byte[] msg = new byte[256];
        MulticastSocket socket = new MulticastSocket(BCAST_PORT);
        InetAddress group = InetAddress.getByName("230.0.0.1");
        String call = "ip";
        msg = call.getBytes();
        
        packet = new DatagramPacket(msg, msg.length, group, BCAST_PORT);
        
        try {
            socket.send(packet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

		try {
		    sleep((long)(Math.random() /* * 3000 to pause for 3 seconds and wait for a response */ ));
		} catch (InterruptedException e) {
            e.printStackTrace();
        }

        socket.close();

    }






}
