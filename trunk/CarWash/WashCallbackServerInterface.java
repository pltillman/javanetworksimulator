import java.rmi.*;

public interface WashCallbackServerInterface extends Remote {

    public void registerForCallback(WashCallbackClientInterface cb)
            throws java.rmi.RemoteException;

    public void unregisterForCallback(WashCallbackClientInterface cb)
            throws java.rmi.RemoteException;

    public void doCallbacks()
            throws java.rmi.RemoteException;

}
