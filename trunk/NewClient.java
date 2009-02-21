
import java.net.*;
import java.io.*;
import java.util.Scanner;

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
    protected byte[] constrPacket = new byte[256];
    protected String host;


    public static void main(String[] args) throws IOException {
        new NewClient();
    }

    

    public NewClient() throws IOException {

        Scanner scan = new Scanner(System.in);
        
        message = new byte[256];
        address = InetAddress.getLocalHost();
        IP = address.getAddress();
        host = address.getHostName();

        // Get a string representation of the local ip address
        for (int index=0; index<IP.length; index++) {
            if (index > 0) {
                local_ip += ".";
            }
            local_ip += ((int)IP[index]) & 0xff;
        }

        //strip of the stupid null that keeps showing up for some reason
        local_ip = local_ip.substring(4);

        System.out.println("My local IP address is: " + local_ip);
        broadCastIP();
        
        // this will serve as the basic call for encoding a message
        //message = encode("sample message - add anything here", "localhost", 0);
        
        //byte f, byte[] host, byte[] ip, String message
        
        byte f = 0;
        IP[0] = 1;
        message = makePacket(f,host,IP,"Can you see this?");

        // Then a packet will be create from the message
        packet = new DatagramPacket(message, message.length, address, SERVER_PORT);
        int i = 0;
        while (i<10) {
            try {
                client_socket = new DatagramSocket();
                client_socket.setSoTimeout(expiration);
                client_socket.send(packet);
                System.out.println("message sent successfully");

            } catch (SocketException se) {
                se.printStackTrace();
            }
            i++;
        }

    }


    protected void broadCastIP() throws IOException {

        byte[] msg = new byte[256];
        MulticastSocket socket = new MulticastSocket(BCAST_PORT);
        InetAddress group = InetAddress.getByName("230.0.0.1");
        byte f = 1;
        msg = makePacket(f, host, IP, "Test");

        packet = new DatagramPacket(msg, msg.length, group, BCAST_PORT);

        try {
            socket.send(packet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        socket.close();

    }
    
    protected byte[] makePacket(byte f, String host, byte[] ip, String message) {

        constrPacket[0] = f;
        byte[] b = message.getBytes();
        byte[] h = host.getBytes();
        System.out.println("host length: " + h.length);

        for (int y=1,k=0; y<5; y++) {
            constrPacket[y] = ip[k++];
        }
        for (int j=5, l=0; j<h.length; j++) {
            constrPacket[j] = h[l++];
        }
        for (int p=33,w=0; p<b.length; p++) {
            constrPacket[p] = b[w++];
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

    protected void updatePacket(byte[] n) {
        for (int i=1,k=0; i<5; i++) {
            constrPacket[i] = n[k++];
        }
    }

  

}
