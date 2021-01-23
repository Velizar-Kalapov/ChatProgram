package chatServer;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.ClientHandler;

public class ChatServer {
	ServerSocket serverSocket;
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	public static int counter = 0;
	private String host = "localhost";
	private static int port = 9090;


	
	public static void main(String[] args) {
		try {
			ChatServer chatServer = new ChatServer(9090);
			
		} catch (IOException e) {
			System.out.println("IOException when creating server");
		}	
	}
	

	public ChatServer(int port) throws IOException {
		serverSocket = new ServerSocket(port, 0, InetAddress.getByName(host));
		listenForClients();
	}
	
	
	public ChatServer() throws IOException {
		serverSocket = new ServerSocket(0, 0, InetAddress.getByName(host));
		port = serverSocket.getLocalPort();
		System.out.println(port);
		listenForClients();
	}
		

	private void listenForClients() throws IOException {
		while(true) {
			
			Socket s = serverSocket.accept();

			ClientHandler clientHandler = new ClientHandler(s);
			
			Thread  t = new Thread(clientHandler);

			clientList.add(clientHandler);

			t.start();
			//counter ++;
		}
	}
	
	public static int getPort() {
		return port;
	}
	
	public String getHost() {
		return host;
	}
	
	

}