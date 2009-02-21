import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class NewServer extends Thread {

    protected DatagramSocket serv_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4449;
    protected final int BCAST_PORT = 7872;
    protected final int expiration = 2000;
    protected final int THREE_SECONDS = 3000;
    protected byte[] constrPacket = new byte[256];
    private static ArrayList<rt_entry> RoutingTable = new ArrayList<rt_entry>();
    protected InetAddress address;
    protected byte[] IP;
    protected String local_ip;


    public static void main(String[] args) throws IOException {

        new NewServer("s1");
    }


    public NewServer(String n) throws IOException {

        super(n);

        listen();
        System.out.println("after listen");
        byte[] message = new byte[256];

        address = InetAddress.getLocalHost();
        IP = address.getAddress();

        packet = new DatagramPacket(message, message.length);
        serv_socket = new DatagramSocket(DEFAULT_PORT);

        serv_socket.receive(packet);
        while (!serv_socket.isClosed()) {
            System.out.println("inside");
            byte[] rcvdPacket = packet.getData();

            rt_entry target = new rt_entry(rcvdPacket, extractIP(rcvdPacket));
            byte[] br = rtLookup(target);


            int i = rcvdPacket[0];
            switch(i) {
                
                case 0:
                    if (br != null) {
                        System.out.println("rt lookup flag bit detected");
                        updatePacket(br);
                    } else {
                        System.out.println("IP is not registered");
                        RoutingTable.add(target);
                        //sendMessage() to client.. undeliverable flag = 3
                    }
                    break;

                case 1:                    
                    if (br != null) {
                        System.out.println("rt.add flag bit detected");
                        RoutingTable.add(target);
                    } else {
                        System.out.println(" flag = 1 else");
                        //reply with ack message
                    }
                    break;
                    
                default:
                    System.out.println("unrecognized flag bit");
                    break;
            }
            
            //String received = new String(rcvdPacket, 0, rcvdPacket.length);
            System.out.println("Host: " + extractDestHost(rcvdPacket) + "\tIP: "
                    + extractIP(rcvdPacket));

            serv_socket.close();
        }
        

    }


    // We may want to add the listening piece to the server. I suppose
    // it makes more sense that way.
    protected void listen() throws IOException {

        //Boolean added = false;
        InetAddress b_address = null;
        MulticastSocket bsocket = null;

        b_address = InetAddress.getByName("230.0.0.1");
        bsocket = new MulticastSocket(BCAST_PORT);
        bsocket.joinGroup(b_address);

        while (!bsocket.isClosed()) {

            // Create a broadcast range & socket and then bind them

            // Create an emtpy packet and call to recieve
            byte[] msg = new byte[256];
            packet = new DatagramPacket(msg, msg.length);
            bsocket.receive(packet);

            msg = packet.getData();
            // Convert the byte[] to String and print it
            
            System.out.println("Received message: " + msg[0] + " \t"+ extractDestHost(msg) + " \t IP " + extractIP(msg));

            // Check the message for a broadcast call
            if (msg[0] == 1) {
                System.out.println("Closing socket...");
                bsocket.leaveGroup(b_address);
                bsocket.close();
            //setup a new socket and send ip back to the server
            //add code here
            }

        }
    }



    protected byte[] makePacket(byte f, String host, byte[] ip, String message) {

        constrPacket[0] = f;
        byte[] b = message.getBytes();
        byte[] h = host.getBytes();

        for (int y=1,k=0; y<5; y++) {
            constrPacket[y] = ip[k++];
        }
        for (int j=5, l=0; j<h.length; j++) {
            constrPacket[j] = h[l++];
        }
        for (int p=33,w=0; p<256; p++) {
            constrPacket[p] = b[w++];
        }
        return constrPacket;
    }


    
    protected String extractDestHost(byte[] p) {
        byte[] h = new byte[28];
        
        for (int j=5,k=0; j<33; j++) {
            //System.out.println("packet " + p[j]);
            h[k++] = p[j];
        }
        String DestHost = new String(h,0,h.length);
        return DestHost;

    }

    protected String extractIP(byte[] p) {
        byte[] ip = new byte[4];
        for (int j=1,k=0; j<5; j++) {
            ip[k] = p[j];
            k++;
        }
        return getIPString(ip);
    }

    protected void updatePacket(byte[] n) {
        for (int i=1,k=0; i<5; i++) {
            constrPacket[i] = n[k++];
        }
    }
    protected void populate_rt(byte[] b) {
        byte[] ip = null;
        byte[] dh = null;
        //String ip_str = null;
        String dest_host = null;
        for (int i=1, j=0; i<5; i++) {
            ip[j++] = b[i];
        }
        for (int k=5,l=0; k<33; k++) {
            dh[l++] = b[k];
        }
        //ip_str = new String(ip,0,ip.length);
        dest_host = new String(dh,0,dh.length);
        RoutingTable.add(new rt_entry(ip, dest_host));
    }

    private byte[] rtLookup(rt_entry n) {
        byte[] ip = null;
        
        for (int j=0;j<RoutingTable.size();j++) {
            if (n.getHost().equals(RoutingTable.get(j).getHost())) {
                ip = RoutingTable.get(j).getIP();
                System.out.println("Added entry to routing table");
            }
        }
        return ip;
    }

    private String getIPString(byte[] o) {
        String ipStr = null;
        for (int index=0; index<o.length; index++) {
            if (index > 0) {
                ipStr += ".";
                //System.out.print(".");
            }
            ipStr += ((int)o[index]) & 0xff;
            //System.out.println("index " + index + (((int)o[index]) & 0xff));
            //System.out.print(((int)IP[index])& 0xff);

        }
        ipStr = ipStr.substring(4);
        return ipStr;
    }
    
    class rt_entry {

        protected byte[] ip;
        protected String destHost;

        public rt_entry(byte[] i, String h) {
            this.ip = i;
            this.destHost = h;
        }

        public byte[] getIP() {
            return this.ip;
        }
        public String getHost() {
            return this.destHost;
        }
        
        
    }
}
