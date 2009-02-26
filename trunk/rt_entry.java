/**************************************************************
 * Class to create a Routing Table object.
 **************************************************************/
public class rt_entry {

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