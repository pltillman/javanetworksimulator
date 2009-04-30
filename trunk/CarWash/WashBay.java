import java.rmi.*;
import java.rmi.server.*;


public class WashBay extends UnicastRemoteObject implements WashBayInterface {

    public int type;
    private Boolean carReady, isReady;
    private String car;

    public WashBay() throws RemoteException {
        super();
        this.isReady = false;
        this.carReady = false;
    }
    public void setisReady(Boolean f) throws RemoteException {
        this.isReady = f;
    }
    public Boolean isReady() throws RemoteException {
        return this.isReady;
    }
    public int getType() throws RemoteException {
        return this.type;
    }
    public void setType(int i) throws RemoteException {
        this.type = i;
    }
    public void setCarReady(Boolean f) throws RemoteException {
        this.carReady = f;
    }
    public Boolean carReady() throws RemoteException {
        return this.carReady;
    }
    public void setCar(String c) throws RemoteException {
        this.car = c;
    }
    public String getCar() throws RemoteException {
        return this.car;
    }




}
