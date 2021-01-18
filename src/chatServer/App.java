package chatServer;

import java.io.IOException;

import client.Client;

public class App {

	public static void main(String[] args) {
		try {
			ChatServer chatServer = new ChatServer(9090);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
