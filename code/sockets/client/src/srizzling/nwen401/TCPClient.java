package srizzling.nwen401;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
 
/**
 *
 * @author Sriram Venkatesh (@srizzling)
 */
public class TCPClient {
  public static void main(String[] args){
    try{
      int transmittedTime = 1000;
      int n = 5;
      String hostname = args[0];
      HashMap<String, Long> stats = new HashMap<String, Long>();
      //Vary the message size
      for(int num=0;num<5;num++){
        n = n + 5;
 
        //Repeat the same size for 5 times
        for(int loop=0;loop<5;loop++){
          //Connect to server
          Socket socket = new Socket(hostname, 6000);
 
          PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
 
          //Prepare message size to be transmitted
          byte[] byteMsg = new byte[1024*n];
          String strMsg = new String(byteMsg);
 
          System.out.println("Start sending messages");
          System.out.println("n=" + n + " loop=" + loop);
          long startTime = System.nanoTime();
          for(int i=0; i<transmittedTime; i++){
        	System.out.println(strMsg + "==" + i);
            pw.println(strMsg);
          }
          pw.println("quit");
          long endTime = System.nanoTime();
          String descriptor = String.valueOf(n) + " " + loop;
          stats.put(descriptor, (endTime-startTime));
          //System.out.println("Duration = " + (endTime-startTime));
        }
      }
      //All Done Print out the stats here
      for (Entry<String, Long> entry : stats.entrySet()) {
    	    String sizeOfFile = entry.getKey();
    	    Long duration = entry.getValue();
    	    System.out.println("Test:" +sizeOfFile + " || " + "Duration" + duration);
    	}
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}