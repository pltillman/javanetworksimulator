import java.rmi.RemoteException;
import java.rmi.Remote;

public interface CallbackClientInterface extends Remote {

    public String notifyMe(String m) throws RemoteException;

    public Car receiveCar(String c) throws RemoteException;

    public void notifyAttendent(String n) throws RemoteException;

}
