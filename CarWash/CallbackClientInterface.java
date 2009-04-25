import java.rmi.RemoteException;
import java.rmi.Remote;

public interface CallbackClientInterface extends Remote {

    public String notifyMe(String m) throws RemoteException;

    public Car receiveCar(Car c) throws RemoteException;

    public void notifyAttendent(String s) throws RemoteException;

}
