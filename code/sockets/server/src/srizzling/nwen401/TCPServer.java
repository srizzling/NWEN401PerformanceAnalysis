package srizzling.nwen401;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 *
 * @author Sriram Venkatesh (@srizzling)
 */
public class TCPServer {
  public static void main(String[] args){
    try{
      ServerSocket server = new ServerSocket(6000);
 
      while(true){
        System.out.println("Server starts listening...");
        Socket socket = server.accept();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
 
        String msg = "";
        while(true){
          msg = br.readLine();
          if(msg.equals("quit")) break;
        }
      }
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}