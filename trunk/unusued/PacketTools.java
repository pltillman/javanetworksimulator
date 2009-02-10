
/**
 * Encode and Decode methods that will create packet
 * objects, and retrieve messages from packet objects respectively.
 *
 */
public class PacketTools {

    PacketStruct packet;
    

    public PacketTools() {

    }

    public Boolean encode(String h, String d, int f, String m) {

        //make call to get local sender's ip

        //create new packet
        if ( h.length() > 0 && h.length() > 0 && m.length() > 0 ) {
            packet = new PacketStruct(h,d,f,m)
            return true;
        } else {
            return false;
        }

    }

    public String decode(PacketStruct p) {
        
        return p.get_message();

    }

}


//
//currentSender;
//message;
//flag;
//
//encode (host, message, flags) return boolean
//
//to encode a message into a packet object,
//    get the sender's ip address using java calls
//
//    create a new packet object from the sender's ip, and the passed parameter values
//    if the object was created successfully, return true, otherwise return false.
//
//
//
//decode (object) returns the sent message
//
//to decode the message
//    create a temporary packet object
//    call object method to return the object's message
//    return the message
