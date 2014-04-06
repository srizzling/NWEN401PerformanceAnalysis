package srizzling.nwen401;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
 
/**
 *
 * @author Sriram Venkatesh (@srizzling)
 */
public class TCPClient {
  public static void main(String[] args){
    try{
      int transmittedTime = 100000;
      int n = 5;
      String hostname = args[0];
      //Vary the message size
      for(int num=0;num<5;num++){
        n = n + 5;
 
        //Repeat the same size for 5 times
        for(int loop=0;loop<5;loop++){
          //Connect to server
          Socket socket = new Socket(hostname, 8888);
 
          PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
 
          //Prepare message size to be transmitted
          byte[] byteMsg = new byte[1024*n];
          String strMsg = new String(byteMsg);
 
          System.out.println("Start sending messages");
          System.out.println("n=" + n + " loop=" + loop);
          long startTime = System.nanoTime();
          for(int i=0; i<transmittedTime; i++){
            pw.println(strMsg);
          }
          pw.println("quit");
          long endTime = System.nanoTime();
 
          System.out.println("Duration = " + (endTime-startTime));
        }
      }
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}