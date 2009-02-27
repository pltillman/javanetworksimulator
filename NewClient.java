
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
    protected byte[] constrPacket = new byte[256];
    protected String host;


    public static void main(String[] args) throws IOException {
        new NewClient();
    }

    public NewClient() throws IOException {

        message = new byte[256];
        address = InetAddress.getLocalHost();
        IP = address.getAddress();
        host = address.getHostName();

    }

    /**************************************************************
     *
     * @param s
     * @throws java.io.IOException
     **************************************************************/
    protected void sendMSG(String h, String s, int l) throws IOException {

        byte f = 0;
        String text = "\n" + h + ": " + s;
        message = makePacket(f,host,IP,text,l);
        packet = new DatagramPacket(message, message.length, address, SERVER_PORT);

        try {
            client_socket = new DatagramSocket();
            //client_socket.setSoTimeout(expiration);
            client_socket.send(packet);
            System.out.println("message sent successfully");
				client_socket.close();

        } catch (SocketException se) {
            se.printStackTrace();
        }
    }

    protected void sendFILE(File f) throws IOException {

        ServerSocket servsock = new ServerSocket(SERVER_PORT+1);

        while (true) {
            System.out.println("Waiting...");

            Socket sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);

            // sendfile
            File myFile = f;
            byte [] mybytearray  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = sock.getOutputStream();
            System.out.println("Sending...");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            sock.close();
        }
    }

    protected String rcvMSG() {
        String response = null;
        
        return response;
    }
    /**************************************************************
     *
     * @throws java.io.IOException
     **************************************************************/
    protected void broadCastIP() throws IOException {

        byte[] msg = new byte[256];
        MulticastSocket socket = new MulticastSocket(BCAST_PORT);
        DatagramSocket receiver = null;

        InetAddress group = InetAddress.getByName("224.0.0.1");
        byte f = 1;
        msg = makePacket(f, host, IP, "", 0);

        packet = new DatagramPacket(msg, msg.length, group, BCAST_PORT);

        byte[] serverIP = new byte[4];

        DatagramPacket pack = new DatagramPacket(serverIP, serverIP.length);

        Boolean added = false;
        while (!added) {
            try {
                socket.send(packet);
                receiver = new DatagramSocket(DEFAULT_PORT);
                receiver.receive(pack);
                socket.close();
                System.out.println("Broadcast socket closed");


                address = pack.getAddress();
                IP = address.getAddress();
                host = address.getHostName();
                added = true;


            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
//
//            try {
//
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            }
        }

        socket.close();
    }


    /**************************************************************
     *
     * @param f
     * @param host
     * @param ip
     * @param message
     * @return
     **************************************************************/
    protected byte[] makePacket(byte f, String host, byte[] ip, String message, int l) {
        //constrPacket = null;
        constrPacket = new byte[256];
        constrPacket[0] = f;
        byte[] b = message.getBytes();
        byte[] h = host.getBytes();

        for (int y=1,k=0; y<5; y++) {
            constrPacket[y] = ip[k++];
        }
        if (h.length < 33 && b.length < 256) {
            for (int j=5, u=0; j<h.length; j++) {
                constrPacket[j] = h[u++];
            }
            if (l < 128)
                constrPacket[33] = (byte)(l);
            else
                System.out.println("too much text to store");
            for (int p=34,w=0; w<b.length; p++) {
                constrPacket[p] = b[w++];
            }
        }

        return constrPacket;
    }

    /**************************************************************
     *
     * @param p
     * @return
     **************************************************************/
    protected String extractDestHost(byte[] p) {
        byte[] h = null;
        for (int j=5,k=0; j<33; j++) {
            h[k++] = p[j];
        }
        String DestHost = new String(h,0,h.length);
        return DestHost;

    }

    /**************************************************************
     *
     * @param p
     * @return
     **************************************************************/
    protected byte[] extractIP(byte[] p) {
        byte[] ip = null;
        for (int j=1,k=0; j<5; j++) {
            ip[k++] = p[j];
        }
        return ip;
    }

    /**************************************************************
     * 
     * @param n
     **************************************************************/
    protected void updatePacket(byte[] n) {
        for (int i=1,k=0; i<5; i++) {
            constrPacket[i] = n[k++];
        }
    }

  

}
