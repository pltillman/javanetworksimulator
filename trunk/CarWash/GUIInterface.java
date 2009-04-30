import java.rmi.*;

public interface GUIInterface extends Remote {

    public void generateAttendantOutput(String f) throws RemoteException;

    public void generateWashOutput(String f) throws RemoteException;

    public void setReady(Boolean f) throws RemoteException;
    
    public Boolean getReady() throws RemoteException;

    public void setCarMax(int i) throws RemoteException;

    public void setCarInterval(int i) throws RemoteException;

    public int getCarMax() throws RemoteException;

    public int getCarInterval() throws RemoteException;

    public void setBayStatus(int i, int j) throws RemoteException;

    public int getBayStatus(int j) throws RemoteException;


}
