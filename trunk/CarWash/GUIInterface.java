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

    public void setBay1Status(int i) throws RemoteException;

    public void setBay2Status(int i) throws RemoteException;

    public void setBay3Status(int i) throws RemoteException;

    public void setBay4Status(int i) throws RemoteException;

    public int getBay1Status() throws RemoteException;

    public int getBay2Status() throws RemoteException;

    public int getBay3Status() throws RemoteException;

    public int getBay4Status() throws RemoteException;


}
