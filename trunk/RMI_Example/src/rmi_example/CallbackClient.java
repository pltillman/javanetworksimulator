import java.io.*;
import java.rmi.*;


public class CallbackClient {

    public static void main(String args[]) {
        try {
            int RMIPort;
            String hostName = "localhost";
            String portNum = "1099";
            int time = 4;
            String registryURL = "rmi://localhost:" + portNum + "/callback";
            // find the remote object and cast it to an interface object
            CallbackServerInterface h = (CallbackServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed \nServer said " + h.sayHello());
            CallbackClientInterface callbackObj = new CallbackClientImpl();
            // register for callback
            h.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException ex) {
                h.unregisterForCallback(callbackObj);
                System.out.println("Unregistered for callback.");
            }

        } catch (Exception e) {
            System.out.println("Exception in CallbackClient: " + e);
        }
    }
}
