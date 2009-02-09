
public class PacketStruct {



    // we will likely need to track at least the following attributes, possibly more.
    // this class will implement the serializable interface

    private String origIP; //sender's ip address;
    private String destHOST; // destination hostname;
    private String message;  // outgoing message;
    private int flag;

    public void PacketStruct (String s_IP, String d_host, int f, String m ) {

        // call calc local ip

        this.origIP = s_IP;
        this.destHOST = d_host;
        this.flag = f;
        this.message = m;
    }

    public String get_OrigIP () {
        return this.origIP;
    }

    public String get_destHOST () {
        return this.destHOST;
    }
    
    public int get_flag () {
        return this.flag;
    }

    public String get_message() {
        return this.message;
    }
    
    public void set_OrigIP (String ip) {
        this.origIP = ip;
    }

    public void set_destHOST (String d) {
        this.destHOST = d;
    }

    public void set_flag (int f) {
        this.flag = f;
    }


}





