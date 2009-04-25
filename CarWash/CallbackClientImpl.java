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

    public Car receiveCar(Car car) throws RemoteException {
        System.out.println("Car recieved \tType: " + car.getType());
        return car;
    }

    public void notifyAttendent(String c) {
        Car car = null;
        try {
            car = (Car)registry.lookup(c);
            AttendantServer.receiveCar(c);
        } catch (NotBoundException nbe) {
            nbe.printStackTrace();
        } catch (RemoteException re) {
            System.out.println("Remote Exception");
        }
    }

}
