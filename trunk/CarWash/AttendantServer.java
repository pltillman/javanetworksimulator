import java.rmi.*;
//import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.util.PriorityQueue;


public class AttendantServer {
    public static PriorityQueue<String> carQ;          // Array to hold all the car references
    private static PriorityQueue<String> waitingQ;      // Array to hold the cars waiting to be serviced
    private static PriorityQueue<String> busyQ;         // Array to hold the cars that couldn't
                                            // be serviced by the available wash station

    private String prefix = "rmi://localhost:1099/";
    private static CallbackServerInterface h;
    // Array to hold the busy status of each wash station
    private static Boolean[] washBusyStatus = new Boolean[4];

    public static void main(String args[]) {
        washBusyStatus[0] = false;
        washBusyStatus[1] = false;
        washBusyStatus[2] = false;
        washBusyStatus[3] = false;

        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);

        String portNum, registryURL;
        int RMIPortNum;

        try {
            // code for port number value to be supplied
            RMIPortNum = 1099;
            portNum = "1099";
            startRegistry(RMIPortNum);
            // register the object under the name "car"
            //registryURL = "rmi://localhost:" + portNum + "/car";

            h = (CallbackServerInterface)Naming.lookup("callback");

            CallbackClientInterface callback = (CallbackClientInterface)new CallbackClientImpl();

            h.registerForCallback(callback);

            System.out.println("Car Server ready.");
        } catch (Exception e) {
            System.out.println("Exception in CarServer: " + e);
        }

        while (true) {

            Boolean busy = washBusyStatus[0] || washBusyStatus[1] || washBusyStatus[2] || washBusyStatus[3];
            if (!carQ.isEmpty() && busy) {
                String s = carQ.peek();

                try {
                    Car tmp = (Car)Naming.lookup(s);
                    int carType = tmp.getType();

                    while (carType >= 0) {
                        if (washBusyStatus[carType]) {
                            h.sendCarToWash(carQ.poll());
                        } else {
                            carType--;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Exception occurred");
                }

                for (int i=0; i<washBusyStatus.length; i++) {
                    if (washBusyStatus[i]) {
                        h.sendCarToWash(carQ);

                    }
                }
            }

        }
    }

    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            // The above call will throw an exception
            // if the registry does not already exist

        } catch (RemoteException re) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port: " + RMIPortNum);
        }
    }

    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (int i=0; i<names.length; i++)
            System.out.println(names[i]);
    }

    public static void updateWashStatus(int id, Boolean f) {
        washBusyStatus[id] = f;
    }
    public void addCar(String c) {
        carQ.add(c);
    }
    public static void receiveCar(Car c) {
        
    }
}
