import java.rmi.*;

public interface WashBayInterface extends Remote {

    public int getType() throws RemoteException;

    public void setType(int i) throws RemoteException;

    public void setCarReady(Boolean f) throws RemoteException;

    public Boolean carReady() throws RemoteException;

    public void setCar(String c) throws RemoteException;

    public String getCar() throws RemoteException;

    public void setisReady(Boolean f) throws RemoteException;

    public Boolean isReady() throws RemoteException;
}
