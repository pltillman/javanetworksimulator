import java.rmi.*;

public interface CarInterface extends Remote {

    public int getType() throws RemoteException;

    public int getServices() throws RemoteException;

    public void setType(int i) throws RemoteException;

    public void setServices(int i) throws RemoteException;

    public Car getCar() throws RemoteException;
	 
	 public void setWashTime(int i) throws RemoteException;
	 
	 public int getWashTime() throws RemoteException;
	 
	 public void setArrivalTime(long i) throws RemoteException;
	 
	 public long getArrivalTime() throws RemoteException;
	 
	 public void setExitTime(long i) throws RemoteException;
	 
	 public long getExitTime() throws RemoteException;
	 
	 public float getWaitTime() throws RemoteException;
    
}
