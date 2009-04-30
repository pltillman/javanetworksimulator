//import java.net.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.util.Random;

//import javax.swing.*;

/**
 *
 * @author Patrick Tillman, Brandon Parker, Ryan Spencer
 */
public class CarSource {
    
    private Registry registry;
    private Random generator;
    private GUIInterface gui;

    /**
     * Default constructor for CarSource
     */
    public CarSource() {

        generator = new Random();
    }

    /**
     * Contains all the logic for generating cars
     */
    public void start() {
        try {

//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new RMISecurityManager());
//            }
//
//            System.setProperty("java.rmi.server.hostname", "10.220.1.204");
//            System.setProperty("java.security.policy", "grant.policy");

//            registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
//            registry.rebind(getRegistryNameImpl(), this);


            //String portNum = "1099";
            //String registryURL;
            //String serverAddress = JOptionPane.showInputDialog(null, "Please Enter the server's ip address");
            
            String serverAddress = "192.168.0.104";

            // get the “registry”
            registry = LocateRegistry.getRegistry(serverAddress,1099);
            System.out.println("Registry located successfully");


            CallbackServerInterface h = (CallbackServerInterface)registry.lookup("sourceCallback");
            System.out.println("Callback located successfully");

            gui = new GUI();
            registry.rebind("GUI", gui);

            if (!gui.getReady()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {
                    System.out.println("InterruptedException in cs " + ie);
                }
            } 


            int timeInterval = gui.getCarInterval();
            int number_of_cars = gui.getCarMax();
            if (timeInterval < 500) {
                timeInterval = 1000;
            }
            if (number_of_cars < 10) {
                number_of_cars = 100;
            }

            for (int i=0; i < number_of_cars+1; i++) {
                if (i == number_of_cars) {
                // SEND NULL CAR
                // how will this be set? Type 5 maybe?
                } else {
                    // Generate random type (1-4)
                    int type = generator.nextInt(4);

                    // Generate random service (0-9)
                    int service = generator.nextInt(9);

                    // Create Car object based on above parameters.
                    Car temp;
                    temp = new Car();
                    temp.setType(type);
                    temp.setServices(service);
                    registry.rebind(("car" + i),temp);
                    System.out.println("\nCar type" + temp.getType() + " sent with services " + temp.getServices());
                    h.tellServerAboutCar(("car" + i));
                    Thread.sleep(timeInterval);
                }
            }
            
            System.out.println("Cars have been created, exported, and notified");


        } catch (Exception e) {
            System.out.println("Exception in CarSource: " + e);

        }
    }

    /**
     * Invokes the CarSource Car generator
     * @param args
     */
    public static void main(String[] args) {
        CarSource cs = new CarSource();
        cs.start();
    }


}