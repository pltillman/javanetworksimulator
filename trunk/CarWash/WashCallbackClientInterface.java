import java.rmi.RemoteException;
import java.rmi.Remote;

public interface WashCallbackClientInterface extends Remote {

    public String notifyMe(String m) throws RemoteException;

    public void receiveCar(Car c) throws RemoteException;

    public void notifyAttendent(int c, Boolean f) throws RemoteException;

    public Boolean carAvailable() throws RemoteException;

}
