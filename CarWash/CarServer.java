import java.rmi.*;
//import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;


public class CarServer {

    public static void main(String args[]) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);

//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager   (new RMISecurityManager());
//        }

        String portNum, registryURL;
        int RMIPortNum;

        try {
            
            // code for port number value to be supplied
            RMIPortNum = 1099;
            portNum = "1099";
            Car exportedObj = new Car();
            startRegistry(RMIPortNum);
            // register the object under the name "car"
            registryURL = "rmi://localhost:" + portNum + "/car";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Car Server ready.");
            exportedObj.setServices(2);
            System.out.println(exportedObj.getServices());
        } catch (Exception e) {
            System.out.println("Exception in CarServer: " + e);
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
}
