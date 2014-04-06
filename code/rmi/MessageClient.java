import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
 
/**
 *
 * @author http://lycog.com
 */
public class MessageClient {
  public static void main(String[] args){
    try{
      int n = 0;
      int transmittedTime = 10000;
      for(int num=0;num<5;num++){
        n = n + 2;
        for(int loop=0;loop<5;loop++){
          //Get reference from registry
          Registry registry = LocateRegistry.getRegistry("192.168.1.65", 1099);
          //Lookup message object from server
          IMessage msgObject = (IMessage)registry.lookup("Message");
 
          System.out.println("n=" + n + " loop=" + loop);
          byte[] message = new byte[1024*n];
          System.out.println("Start sending....");
          long startTime = System.nanoTime();
          for(int i=0;i<transmittedTime;i++){
            msgObject.captureMessage(message);
          }
          msgObject.captureMessage("quit".getBytes());
          long stopTime = System.nanoTime();
 
          System.out.println("Duration = " + (stopTime - startTime));
        }
      }
    }catch(NotBoundException nbe){
      nbe.printStackTrace();
    }catch(RemoteException re){
      re.printStackTrace();
    }
  }
}
