package srizzling.nwen401;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple TCP/IP socket implementation using threads to send messages 
 * to client. Used to measure the performance between different IPC paradigms * 
 * 
 * @author Sriram Venkatesh (venksriram@gmail.com)
 *
 */
public class PingServer extends Thread {

	protected Socket clientSocket;

	public static void main(String[] args) throws IOException 
	{ 
		
		int port = 0;
		if (args.length > 0){
			port = Integer.parseInt(args[0]);
		}
		
		ServerSocket serverSocket = null; 

		try { 
			serverSocket = new ServerSocket(port); 
			System.out.println ("Connection Socket Created");
			try { 
				while (true)
				{
					System.out.println ("Waiting for Connection");
					new PingServer (serverSocket.accept()); 
				}
			} 
			catch (IOException e) 
			{ 
				System.err.println("Accept failed."); 
				System.exit(1); 
			} 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Could not listen on port:" + port); 
			System.exit(1); 
		} 
		finally
		{
			try {
				serverSocket.close(); 
			}
			catch (IOException e)
			{ 
				System.err.println("Could not close port: "+ port); 
				System.exit(1); 
			} 
		}
	}

	private PingServer (Socket clientSoc)
	{
		clientSocket = clientSoc;
		start();
	}

	public void run()
	{
		System.out.println ("New Communication Thread Started");

		try { 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
					true); 
			BufferedReader in = new BufferedReader( 
					new InputStreamReader( clientSocket.getInputStream())); 

			String inputLine; 

			while ((inputLine = in.readLine()) != null) 
			{ 
				int input = Integer.parseInt(inputLine);
				System.out.println ("Server: " + input*input); 
				out.println(input*input); 

				if (input ==-1) break; 
			} 

			out.close(); 
			in.close(); 
			clientSocket.close(); 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Problem with Communication Server");
			System.exit(1); 
		} 

	}
}
