import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {

    
    Registry registry;


    public CallbackClientImpl() throws RemoteException {
        super();
        registry = LocateRegistry.getRegistry("localhost",1099);
    }

    public String notifyMe(String message) {
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }

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
     * @param n
     */
    public void notifyAttendent(String n) {
        //Car car = c;
        //try {

            //registry.rebind(n, car);
            AttendantServer.carQ.add(n);
            System.out.println(AttendantServer.carQ.toString());
            
            //AttendantServer.receiveCar(car);
        
//        } catch (RemoteException re) {
//            System.out.println("Remote Exception");
//        }
    }

}
