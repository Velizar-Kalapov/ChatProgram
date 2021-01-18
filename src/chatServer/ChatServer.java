package chatServer;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.ClientHandler;

public class ChatServer {
	ServerSocket serverSocket;
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	public static int counter = 0;


	public ChatServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		listenForClients();
	}

	public void listenForClients() throws IOException {

		while(true) {
			Socket s = serverSocket.accept();

			ClientHandler clientHandler = new ClientHandler(s, counter);
			
			Thread  t = new Thread(clientHandler);

			clientList.add(clientHandler);

			t.start();
			counter ++;
			printClients();
		}


	}


	private void printClients() {
		for (ClientHandler c : clientList) {
			System.out.println(c.getId());
		}
	}



}