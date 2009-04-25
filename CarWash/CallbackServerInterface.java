import java.rmi.*;

public interface CallbackServerInterface extends Remote {

    public void registerForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException;

    public void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException;

    public void doCallbacks(String c)
            throws RemoteException;

    public void tellServerAboutCar(String c) throws RemoteException;

    public void sendCarToWash(String s) throws RemoteException;

}
