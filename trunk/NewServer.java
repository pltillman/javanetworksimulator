import java.net.*;
import java.io.*;


public class NewServer extends Thread {

    protected DatagramSocket serv_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4449;
    protected final int BCAST_PORT = 7872;
    protected final int expiration = 2000;
    protected final int THREE_SECONDS = 3000;
    protected byte[] constrPacket = new byte[256];


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


    protected byte[] makePacket(byte f, byte[] host, byte[] ip, String message) {

        constrPacket[0] = f;
        byte[] b = message.getBytes();

        for (int y=1; y<5; y++) {
            constrPacket[y] = ip[y];
        }
        for (int j=5; j<33; j++) {
            constrPacket[j] = host[j];
        }
        for (int p=33; p<256; p++) {
            constrPacket[p] = b[p];
        }
        return constrPacket;
    }

    protected String extractDestHost(byte[] p) {
        byte[] h = null;
        for (int j=5,k=0; j<33; j++) {
            h[k++] = p[j];
        }
        String DestHost = new String(h,0,h.length);
        return DestHost;

    }

    protected byte[] extractIP(byte[] p) {
        byte[] ip = null;
        for (int j=1,k=0; j<5; j++) {
            ip[k++] = p[j];
        }
        return ip;
    }


    protected void populate_rt(byte[] b) {
        
    }

    protected void rtLookup(String n) {

        
        for (int j=0;j<rt.length;j++) {
            if (rt.contains(n)) {
                rt[j].getIP();
            }

        }
    }



}
