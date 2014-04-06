import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
 
/**
 *
 * @author http://lycog.com
 */
public class MessageImplementation extends UnicastRemoteObject
                implements IMessage
{
  public MessageImplementation() throws RemoteException{
 
  }
 
  public void captureMessage(byte[] message){
    //Hold receiving message
    byte[] msg = message;
  }
}
