import java.rmi.*;
import java.rmi.server.*;

public class WashCallbackClientImpl extends UnicastRemoteObject implements WashCallbackClientInterface {

    Car c = new Car();
    private Boolean receivedcar;


    public WashCallbackClientImpl() throws RemoteException {
        super();
    }

    public String notifyMe(String message) {
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }

    // Receives the car from the server
    public void receiveCar(Car car) throws RemoteException {
        System.out.println("Car recieved \tType: " + car.getType());
        c = car;
    }

    // Returns the received car to the client wash station for processing
    public Car getCar() {
        return c;
    }

    // Returns a boolean indicating whether or not a car has been received
    public Boolean carAvailable() {
        return receivedcar;
    }

    // Notifies the attendent that a wash station is available
    public void notifyAttendent(int t, Boolean f) throws RemoteException {
        AttendantServer.updateWashStatus(t, f);
    }

}
