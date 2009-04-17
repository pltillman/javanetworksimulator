import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class CallbackServer {

    public static void main (String args[]) {
        String portNum, registryURL;
        try {
            portNum = "1099";
            int RMIPortNum = 1099;
            startRegistry(RMIPortNum);
            CallbackServerImpl exportedObj = new CallbackServerImpl();
            registryURL = "rmi://localhost:" + portNum + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Callback Server ready.");
        } catch (Exception re) {
            re.printStackTrace();
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
