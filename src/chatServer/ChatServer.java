package chatServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.ClientHandler;

public class ChatServer {
	ServerSocket serverSocket;
//	Socket s;
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	public static int counter = 0;
	
	
	public ChatServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		listenForClients();
	}
	
	public void listenForClients() throws IOException {
		
		while(true) {
			Socket s = serverSocket.accept();
			System.out.println("Client request recieved");
			
			DataInputStream in = new DataInputStream(s.getInputStream());
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			ClientHandler client = new ClientHandler(s, counter, in , out);
			
			Thread t = new Thread(client);
			
			
			clientList.add(client);
			
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
	
	
	public void close() throws IOException {
		System.out.println("closing server");
		clientList.clear();
		serverSocket.close();
	}
	

}
