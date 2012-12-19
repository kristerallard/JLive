package jlive.server;

import java.io.IOException;

public class ServerMain {
	public static void main(String[] args) {
		// Create new Server. //
		Server server = new Server();
		
		// Start the Server //
		try {
			server.init(444);
		} catch (IOException e) {
			System.err.println("Failed to setup a server socket. " + e.getMessage());
			System.exit(1);
		}
		
		// Server online. Wait for clients //
		try {
			server.waitForConnection();
			
		} catch (IOException e) {
			System.err.println("Failed to setup connection. " + e.getMessage());
			System.exit(1);
		}
		
		server.send("Server ready.");
		server.receive();
		
		server.close();
	}
}
