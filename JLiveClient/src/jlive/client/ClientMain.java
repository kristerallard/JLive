package jlive.client;

import java.io.IOException;


public class ClientMain {
	public static void main(String[] args) {
		// Create new Client. //
		
		try {
			Client client = new Client();
			client.connect("127.0.0.1", 444);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
