import java.rmi.*;

public interface CallbackServerInterface extends Remote {

    public void registerForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException;

    public void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException;

    public void doCallbacks(String c) throws RemoteException;

    public void tellServerAboutCar(String n) throws RemoteException;

    public void sendCarToWash(int i, String s) throws RemoteException;

}
