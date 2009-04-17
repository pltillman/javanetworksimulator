
import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    private Vector clientList;
    
    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new Vector();
    }
    public String sayHello() throws RemoteException {
        return ("hello");
    }
    public void registerForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException {
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("registered new client ");
            //doCallbacks();
        }
    }

    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException {
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println("unregister: client wasn't registerd.");
        }
    }

    public synchronized void doCallbacks() throws RemoteException {
        System.out.println("******************************");
        for (int i=0; i<clientList.size(); i++) {
            System.out.println("doing " + i + " -th callback\n");
            // convert the vector object to a callback object
            CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients= " + clientList.size());
        }
        System.out.println("************************\n" +
                "Server completed callbacks -- ");
    }
}
