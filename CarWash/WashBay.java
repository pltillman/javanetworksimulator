import java.rmi.*;
import java.rmi.server.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class WashBay extends UnicastRemoteObject implements WashBayInterface {

    public int type;
    private Boolean carReady, isReady;
    private String car;

    /**
     * WashBay default constructor
     *
     * @throws java.rmi.RemoteException
     */
    public WashBay() throws RemoteException {
        super();
        this.isReady = false;
        this.carReady = false;
    }

    /**
     * Sets the ready status of the washbay object
     *
     * @param f - Boolean
     * @throws java.rmi.RemoteException
     */
    public void setisReady(Boolean f) throws RemoteException {
        this.isReady = f;
    }
    /**
     * Returns the ready status of the washbay object
     *
     * @return Boolean - ready status
     * @throws java.rmi.RemoteException
     */
    public Boolean isReady() throws RemoteException {
        return this.isReady;
    }
    /**
     * Returns the wash bay type
     *
     * @return - int - indicating what car type the bay can service
     * @throws java.rmi.RemoteException
     */
    public int getType() throws RemoteException {
        return this.type;
    }
    /**
     * Sets the wash bay type
     *
     * @param i - int - Represents bay type
     * @throws java.rmi.RemoteException
     */
    public void setType(int i) throws RemoteException {
        this.type = i;
    }
    /**
     * Sets the car ready status of the wash bay. Indicates when a car is ready for washing
     *
     * @param f - Boolean
     * @throws java.rmi.RemoteException
     */
    public void setCarReady(Boolean f) throws RemoteException {
        this.carReady = f;
    }
    /**
     * Returns the car ready status of the wash bay.
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public Boolean carReady() throws RemoteException {
        return this.carReady;
    }
    /**
     * Sets the car for this bay
     *
     * @param c - String - Reference to the car object in the registry
     * @throws java.rmi.RemoteException
     */
    public void setCar(String c) throws RemoteException {
        this.car = c;
    }
    /**
     * Returns the car reference currently being used by this bay
     *
     * @return
     * @throws java.rmi.RemoteException
     */
    public String getCar() throws RemoteException {
        return this.car;
    }




}
