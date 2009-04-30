import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class Car extends UnicastRemoteObject implements CarInterface {

    public int type;
    public int services;
    public int washTime;
    public long arrivalTime;
    public long exitTime;
    public float waitTime;

    /**
     * Default constructor for the Car object
     * @throws java.rmi.RemoteException
     */
    public Car() throws RemoteException {
        super();
    }
    /**
     * Returns the car type
     *
     * @return - int - Represents car type
     * @throws java.rmi.RemoteException
     */
    public int getType() throws RemoteException {
        return this.type;
    }
    /**
     * Returns the services needed for the car object
     *
     * @return - int - Encoded representation of the services needed
     * @throws java.rmi.RemoteException
     */
    public int getServices() throws RemoteException {
        return this.services;
    }
    /**
     * Sets the car type
     *
     * @param i - int - Represents the car type
     * @throws java.rmi.RemoteException
     */
    public void setType(int i) throws RemoteException {
        if (i > 0 && i < 5) {
            this.type = i;
        } else {
            System.out.println("Invalid car type: " + i);
        }
    }
    /**
     * Sets the services needed for the car
     *
     * @param i - int - Represents the services needed by car
     * @throws java.rmi.RemoteException
     */
    public void setServices(int i) throws RemoteException {
        this.services = i;
    }

    /**
     * Sets the wash service time for the car
     *
     * @param i - int - time it took to wash the car
     * @throws java.rmi.RemoteException
     */
    public void setWashTime(int i) throws RemoteException {
        this.washTime = i;
    }
    /**
     * Returns the wash service time for the car
     *
     * @return - int - time it took to wash the car
     * @throws java.rmi.RemoteException
     */
    public int getWashTime() throws RemoteException {
        return this.washTime;
    }
    /**
     * Sets the arrival time of the car
     *
     * @param i - long - Time the car arrived at the attendant
     * @throws java.rmi.RemoteException
     */
    public void setArrivalTime(long i) throws RemoteException {
        this.arrivalTime = i;
    }
    /**
     * Returns the arrival time of the car
     *
     * @return - long - Time the car arrived at the attendant
     * @throws java.rmi.RemoteException
     */
    public long getArrivalTime() throws RemoteException {
        return this.arrivalTime;
    }
    /**
     * Sets the exit time of the car
     *
     * @param i - long - Time the car exited the wash bay
     * @throws java.rmi.RemoteException
     */
    public void setExitTime(long i) throws RemoteException {
        this.exitTime = i;
    }
    /**
     * Sets the exit time of the car
     *
     * @return - long - Time the car exited the wash bay
     * @throws java.rmi.RemoteException
     */
    public long getExitTime() throws RemoteException {
        return this.exitTime;
    }
    /**
     * Returns the wait time of the car
     *
     * @return - long - Time the car waited before being serviced
     * @throws java.rmi.RemoteException
     */
    public float getWaitTime() throws RemoteException {
        float wait = this.exitTime  - this.arrivalTime;
        wait /= 1000;
        this.waitTime = wait;
        return this.waitTime;
    }

}
