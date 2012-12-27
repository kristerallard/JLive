package jlive.client;

import java.io.*;
import java.net.*;

public class Client {
	
	// JAVA.net //
	private Socket connectToServer;
	
	// JAVA.io //
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	
// CONSTRUCTOR ----------------------------------------------- //
	public Client() {
		// Start as NULL //
		connectToServer = null;
		outStream = null;
		inStream = null;
	}

	
// LOG:ERS ----------------------------------------------- //
	private void log(String message){
		System.out.println("Client: " + message);
	}

	private void logError(String message){
		System.err.println("Client: " + message);
	}

	
// CONNECT ---------------------------------------------- //
/* Client connecting to a server . 
 * Establising the connection. */
	public void connect(String server, int port) throws IOException{
		log("Connecting to server...");
		connectToServer = new Socket(server, port);
		outStream = new PrintWriter(
				new OutputStreamWriter(connectToServer.getOutputStream()));
		inStream  = new BufferedReader(
				new InputStreamReader(connectToServer.getInputStream()));
		log("Connected.");
	}
	
	
// SEND ---------------------------------------------- //
/* Client sends message package to server. */	
	public void send(String message){
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}
	
	
// RECEIVE ---------------------------------------------- //
/* Client receives message package from server. */	
	public String receive(){
		log("Receiving...");

		String receivedMessage = "";
		try {
			receivedMessage = inStream.readLine();
			log("Message received.");
		} catch (IOException e) {
			logError("Receive failed. " + e.getMessage());
		}
		
		return receivedMessage;
	}
	
	
// CLOSE ---------------------------------------------- //
/* Close the connection. */	
	public void close(){
		log("Closing connection.");
		try{
			inStream.close();
			outStream.close();
			connectToServer.close();
		}catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}
		
		log("Connection closed.");
	}
}
