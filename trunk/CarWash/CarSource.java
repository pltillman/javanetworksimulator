import java.net.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import javax.swing.*;

public class CarSource {


    public static void main(String[] args) {
        try {
            
            String portNum = "1099";
            String registryURL;
            //String serverAddress = JOptionPane.showInputDialog(null, "Please Enter the server's ip address");
            String serverAddress = "192.168.0.196";
            
            // get the “registry”
            Registry registry = LocateRegistry.getRegistry(serverAddress,1099);
            System.out.println(registry.list());

            Car expObj1, expObj2, expObj3, expObj4, expObj5;

            expObj1 = new Car();
            expObj1.setType(1);
            expObj2 = new Car();
            expObj2.setType(2);
            expObj2.setServices(4);
            expObj3 = new Car();
            expObj3.setType(2);
            expObj3.setServices(2);
            expObj4 = new Car();
            expObj4.setType(3);
            expObj5 = new Car();
            expObj5.setType(4);


            registry.bind("car1", expObj1);
            registry.bind("car2", expObj2);
            registry.bind("car3", expObj3);
            registry.bind("car4", expObj4);
            registry.bind("car5", expObj5);

            CallbackServerInterface h = (CallbackServerInterface)registry.lookup("callback");
            h.tellServerAboutCar("car1");
            Thread.sleep(10000);
            h.tellServerAboutCar("car2");
            Thread.sleep(10000);
            h.tellServerAboutCar("car3");
            Thread.sleep(10000);
            h.tellServerAboutCar("car4");
            Thread.sleep(10000);
            h.tellServerAboutCar("car5");
            //String registryURL = "24.99.194.128:" + serverPort + "/car";

//            CarInterface c = (CarInterface)registry.lookup("car");
//
//            Car g = c.getCar();
//            g.setServices(15);
//
//            registry.rebind(serverAddress, g);

            // invoke the remote methods
//            c.setServices(5);
//            c.setType(24);
            //Thread.sleep(10000);
            //System.out.println("Type: " + c.getType() + "\tServices: " + c.getServices());

        } catch (Exception e) {
            System.out.println("Exception in CarSource: " + e);

        }
//        catch (RemoteException e) {
//
//            System.out.println("Remote Exception: " + e.getMessage());
//            //e.printStackTrace();
//        } catch (NotBoundException nbe) {
//            System.out.println("NotBound Exception");
//            //nbe.printStackTrace();
//        } catch (InterruptedException ie) {
//            System.out.println("Interrupted Exception");
//            //ie.printStackTrace();
//        }
//        catch (MalformedURLException mue) {
//            System.out.println("Malformed URL Exception");
//        }
    }


}