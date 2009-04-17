
import java.rmi.*;

public interface CallbackClientInterface extends Remote {

    public String notifyMe(String m) throws java.rmi.RemoteException;

}
