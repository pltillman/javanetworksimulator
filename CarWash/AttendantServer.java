import java.rmi.*;
//import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class AttendantServer {
    
    public static ArrayList<String> carQ;          // Array to hold all the car references
    private static ArrayList<String> waitingQ;      // Array to hold the cars waiting to be serviced
    private static ArrayList<String> busyQ;         // Array to hold the cars that couldn't
                                                    // be serviced by the available wash station

    private static String prefix = "rmi://localhost:1099/";
    private static CallbackServerInterface serverCallback, cbServer;

    private static Registry registry;
    private GUIInterface gui;
    // Array to hold the busy status of each wash station
    private static WashBay[] washStations;


    /**
     *
     * @param args
     * @throws java.rmi.RemoteException
     */
    public static void main(String args[]) throws RemoteException {
        AttendantServer attendant = new AttendantServer();
    }


    /**
     * 
     * @throws java.rmi.RemoteException
     */
    public AttendantServer() throws RemoteException {

//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new RMISecurityManager());
//        }
//
//        System.setProperty("java.rmi.server.hostname", "10.220.1.204");
//        System.setProperty("java.security.policy", "grant.policy");
        washStations = new WashBay[5];
        carQ = new ArrayList();

        WashBay expObj1, expObj2, expObj3, expObj4, expObj5;
        


        int RMIPortNum;

        try {
            // code for port number value to be supplied
            RMIPortNum = 1099;
            
            startRegistry(RMIPortNum);
            // register the object under the name "car"
            //registryURL = "rmi://localhost:" + portNum + "/car";

            gui = new GUI();
            registry.rebind("GUI", gui);
            
            String sourceCallback = prefix + "" + "sourceCallback";

//            cbServer = (CallbackServerInterface)Naming.lookup("sourceCallback");
//            cbWash = (WashCallbackServerInterface)Naming.lookup("washCallback");

            // Create callback objects for exporting to registry
            serverCallback = (CallbackServerInterface)new CallbackServerImpl();

            // Bind them to the registry with the appropriate references
            Naming.bind(sourceCallback, serverCallback);
            System.out.println("Callbacks added to registry");
            

            // Create the client call backs so that they can be used to receive the callback notifications
            CallbackClientInterface clientCallback = (CallbackClientInterface)new CallbackClientImpl();

            // Register the client objects as callback listeners
            serverCallback.registerForCallback(clientCallback);
            System.out.println("Registered callbacks successfully");
            System.out.println("Car Server ready.");
            


            expObj1 = new WashBay();
            expObj2 = new WashBay();
            expObj3 = new WashBay();
            expObj4 = new WashBay();

            //Registry x = (Registry)registry.lookup("reggy");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Interrupt exception in carSouce " + e);
            }

            registry.rebind("bay1", expObj1);
            registry.rebind("bay2", expObj2);
            registry.rebind("bay3", expObj3);
            registry.rebind("bay4", expObj4);

            washStations[0] = expObj1;
            washStations[1] = expObj2;
            washStations[2] = expObj3;
            washStations[3] = expObj4;


        } catch (Exception e) {
            System.out.println("Exception in Attendant: " + e);
        }

        while (true) {
            
            Boolean busy = washStations[0].isReady() || washStations[1].isReady() || washStations[2].isReady() || washStations[3].isReady();
            System.out.println("Checking status of wash stations..." + busy);
            if (!carQ.isEmpty() && busy && gui.getReady()) {
                System.out.println("Attempting to send car to wash station...");
                System.out.println("Car Q Size: " + carQ.size());

                String s = carQ.remove(0);

                try {
                    CarInterface tmp = (CarInterface)registry.lookup(s);
                    int carType = tmp.getType();
                    System.out.println("Car Type: " + carType);
                    Boolean sent = false;
                    while (carType >= 0 && !sent) {
                        if (washStations[carType].isReady()) {
                            washStations[carType].setCar(s);
                            washStations[carType].setCarReady(true);
                            System.out.println("Transporting car..");
							tmp.setExitTime(System.currentTimeMillis());
                            sent = true;
                        } else {
                            carType--;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Exception occurred in Attendant: " + e);
                }
                
            } else {
                try {
                    System.out.println("Sleeping until we get a car, or a bay comes available, or the GUI is ready");
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    System.out.println("Thread interrupted");
                }
            }
        }

    }


    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            // The above call will throw an exception
            // if the registry does not already exist

        } catch (RemoteException re) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            registry = LocateRegistry.createRegistry(RMIPortNum);
            //registry.rebind("reggy", registry);
            System.out.println("RMI registry created at port: " + RMIPortNum);
        }
    }

    private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (int i=0; i<names.length; i++)
            System.out.println(names[i]);
    }

    public void addCar(String c) throws Exception {
        carQ.add(c);
		Car temporary = (Car)Naming.lookup(c);
        temporary.setArrivalTime(System.currentTimeMillis());
    }
    public static void receiveCar(Car s) {
        try {
            registry.rebind(carQ.remove(0), s);
        } catch (RemoteException re) {
            System.out.println("Remote Exception");
        }
    }
}
