import java.io.*;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class CarClient {


    public static void main(String[] args) {
        try {
            int RMIPort;
            String hostName, portNum = "1901";

            /**
             * Code for obtaining hostName and RMI Registry port to be supplied.
             *
             * Look up the remote object and cast its reference to the remote
             * interface class--replace "localHost" with the appropriate host
             * name of the remote object.
             *
             */
            String registryURL = "rmi://localhost:" + portNum + "/car";

            CarInterface c = (CarInterface)Naming.lookup(registryURL);

            // invoke the remote methods
            c.setServices(5);
            System.out.println(c.getServices());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
