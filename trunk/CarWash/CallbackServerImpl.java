import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;
import java.rmi.registry.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    private Vector clientList;
    private Registry registry;
    private int[] washStationList;

    /**
     * Default constructor for CallbackServerImpl
     *
     * @throws java.rmi.RemoteException
     */
    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new Vector();
        registry = LocateRegistry.getRegistry("localhost",1099);
    }

    /**
     * Allows clients to register for a callback
     *
     * @param callbackClientObject - client object to add to the client list
     * @throws java.rmi.RemoteException
     */
    public void registerForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException {
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            //System.out.println("registered new client ");
            //doCallbacks();
        }
    }

    /**
     * Allows client to unregister for a callback
     *
     * @param callbackClientObject - client object to remove from the client list
     * @throws java.rmi.RemoteException
     */
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject)
            throws RemoteException {
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println("unregister: client wasn't registerd.");
        }
    }

    /**
     * Method that send a car reference to the specified bay
     *
     * @param id - int - Wash bay id - Determines which bay car will be sent
     * @param s - String - Car object reference
     * @throws java.rmi.RemoteException
     */
    public synchronized void sendCarToWash(int id, String s) throws RemoteException {
        if (id > -1 && id < 4) {
            CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(id);
            nextClient.receiveCar(s);
        } else {
            System.out.println("Invalid station ID");
        }
    }

    /**
     * Method that allows the attendant to be notified that a new car has arrived
     * 
     * @param n - String - Car object reference
     * @throws java.rmi.RemoteException
     */
    public synchronized void tellServerAboutCar(String n) throws RemoteException {
        System.out.println("******************************");

        System.out.println("Notify attendant of new car\n");
        // convert the vector object to a callback object
        CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(0);
        // invoke the callback method
        nextClient.notifyAttendent(n);
        
        System.out.println("************************\n");
    }
}
