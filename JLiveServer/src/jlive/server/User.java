package jlive.server;

import java.io.*;
import java.net.*;

public class User implements Runnable {
	// JAVA.net //
	private Socket SOCK;
	
	// JAVA.io //
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	private String name;
		
	public User(Socket socket, String n) {
		// Start as NULL //
		SOCK = socket;
		outStream = null;
		inStream = null;
		
		setName(n);
	}

	
// LOG:ERS ----------------------------------------------- //
	private void log(String message){
		System.out.println("Server: " + message);
	}

	private void logError(String message){
		System.err.println("Server: " + message);
	}

	@Override
	public void run() {
		try {
			outStream = new PrintWriter(
					new OutputStreamWriter(SOCK.getOutputStream()));
			inStream  = new BufferedReader(
					new InputStreamReader(SOCK.getInputStream()));
			log("User connected from " + SOCK.getLocalAddress());
			send("test");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally{
			try {
				SOCK.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
// SEND ---------------------------------------------- //
/* Server sends message package to client. */
	public void send(String message){
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}

	
// RECEIVE ---------------------------------------------- //
/* Server receives message package from client. */	
	public String receive(){
		log("Receiving...");

		String receivedMessage = "test";
		try {
			receivedMessage = inStream.readLine();
			log("Message received!");
		} catch (IOException e) {
			logError("Receive failed! " + e.getMessage());
		}
		
		return receivedMessage;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
