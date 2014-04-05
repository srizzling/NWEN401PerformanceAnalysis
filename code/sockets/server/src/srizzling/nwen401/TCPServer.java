package srizzling.nwen401;
import java.net.*;   
import java.io.*;   
   
/**  
 * A simple TCP server, just used to response the packet  
 * user send to it again  
 *   
 *  
 */   
public class TCPServer {   
   
    // the port number socket binds   
    private int port;   
       
    // listen socket and connect socket from client   
    private ServerSocket listenSocket;   
    private Socket socket;   
       
    // iostream used to send and receive data   
    private BufferedInputStream istream;   
    private BufferedOutputStream ostream;   
       
    // buffer with one length   
    private byte[] receiveData = new byte[1];   
       
    // global output variable   
    private static PrintStream err = System.out;   
       
    /**  
     * initialize the port number socket binds  
     *   
     * @param p the port number  
     */   
    public TCPServer(int p)   
    {   
        port = p;   
    }   
       
    /**  
     * receive packet from user and reponse that to user again  
     */   
    public void run()   
    {   
        try   
        {   
            // begin to listen the port   
            listenSocket = new ServerSocket(port);   
               
            while(true)   
            {   
                // accept client's connection   
                socket = listenSocket.accept();   
                   
                // initialize iostream   
                istream = new BufferedInputStream(socket.getInputStream());   
                ostream = new BufferedOutputStream(socket.getOutputStream());   
                   
                // get the packet user sends   
                istream.read(receiveData, 0, 1);   
                err.println("received packet: " + receiveData[0]);   
                   
                //send the packet user sends to server to user again   
                ostream.write(receiveData, 0, 1);   
                ostream.flush();   
            }   
        }   
        catch(Exception e)   
        {   
            // exception occurs, print error message and exit program   
            err.println(e.getMessage());   
            System.exit(1);   
        }   
    }   
       
    /**  
     * the control the running of program   
     *   
     * @param args a <code>String</code> array  
     * contain a port number socket binds  
     */   
    public static void main(String[] args)   
    {   
        // no one arguments, give right usage to user   
        if (args.length != 1)   
        {   
            err.println("Usage: TCPServer <portnumber>");   
            System.exit(1);   
        }   
           
        try   
        {   
            err.println("TCPServer is running...");   
            TCPServer server = new TCPServer(Integer.parseInt(args[0]));   
               
            // begin to run   
            server.run();   
        }   
        catch(Exception e)   
        {   
            // exception occurs, print error message and exit the running of program   
            e.printStackTrace();   
            System.exit(1);   
        }   
    }   
}   