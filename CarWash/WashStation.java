import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;


public class WashStation {

    private int type;
    private Boolean status;
    private int ID;

    public WashStation(int id, int t) {
        this.ID = id;
        this.type = t;
    }

    public static void main(String[] args) throws RemoteException {
        try {

            String portNum = "1099";
            String registryURL;
            //String serverAddress = JOptionPane.showInputDialog(null, "Please Enter the server's ip address");
            String serverAddress = "192.168.0.196";

            // get the “registry”
            Registry registry = LocateRegistry.getRegistry(serverAddress,1099);

            WashCallbackServerInterface h = (WashCallbackServerInterface)Naming.lookup("callback");



        } catch (Exception e) {
            System.out.println("Exception in CarSource: " + e);

        }
        WashCallbackClientImpl callback = new WashCallbackClientImpl();

        try {
            while (!callback.carAvailable()) {
                Thread.sleep(2000);
            }
        } catch (InterruptedException ie) {
            System.out.println("Interrupted Exception");
        }

    }

    public void receiveCar(Car c) throws RemoteException {
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
					 System.out.println("Services Rendered: wash");
                break;

            case 1:
					 totalServTime = wash + rinse;
					 System.out.println("Services Rendered: wash, rinse");
                break;
            case 2:
					 totalServTime = wash + rinse + wax;
					 System.out.println("Services Rendered: wash, rinse, wax");
                break;
            case 3:
					 totalServTime = wash + rinse + wax + tire_shine;
					 System.out.println("Services Rendered: wash, rinse, wax, tire shine");
                break;
            case 4:
					 totalServTime = vacuum + ArmorAll;
					 System.out.println("Services Rendered: vacuum, ArmorAll");
                break;
            case 5:
					 totalServTime = wash + rinse + tire_shine;
					 System.out.println("Services Rendered: wash, rinse, tire shine");
                break;
            case 6:
					 totalServTime = vacuum;
					 System.out.println("Services Rendered: vacuum");
                break;
            case 7:
					 totalServTime = carpet_shampoo + vacuum + ArmorAll;
					 System.out.println("Services Rendered: carpet shampoo, vacuum, ArmorAll");
                break;
            case 8:
					 totalServTime = rain_x_treatment + wash + rinse;
					 System.out.println("Services Rendered: Rain-x treatment, wash, rinse");
                break;
            case 9:
					 totalServTime = wash + rinse + wax + tire_shine + carpet_shampoo + vacuum + ArmorAll + rain_x_treatment;
					 System.out.println("Services Rendered: wash, rinse, wax, tire shine, carpet shampoo, vacuum, ArmorAll, Rain-x treatment");
                break;
            default:
                System.out.println("No service requests were received");
                break;
        }
		  c.setWashTime(totalServTime);
		  try{
		  		Thread.sleep(totalServTime * 1000);
		  }catch(InterruptedException ex){
		  		System.out.println("An Interrupted Exception occurred in WashStation.java");
		  }
    }
}

