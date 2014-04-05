package srizzling.nwen401;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PingClient {

	public static void main(String[] args) throws IOException {
		String serverHostname = "";
		int port = 0;
		if (args.length > 0){
			serverHostname = args[0];
			port = Integer.parseInt(args[1]);
		}
		else{
			System.out.println ("No hostname or port specifed...exiting");
			System.exit(0);
		}
		
		System.out.println ("Attemping to connect to host " +
				serverHostname + " on port " + port);

		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(serverHostname, port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + serverHostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: " + serverHostname);
			System.exit(1);
		}

		int i = 0;
		while (i < 50) {
			System.out.println("You are sending: " + i);
			out.println(i);
			System.out.println("Server Response: " + in.readLine());
			System.out.println("=========");
			
			i++;
		}

		out.close();
		in.close();
		echoSocket.close();
	}

}


