
import java.net.*;
import java.io.*;

public class NewClientThread implements Runnable {

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


    public void run() {
        System.out.println("Hello from a thread!");

        //Boolean added = false;
        InetAddress b_address = null;
        MulticastSocket bsocket = null;

        try {
            b_address = InetAddress.getByName("224.0.0.251");
            bsocket = new MulticastSocket(BCAST_PORT);
            bsocket.joinGroup(b_address);

            while (!bsocket.isClosed()) {
                System.out.println("checking for packets....\n");
                // Create an emtpy packet and call to recieve
                byte[] msg = new byte[256];
                packet = new DatagramPacket(msg, msg.length);
                bsocket.receive(packet);

                msg = packet.getData();
                String strMessage = extractMSG(msg);
                ShellIMApp.outputText.append(strMessage);
                System.out.println("finished checking for packets....\n");

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String args[]) {
        System.out.println("Hello from a thread!_2");
        try {
            new Thread(new NewClientThread()).start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    protected String extractMSG(byte[] p) {
        System.out.println("size to calculate: " + p[33]);
        String msg = new String(p,34,p[33]);
        System.out.println("extracting message" + msg);
        return msg;
    }

    public NewClientThread() throws IOException{

        System.out.println("Hello from a thread!_3");
        message = new byte[256];
        address = InetAddress.getLocalHost();
        IP = address.getAddress();
        host = address.getHostName();

    }


//    protected void listen_BCAST() {
//
//
//    }

}