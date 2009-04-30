import java.rmi.*;
import java.rmi.server.*;


public class GUI extends UnicastRemoteObject implements GUIInterface {

    private String washOutput, attendantOutput;
    private int bay1Status, bay2Status, bay3Status, bay4Status, carMax, carInterval;
    private Boolean ready;

    public GUI() throws RemoteException {
        super();
        bay1Status = 0;
        ready = false;

    }
    public void generateAttendantOutput(String f) throws RemoteException {
        this.attendantOutput = f;
    }
    public void generateWashOutput(String f) throws RemoteException {
        this.washOutput = f;
    }
    public void setReady(Boolean f) throws RemoteException {
        this.ready = f;
    }
    public Boolean getReady() throws RemoteException {
        return this.ready;
    }
    public void setCarMax(int i) throws RemoteException {
        this.carMax = i;
    }
    public void setCarInterval(int i) throws RemoteException {
        this.carInterval = i;
    }
    public int getCarMax() throws RemoteException {
        return this.carMax;
    }
    public int getCarInterval() throws RemoteException {
        return this.carInterval;
    }
    /**
     *
     * @param i
     *          = 0 - Ready
     *          = 1 - Washing
     * @throws java.rmi.RemoteException
     */
    public void setBay1Status(int i) throws RemoteException {
        if (i == 1 || i == 2)
            this.bay1Status = i;
        else
            System.out.println("Invalid status");
    }
    public void setBay2Status(int i) throws RemoteException {
        if (i == 1 || i == 2)
           this.bay2Status = i;
        else
            System.out.println("Invalid status");
    }
    public void setBay3Status(int i) throws RemoteException {
        if (i == 1 || i == 2)
            this.bay3Status = i;
        else
            System.out.println("Invalid status");
    }
    public void setBay4Status(int i) throws RemoteException {
        if (i == 1 || i == 2)
            this.bay4Status = i;
        else
            System.out.println("Invalid status");
    }



    public int getBay1Status() throws RemoteException {
        return this.bay1Status;
    }
    public int getBay2Status() throws RemoteException {
        return this.bay2Status;
    }
    public int getBay3Status() throws RemoteException {
        return this.bay3Status;
    }
    public int getBay4Status() throws RemoteException {
        return this.bay4Status;
    }

}
