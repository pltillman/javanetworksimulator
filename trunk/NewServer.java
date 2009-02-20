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

        callForClients();

        byte[] message = new byte[256];

        packet = new DatagramPacket(message, message.length);
        serv_socket = new DatagramSocket(DEFAULT_PORT);
        
        InetAddress local_addr = InetAddress.getLocalHost();
        System.out.println(local_addr);
        for (int j=0; j<10; j++) {
            serv_socket.receive(packet);
            byte[] rcvdPacket = packet.getData();
            String s = null;

            address = InetAddress.getLocalHost();
            IP = address.getAddress();

            // Get a string representation of the local ip address
            for (int index=0; index<IP.length; index++) {
                if (index > 0) {
                    local_ip += ".";
                }
                local_ip += ((int)IP[index]) & 0xff;
            }
            rt_entry target = new rt_entry(rcvdPacket, local_ip);

            if (rcvdPacket[0] == 0) {
                
                byte[] br = rtLookup(target);
                if (br != null) {
                    updatePacket(br);
                } else {
                    //sendMessage() to client.. undeliverable flag = 3
                }

            } else if (rcvdPacket[0] == 1) {
                byte[] br = rtLookup(target);
                if (br != null) {
                    RoutingTable.add(target);
                } else {
                    //reply with ack message
                }
            }
            
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received + "\t" + packet.getLength() + "\t" + packet.getOffset()
                    + "\t" + packet.getPort());
        }
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
            }
        }
        return ip;
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
