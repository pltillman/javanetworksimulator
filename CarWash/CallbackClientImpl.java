import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {

    Registry registry;


    /**
     * Default constructor for callback client
     *
     * @throws java.rmi.RemoteException
     */
    public CallbackClientImpl() throws RemoteException {
        super();
        registry = LocateRegistry.getRegistry("localhost",1099);
    }


//    public String notifyMe(String message) {
//        String returnMessage = "Call back received: " + message;
//        System.out.println(returnMessage);
//        return returnMessage;
//    }

    
    /**
     * Receives the car and attempts to locate it in the RMI registry
     *
     * @param car - String - Car object reference
     * @return - Car - Car object found in the Registry
     * @throws java.rmi.RemoteException
     */
    public Car receiveCar(String car) throws RemoteException {
        System.out.println("Car recieved \tType: " + car);
        Car c = null;
        try {
            c = (Car)registry.lookup(car);
        } catch (NotBoundException nbe) {
            System.out.println("Not Bound Exception");
        }
        return c;
    }

    /**
     * Adds the car to the attendant's car queue
     *
     * @param n - String - Car object reference
     */
    public void notifyAttendent(String n) {

        AttendantServer.carQ.add(n);
        System.out.println(AttendantServer.carQ.toString());

    }

}
