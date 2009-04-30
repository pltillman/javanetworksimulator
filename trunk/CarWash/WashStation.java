import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;


public class WashStation {

    private int type;
    private static int bayID = 1;
    private static Registry registry;
    public static String car, bayName;
    public static Boolean carReady;
    private WashBayInterface washer;
    private GUIInterface gui;

    /**
     *
     * @param args
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        //String bayID = JOptionPane.showInputDialog(null, "Please enter 1-4");
        WashStation wash = new WashStation(bayID);
        wash.run();
    }

    /**
     *
     * @param id
     * @param t
     */
    public WashStation(int i) {
        this.type = i;

//        System.setProperty("java.rmi.server.hostname", "10.220.1.204");
//        System.setProperty("java.security.policy", "grant.policy");

    }

    /**
     * 
     */
    public void run() {
        try {
            //String serverAddress = JOptionPane.showInputDialog(null, "Please Enter the server's ip address");
            String serverAddress = "192.168.0.104";

            // get the registry
            registry = LocateRegistry.getRegistry(serverAddress,1099);
            System.out.println("Found registry");
            bayName = "bay" + bayID;
            washer = (WashBayInterface)registry.lookup(bayName);
            gui = (GUIInterface)registry.lookup("GUI");
            System.out.println("Located Wash Bay");
            System.out.println("Located GUI");

        } catch (Exception e) {
            System.out.println("Exception in Wash Station: " + e);
        }
        

        while (true) {
            try {
                System.out.println("Setting wash station busy status to true");
                washer.setisReady(true);
                gui.generateWashOutput("Wash" + bayID + ": Setting wash station busy status to true");
                if (gui.getReady()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread exception " + e);
                    }
                }

                while (!washer.carReady()) {
                    try {
                        System.out.println("No car in bay... sleeping...");
                        gui.generateWashOutput("Wash" + bayID + ": No car in bay... sleeping...");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread exception " + e);
                    }
                }
                gui.setBay1Status(1);
                System.out.println("Detected car");
                gui.generateWashOutput("Wash" + bayID + ": Detected car");
                System.out.println("Setting wash station busy status to false");
                gui.generateWashOutput("Wash" + bayID + ": Setting wash station busy status to false");
                washer.setisReady(false);
                washer.setCarReady(false);
                System.out.println("\n******************************************");
                System.out.println("Washing car...");
                gui.generateWashOutput("Wash" + bayID + ": Washing car...");
                receiveCar(washer.getCar());
                gui.setBay1Status(0);
                washer.setisReady(true);
                

            } catch (Exception e) {
                System.out.println("Exception in washstation " + e);
            }
        }
    }


    /**
     * 
     * @param s
     * @throws java.rmi.RemoteException
     */
    public void receiveCar(String s) throws RemoteException {
        CarInterface c = null;
        try {
            
            c = (CarInterface)registry.lookup(s);
            System.out.println("\tCar Name " + s + "\tType: " + c.getType() + "\tServices: " + c.getServices());

        } catch (NotBoundException nbe) {
            System.out.println("Not bound exception" + nbe);
        }
        if (c != null) {
            int services = c.getServices();
            int carType = c.getType();
            int wash = 4;
            int rinse = 2;
            int wax = 5;
            int carpet_shampoo = 6;
            int tire_shine = 2;
            int ArmorAll = 3;
            int rain_x_treatment = 2;
            int vacuum = 4;
            int totalServTime = 0;

            switch (services) {

                /**
                 * Wash = 5
                 * Rinse = 2
                 * Wax = 7
                 * ....
                 * ....
                 */
                case 0:
                    //set time for service
                    // was
                         totalServTime = wash;
                         System.out.println("\tServices Rendered: wash");
                    break;

                case 1:
                         totalServTime = wash + rinse;
                         System.out.println("\tServices Rendered: wash, rinse");
                    break;
                case 2:
                         totalServTime = wash + rinse + wax;
                         System.out.println("\tServices Rendered: wash, rinse, wax");
                    break;
                case 3:
                         totalServTime = wash + rinse + wax + tire_shine;
                         System.out.println("\tServices Rendered: wash, rinse, wax, tire shine");
                    break;
                case 4:
                         totalServTime = vacuum + ArmorAll;
                         System.out.println("\tServices Rendered: vacuum, ArmorAll");
                    break;
                case 5:
                         totalServTime = wash + rinse + tire_shine;
                         System.out.println("\tServices Rendered: wash, rinse, tire shine");
                    break;
                case 6:
                         totalServTime = vacuum;
                         System.out.println("\tServices Rendered: vacuum");
                    break;
                case 7:
                         totalServTime = carpet_shampoo + vacuum + ArmorAll;
                         System.out.println("\tServices Rendered: carpet shampoo, vacuum, ArmorAll");
                    break;
                case 8:
                         totalServTime = rain_x_treatment + wash + rinse;
                         System.out.println("\tServices Rendered: Rain-x treatment, wash, rinse");
                    break;
                case 9:
                         totalServTime = wash + rinse + wax + tire_shine + carpet_shampoo + vacuum + ArmorAll + rain_x_treatment;
                         System.out.println("\tServices Rendered: wash, rinse, wax, tire shine, carpet shampoo, vacuum, ArmorAll, Rain-x treatment");
                    break;
                default:
                    System.out.println("\tNo service requests were received");
                    break;
            }

            c.setWashTime(totalServTime);
            System.out.println("\tSaved service time on car receipt");
            System.out.println("\tFinishing up...........");
            try{
                Thread.sleep(totalServTime * 1000);
            }catch(InterruptedException ex){
                System.out.println("An Interrupted Exception occurred in WashStation.java");
            }
            System.out.println("******************************************\n");
        }
    }
}

