import java.rmi.*;

public interface CallbackServerInterface extends Remote {

    public String sayHello()
            throws java.rmi.RemoteException;

    public void registerForCallback(CallbackClientInterface callbackClientObject)
            throws java.rmi.RemoteException;

    public void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws java.rmi.RemoteException;

    public void doCallbacks()
            throws java.rmi.RemoteException;

}
