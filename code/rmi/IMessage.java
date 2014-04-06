import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author http://lycog.com
 */
public interface IMessage extends Remote{
  void captureMessage(byte[] message) throws RemoteException;
}
