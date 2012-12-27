package jlive.server;

import java.io.*;
import java.net.*;

public class User implements Runnable {
	// JAVA.net //
	private Socket socket;
	
	// JAVA.io //
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	// User //
	private String name;
	private InetAddress IP;
		
	public User(Socket s, String n) {
		// Start as NULL //
		socket = s;
		outStream = null;
		inStream = null;
		
		// Set User //
		setName(n);
		IP = null;
	}

	
// LOG:ERS ----------------------------------------------- //
	private void log(String message){
		System.out.println(name + ": " + message);
	}

	private void logError(String message){
		System.err.println(name + ": " + message);
	}

	
// RUN THREAD ----------------------------------------------- //
	@Override
	public void run() {
		
		try {
			outStream = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			inStream  = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			// Set IP Address //
			IP = socket.getLocalAddress();
			
			log("joined the server.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
// SEND ---------------------------------------------- //
/* Server sends message package to client. */
	public synchronized void send(String message){
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}

	
// RECEIVE ---------------------------------------------- //
/* Server receives message package from client. */	
	public synchronized String receive(){
		log("Receiving...");

		String receivedMessage = null;
		try {
			receivedMessage = inStream.readLine();
			log("Message received!");
		} catch (IOException e) {
			logError("Receive failed! " + e.getMessage());
		}
		
		return receivedMessage;
	}
	
// CLOSE ---------------------------------------------- //	
	public synchronized void close(){
		log(name + " logging out");
		try{
			inStream.close();
			outStream.close();
			socket.close();
			log(name + " logged out.");
		} catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}
	}

	
// GET:ERS ----------------------------------------------- //
	public String getName() {
		return name;
	}
	
	public InetAddress getIP() {
		return IP;
	}
	

// SET:ERS ----------------------------------------------- //
	public void setName(String name) {
		this.name = name;
	}
}
