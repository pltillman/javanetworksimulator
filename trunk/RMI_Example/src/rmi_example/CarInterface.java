import java.rmi.*;

public interface CarInterface extends Remote {

    public int getType() throws java.rmi.RemoteException;

    public int getServices() throws java.rmi.RemoteException;

    public void setType(int i) throws java.rmi.RemoteException;

    public void setServices(int i) throws java.rmi.RemoteException;
    
}
