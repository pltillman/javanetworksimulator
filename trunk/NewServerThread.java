import java.io.*;
import java.net.*;



public class NewServerThread implements Runnable {
	
    protected final int DEFAULT_PORT = 4448, BCAST_PORT = 7872;
    protected DatagramPacket packet = null;    
    protected byte[] msg;
    protected InetAddress me;
    protected byte[] myIP;

    
	public NewServerThread() {
		
		try {
		me = InetAddress.getLocalHost();
		myIP = me.getAddress();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
	}
	
	public void run() {
		
		try {
			me = InetAddress.getLocalHost();
			myIP = me.getAddress();
			} catch (UnknownHostException uhe) {
				uhe.printStackTrace();
			}
			
		try {
			listen_BCAST();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		
	}
	
    /**************************************************************
     * Method that instructs the Server to listent for any incoming
     * broadcasts. These broadcasts will extract the pertinent data
     * and update the Routing Table accordingly.
     *
     * @throws java.io.IOException
     **************************************************************/
    protected void listen_BCAST() throws IOException {

        //Boolean added = false;
        InetAddress b_address = null;
        MulticastSocket bsocket = null;

        b_address = InetAddress.getByName("224.0.0.1");
        bsocket = new MulticastSocket(BCAST_PORT);
        bsocket.joinGroup(b_address);

        while (!bsocket.isClosed()) {
				
            // Create an emtpy packet and call to recieve
            msg = new byte[256];
            packet = new DatagramPacket(msg, msg.length);
            bsocket.receive(packet);
            msg = packet.getData();
            rt_entry target = new rt_entry(msg, extractIP(msg));
            byte[] br = rtLookup(target);

            
/*            System.out.println("Received message: " + msg[0] + " \t" 
                    + extractDestHost(msg) + " \t IP " + extractIP(msg)
                    + "\tMSG: " + extractMSG(msg));*/

            // Check the message for a broadcast call
            if ((msg[0] == 1 && br == null)) {
//                System.out.println("Closing broadcast socket...");
//                bsocket.leaveGroup(b_address);
//                bsocket.close();
            	System.out.println("reply packet length: " + myIP.length);
            	DatagramSocket reply = new DatagramSocket();
            	packet = new DatagramPacket(myIP, myIP.length, packet.getAddress(), DEFAULT_PORT);
            	reply.send(packet);
                NewServer.RoutingTable.add(target);
                System.out.println("Entry added to routing table");
            //setup a new socket and send ip back to the server
            //add code here
            } else {
                System.out.println("Flag bit = " + msg[0] + " and " + br);
            }

        }
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
        NewServer.RoutingTable.add(new rt_entry(ip, dest_host));
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
        for (int j=0;j<NewServer.RoutingTable.size();j++) {
            if (n.getHost().equals(NewServer.RoutingTable.get(j).getHost())) {
                ip = NewServer.RoutingTable.get(j).getIP();
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
