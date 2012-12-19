package jlive.client;

import java.io.IOException;

public class ClientMain {
	public static void main(String[] args) {
		// Create new Client. //
		Client client = new Client();
		
		// Connect to Server //
		try {
			client.connect("127.0.0.1", 444);
			
		} catch (IOException e) {
			System.err.println("Failed to connect to the server. " + e.getMessage());
			System.exit(1);
		}

		client.close();
	}
}
