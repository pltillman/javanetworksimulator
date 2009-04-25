import java.rmi.*;

public interface CarInterface extends Remote {

    public int getType() throws RemoteException;

    public int getServices() throws RemoteException;

    public void setType(int i) throws RemoteException;

    public void setServices(int i) throws RemoteException;

    public Car getCar() throws RemoteException;
    
}
