
import java.rmi.*;
import java.rmi.server.*;


public class CarImpl extends UnicastRemoteObject implements CarInterface {

    public int type;
    public int services;

    public CarImpl()  throws RemoteException {
        super();
    }
    public int getType() throws RemoteException {
        return this.type;
    }
    public int getServices() throws RemoteException {
        return this.services;
    }
    public void setType(int i) throws RemoteException {
        this.type = i;
    }
    public void setServices(int i) throws RemoteException {
        this.services = i;
    }

}
