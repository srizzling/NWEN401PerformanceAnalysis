package srizzling.nwen401;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple TCP/IP socket implementation using threads to send messages 
 * to client. Used to measure the performance between different IPC paradigms * 
 * 
 * @author Sriram Venkatesh (venksriram@gmail.com)
 *
 */
public class PingServer {

	private ServerSocket echoServer;
	private Socket clientSocket;
	private int numConnections = 0;
	private int port;


	public PingServer(int port){
		this.port = port;
	}

	public void stopServer(){
		System.out.println("Closing Connections: Server is shutting down");
		System.exit(0);
	}

	public void startServer(){
		//Open a server socket on a given port

		try{
			echoServer = new ServerSocket(port);
		}
		catch (IOException e){
			System.out.println("Opps! An error occured trying to create a socket" + e);
		}

		System.out.println("The server has started successfully and is waiting for incoming connections");

		while(true){
			try{
				clientSocket = echoServer.accept();
				numConnections ++;
				Worker serverWorker = new Worker(clientSocket, numConnections, this);
			}
			catch (IOException e){
				System.out.println("Opps! An error occured during the processing of client requests" + e);
			}
		}
	}


	public static void main(String args[]){
		int port = 5679; // Port of Server need to open it for connections
		PingServer server = new PingServer(port);
		server.startServer();
	}


}

class Worker implements Runnable {

	private BufferedReader is;
	private PrintStream os;
	private Socket clientSocket;
	private int id;
	private PingServer server;

	public Worker(Socket clientSocket, int id, PingServer server){
		this.clientSocket = clientSocket;
		this.id = id;
		this.server = server;
		System.out.println( "Connection " + id + " established with: " + clientSocket );
		try {
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e);
		}
	}


	@Override
	public void run() {
		String line;
		try {
			boolean serverStop = false;

			while (true) {
				line = is.readLine();
				System.out.println( "Received " + line + " from Connection " + id + "." );
				int n = Integer.parseInt(line);
				if ( n == -1 ) {
					serverStop = true;
					break;
				}
				if ( n == 0 ) break;
				os.println("" + n*n ); 
			}

			System.out.println( "Connection " + id + " closed." );
			is.close();
			os.close();
			clientSocket.close();

			if ( serverStop ) server.stopServer();
		} catch (IOException e) {
			System.out.println(e);
		}

	}

}
