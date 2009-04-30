import java.rmi.*;
import java.rmi.server.*;


public class Car extends UnicastRemoteObject implements CarInterface {

    public int type;
    public int services;
    public int washTime;
    public long arrivalTime;
    public long exitTime;
    public float waitTime;

    public Car() throws RemoteException {
        super();
        //this.type = 1;
    }
    public int getType() throws RemoteException {
        return this.type;
    }
    public int getServices() throws RemoteException {
        return this.services;
    }
    public void setType(int i) throws RemoteException {
        this.type = i;
    }
    public void setServices(int i) throws RemoteException {
        this.services = i;
    }
    public Car getCar() throws RemoteException {
        return this;
    }
	 public void setWashTime(int i) throws RemoteException {
	 	  this.washTime = i;
	 }
	 public int getWashTime() throws RemoteException {
	 	  return this.washTime;
	 }
	 public void setArrivalTime(long i) throws RemoteException {
	 	  this.arrivalTime = i;
	 }
	 public long getArrivalTime() throws RemoteException {
	 	  return this.arrivalTime;
	 }
	 public void setExitTime(long i) throws RemoteException {
	 	  this.exitTime = i;
	 }
	 public long getExitTime() throws RemoteException {
	 	  return this.exitTime;
	 }
	 public float getWaitTime() throws RemoteException {
	 	  float wait = this.exitTime  - this.arrivalTime;
		  wait /= 1000;
		  this.waitTime = wait;
		  return this.waitTime;
	 }

}
