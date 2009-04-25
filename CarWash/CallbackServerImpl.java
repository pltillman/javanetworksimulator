import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.rmi.registry.*;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    private Vector clientList;
    private Registry registry;
    private int[] washStationList;
    
    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new Vector();
        registry = LocateRegistry.getRegistry("localhost",1099);
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

    public synchronized void sendCarToWash(int id, String s) throws RemoteException {
        if (id > 0 && id < 4) {

        } else {
            System.out.println("Invalid station ID");
        }
    }
    
    public synchronized void doCallbacks(String c) throws RemoteException {
        System.out.println("******************************");
        
        for (int i=0; i<clientList.size(); i++) {
            System.out.println("doing " + i + " -th callback\n");
            // convert the vector object to a callback object
            CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients= " + clientList.size());
            nextClient.notifyAttendent(c);
        }
        System.out.println("************************\n" +
                "Server completed callbacks -- ");
    }
    
    public synchronized void tellServerAboutCar(String c) throws RemoteException {
        System.out.println("******************************");

        for (int i=0; i<clientList.size(); i++) {
            System.out.println("doing " + i + " -th callback\n");
            // convert the vector object to a callback object
            CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyAttendent(c);
        }
        System.out.println("************************\n" +
                "Server completed callbacks -- ");
    }
}
