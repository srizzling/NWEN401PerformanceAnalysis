import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Sriram Venkatesh
 */
public class MessageClient {
  public static void main(String[] args){

    try{
      HashMap<String, Long> stats = new HashMap<String, Long>();
      int n = 15;
      int transmittedTime = 1000;

      

      for(int loop=0;loop<5;loop++){
          //Get reference from registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
          //Lookup message object from server
        IMessage msgObject = (IMessage)registry.lookup("Message");

        System.out.println("n=" + n + " loop=" + loop);
        byte[] message = new byte[1024*n];
        System.out.println("Start sending....");
        long startTime = System.nanoTime();
        for(int i=0;i<transmittedTime;i++){
          System.out.println(i);
          msgObject.captureMessage(message);
        }
        msgObject.captureMessage("quit".getBytes());
        long stopTime = System.nanoTime();
        String descriptor = String.valueOf(n) + " " + loop;
        stats.put(descriptor, (stopTime-startTime));
          //System.out.println("Duration = " + (stopTime - startTime));
      }
      for (Entry<String, Long> entry : stats.entrySet()) {
        String sizeOfFile = entry.getKey();
        Long duration = entry.getValue();
        System.out.println("Test:" +sizeOfFile + " || " + "Duration " + duration);
      }

    }catch(NotBoundException nbe){
      nbe.printStackTrace();
    }catch(RemoteException re){
      re.printStackTrace();
    }
  }
}
