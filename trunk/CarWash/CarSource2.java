import java.net.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import javax.swing.*;
import java.util.Random;

public class CarSource2 {


    public static void main(String[] args) {
        try {
        
		  		Random generator = new Random();    
            String portNum = "1099";
            String registryURL;
            //String serverAddress = JOptionPane.showInputDialog(null, "Please Enter the server's ip address");
            String serverAddress = "192.168.0.196";
            
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress,1099);
            System.out.println(registry.list());

        		
				// Given input of number_of_cars and timeInterval, how do I get these???
				
				// **Temporary test parameters**
				int timeInterval = 10000;
			   int number_of_cars = 100;
				
				for (int i=0; i <= number_of_cars; i++)
				{
					if (i == number_of_cars)
					{
						// SEND NULL CAR
						// how will this be set? Type 5 maybe?
					}
					else
					{
						// Generate random type (1-4)
						int type = generator.nextInt(4);
				
						// Generate random service (0-9)
						int service = generator.nextInt(9);
				
					   // Create Car object based on above parameters.
						Car temp;
						temp = new Car();
						temp.setType(type);
						temp.setServices(service);
						registry.bind(("car " + i),temp);
						CallbackServerInterface h = (CallbackServerInterface)registry.lookup("callback");
						h.tellServerAboutCar(("car " + i));
						Thread.sleep(timeInterval);
					}
				}
				
				
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