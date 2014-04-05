package srizzling.nwen401;

import java.net.*;   
import java.io.*;   

/**  
 * A TCP client used to send 1000 packets with a byte per each,  
 * and compute duplicated packets, lost packets, average round trip time  
 *   
 * 
 */   
public class TCPClient {   
   
    // the server's IP address and port number   
    private String address;   
    private int port;   
       
    // socket used to connect to server   
    private Socket socket;   
       
    // read and write stream class   
    private BufferedInputStream istream;   
    private BufferedOutputStream ostream;   
       
    // byte array with 1 length contains send and receive data   
    private byte[] sendData = new byte[1];   
    private byte[] receiveData = new byte[1];   
       
    // global output variable   
    private static PrintStream err = System.out;   
       
    /**  
     * initialize the ip address and port number   
     * need to connect to server  
     *   
     * @param add server's address   
     * @param p server's port number  
     */   
    public TCPClient(String add, int p)   
    {   
        try   
        {   
            address =  add;   
            port = p;   
        }   
        catch(Exception e)   
        {   
            err.println(e.getMessage());   
            System.exit(1);   
        }   
    }   
       
    /**  
     * send 1000 packets with a byte per each  
     * and get statistics data such as duplicated packets,   
     * lost packets, average round trip time  
     */   
    public void run()   
    {   
        try   
        {      
            // the number of duplicated and lost packets   
            int duplicated = 0, lost = 0;   
               
            // compute the average round trip time   
            long start = 0,time = 0;   
               
            // is time-out or no   
            boolean timeout = false;   
               
            // loop 1000 times   
            for(int i = 0; i < 1000; i++)   
            {   
                // each byte contains ASCII data from 0 to 127   
                if (i >= 128)    
                    sendData[0] = (byte)(i % 127);   
                else   
                    sendData[0] = (byte)i;   
                   
                // begin to timer   
                start = System.currentTimeMillis();   
                // initialize the socket connect to server   
                socket = new Socket(address, port);            
                // set the time-out for receiving   
                socket.setSoTimeout(2000);   
                // initialize iostream   
                istream = new BufferedInputStream(socket.getInputStream());   
                ostream = new BufferedOutputStream(socket.getOutputStream());   
                   
                // send one byte data to server   
                ostream.write(sendData, 0, 1);   
                ostream.flush();   
                   
                try   
                {   
                    // receive packet from server and add up running time   
                    istream.read(receiveData, 0, 1);   
                }   
                // catch InterruptedIOException   
                catch(InterruptedIOException iioe)   
                {   
                    // overtime occurs   
                    timeout = true;   
                }   
                   
                if (!timeout)   
                {   
                    err.println("send byte: " + receiveData[0] +    
                            "\treceived byte: " + sendData[0]);   
                       
                    // receive byte doesn't match send byte, duplicated packet   
                    if (receiveData[0] != sendData[0])   
                        duplicated++;   
                }   
                else   
                {   
                    // overtime occurs, get a lost packet   
                    lost++;   
                }   
                   
                // close socket once send and receive the byte   
                socket.close();   
                // stop timer   
                time += System.currentTimeMillis() - start;   
            }   
               
            // print statistics result   
            System.out.println("average round trip time: " + (double)(time/1000.0));   
            System.out.println("duplicated packets: " + duplicated);   
            System.out.println("lost packets: " + lost);   
        }   
        catch(Exception e)   
        {   
            err.println(e.getMessage());   
            System.exit(1);   
        }   
           
    }   
       
    /**  
     * control the running of program  
     *   
     * @param args a <code>String</code> array,  
     * contains the server's ip address an port number   
     */   
    public static void main(String[] args)   
    {   
        // no two arguments, give right usage to user   
        if (args.length != 2)   
        {   
            err.println("Usage: TCPClient <server> <portnumber>");   
            System.exit(1);   
        }   
           
        try   
        {   
            err.println("TCPClient is running...");   
            TCPClient client =  new TCPClient(args[0], Integer.parseInt(args[1]));   
               
            // begin to run   
            client.run();   
        }   
        catch(Exception e)   
        {   
            // exception occurs, print message and exit program   
            e.printStackTrace();   
            System.exit(1);   
        }   
    }   
}