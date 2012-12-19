package jlive.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
//import java.util.Scanner;

public class Server {
	// JAVA.net //
	private ServerSocket serverSocket;
	
	// JAVA.io //
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	private User user;
	public static ArrayList<User> users = new ArrayList<User>();


// CONSTRUCTOR ----------------------------------------------- //
	public Server(){
		// Start as NULL //
		serverSocket = null;
		outStream = null;
		inStream = null;
	}
	

// LOG:ERS ----------------------------------------------- //
	private void log(String message){
		System.out.println("Server: " + message);
	}

	private void logError(String message){
		System.err.println("Server: " + message);
	}
	
	
// INIT ----------------------------------------------- //
/* Start a new ServerSocket or throws an IOException on fail. */
	public void init(int port) throws IOException{
		// New ServerSocket //
		log("Setting up server socket...");
		serverSocket = new ServerSocket(port);
		log("Server socket setup complete.");
	}
	

// WAIT FOR CONNECTION ---------------------------------------------- //
/* Server listning for clients who wants to connect. 
 * Establising the connection. */
	public void waitForConnection() throws IOException{
		log("Waiting for connections...");
		
		/* Add new <User> when a client connecting. */
		while(true){
			Socket SOCK = serverSocket.accept();
			
//			@SuppressWarnings("resource")
//			Scanner input = new Scanner(SOCK.getInputStream());
//			String userName = input.nextLine();
			user = new User(SOCK, "userName");
			Thread x = new Thread(user);
			x.start();
			users.add(user);
			
			log("Connection to client established.");
			System.out.println(SOCK.getLocalAddress());
		}
	}


// SEND ---------------------------------------------- //
/* Server sends message package to client. */
	public void send(String message){
		for(User user: users){
			user.send(message);
		}
	}

	
// RECEIVE ---------------------------------------------- //
/* Server receives message package from client. */	
	public void receive(){
		for(User user: users){
			System.out.println(user.receive());
		}
	}


// CLOSE ---------------------------------------------- //
/* Close the server. */	
	public void close(){
		log("Closing connection.");
		try{
			inStream.close();
			outStream.close();
			serverSocket.close();
		} catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}
		
		log("Connection closed.");
	}
}
