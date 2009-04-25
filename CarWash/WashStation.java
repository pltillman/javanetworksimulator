
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
                break;

            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            default:
                
                break;
        }
    }
}
