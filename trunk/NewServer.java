import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.lang.Thread;

/**************************************************************
 *
 * @author patrick tillman
 **************************************************************/
public class NewServer extends Thread {

    protected DatagramSocket serv_socket = null;
    protected DatagramPacket packet = null;
    protected final int DEFAULT_PORT = 4449, BCAST_PORT = 7872;
    protected final int expiration = 2000, THREE_SECONDS = 3000;
    protected byte[] constrPacket = new byte[256];
    public static ArrayList<rt_entry> RoutingTable = new ArrayList<rt_entry>();
    protected InetAddress address;
    protected byte[] IP;
    protected String local_ip;
    protected String host;
    protected Thread t;
	



    public static void main(String[] args) throws IOException {

        new NewServer("s1").start();

    }


    /**************************************************************
     * Default constructor
     * 
     * @param n
     * @throws java.io.IOException
     **************************************************************/
    public NewServer(String n) throws IOException {

        super(n);
        
        t = new Thread(new NewServerThread());
        t.start();
        

        listen();
        
        address = InetAddress.getLocalHost();
        IP = address.getAddress();
        host = address.getHostName();
    }

    /**************************************************************
     *  Method to listen for any incoming traffic
     *
     **************************************************************/
    protected void listen() {
        
        try {
            serv_socket = new DatagramSocket(DEFAULT_PORT);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        while (!serv_socket.isClosed()) {

            byte[] message = new byte[256];

            packet = new DatagramPacket(message, message.length);

            try {
                serv_socket.receive(packet);
					 
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            byte[] rcvdPacket = packet.getData();
            System.out.println("Packet received...");
            
            rt_entry target = new rt_entry(rcvdPacket, extractIP(rcvdPacket));
            byte[] br = rtLookup(target);

            int i = rcvdPacket[0];
            switch(i) {
                case 0:
                    if (true) {
                        System.out.println("rt lookup flag bit detected");
                        //byte [] yum = updatePacket(rcvdPacket, br);
                        
                        try {
                            MulticastSocket socket = new MulticastSocket(BCAST_PORT);
                            InetAddress group = InetAddress.getByName("224.0.0.1");
                            //byte f = 1;
                            //byte[] msg = makePacket(f, host, IP, "Test");
                            packet = new DatagramPacket(rcvdPacket, rcvdPacket.length, group, BCAST_PORT);
                            socket.send(packet);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }

                    } else {
                        System.out.println("IP is not registered");
                        RoutingTable.add(target);
                    }
                    break;
                case 1:
                    if (br != null) {
                        System.out.println("rt.add flag bit detected");
                        RoutingTable.add(target);
                    } else {
                        System.out.println(" flag = 1 else");
                    }
                    break;
                default:
                    System.out.println("unrecognized flag bit");
                    break;
            }

            System.out.println("Host: " + extractDestHost(rcvdPacket) + "\tIP: "
                    + extractIP(rcvdPacket) + "\tMSG: " + extractMSG(rcvdPacket));

            //serv_socket.close();
        }

    }





    /**************************************************************
     * Method to create a structured packet.
     *
     * @param f - Flag bit
     * @param host - Destination host name
     * @param ip - Destination IP address
     * @param message - Message to be encoded
     * @return - a byte[] array that will be sent as a datagram packet
     *************************************************************/

    protected byte[] makePacket(byte f, String host, byte[] ip, String message, int l) {

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
     * Method to extract a String representation of the Dest Host
     * embedded in the packet.
     *
     * @param p - The received packet.
     * @return - String version of the destination host.
     **************************************************************/
    protected String extractDestHost(byte[] p) {
        byte[] h = new byte[28];
        
        for (int j=5,k=0; j<33; j++) {
            //System.out.println("packet " + p[j]);
            h[k++] = p[j];
        }
        String DestHost = new String(h,0,h.length);
        return DestHost;

    }

    /**************************************************************
     * Method that extracts a String representation of the IP
     * address from the received packet.
     *
     * @param p - The received packet
     * @return - String representation of the IP address
     **************************************************************/
    protected String extractIP(byte[] p) {
        byte[] ip = new byte[4];
        for (int j=1,k=0; j<5; j++) {
            ip[k] = p[j];
            //ystem.out.println("packet at index " + j + "\t" + p[j]);
            k++;
        }
        return getIPString(ip);
    }

    /**************************************************************
     * Method that extracts the message from the received packet.
     *
     * @param p - The received packet
     * @return - String represenation of the embedded message to be sent
     **************************************************************/
    protected String extractMSG(byte[] p) {
        String msg = new String(p,34,p[33]);
        //System.out.println(msg);
        return msg;
    }

    /**************************************************************
     * Method to replace the packet's IP address with the resolved
     * address from the Routing Table.
     *
     * @param n - The recevied packet.
     **************************************************************/
    protected byte[] updatePacket(byte[] n, byte[] o) {
        for (int i=1,k=0; i<5; i++) {
            n[i] = o[k++];
        }
        System.out.println("Packet updated with new IP");
        return n;
    }

    
    /**************************************************************
     * Method to forward packet on to the destination host.
     *
     * @param p
     **************************************************************/
    protected void fowardPacket(byte[] p) {

        MulticastSocket socket = null;

        try {
            socket = new MulticastSocket(BCAST_PORT);
            InetAddress group = InetAddress.getByName("224.0.0.1");
            packet = new DatagramPacket(p, p.length, group, BCAST_PORT);
            socket.send(packet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        socket.close();
    }


    /**************************************************************
     * Method that will take the packet, extract pertinent data from
     * it and then add it to the routing table.
     *
     * @param b - The received packet.
     **************************************************************/
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

    /**************************************************************
     * Method to lookup in the Routing Table for an existing entry.
     *
     * @param n - Routing table entry
     * @return - byte[] representation of the found IP, otherwise NULL
     **************************************************************/
    private byte[] rtLookup(rt_entry n) {
        byte[] ip = null;
        System.out.println("Searching for ip....");
        for (int j=0;j<RoutingTable.size();j++) {
            if (n.getHost().equals(RoutingTable.get(j).getHost())) {
                ip = RoutingTable.get(j).getIP();
                System.out.println("Entry found in routing table");
            }
        }
        return ip;
    }

    /**************************************************************
     * Method to get the String representation of IP address from the packet.
     *
     * @param o - The received packet.
     * @return - String representation of the IP address.
     **************************************************************/
    private String getIPString(byte[] o) {
        String ipStr = null;
        for (int index=0; index<o.length; index++) {
            if (index > 0) {
                ipStr += ".";
            }
            ipStr += ((int)o[index]) & 0xff;
        }
        ipStr = ipStr.substring(4);
        return ipStr;
    }




}
