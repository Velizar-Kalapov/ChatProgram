package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import chatServer.ChatServer;



public class ClientHandler implements Runnable {
	private int id;
	private DataInputStream in;
	private DataOutputStream out;
	public boolean isLoggedIn;
	Socket socket;
	private String username;
	private String receivedName;
	
	

	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		
		receivedName = in.readUTF();
	
		this.username = receivedName;
		
		isLoggedIn = true;
		
		sendMessageToUsers(" " + this.username + " connected");
		
	}
	
	
	

	@Override
	public void run() {
		String received;
		System.out.println("Client " + this.username + " connected" );
		try {
			while(true) {
				try{
					
					received = in.readUTF();

					if(received.equals("/logout")) {
						System.out.println("Client " + this.username + " Exiting");
						break;
					}
				
					
					for (ClientHandler c : ChatServer.clientList) {
						if(c.isLoggedIn) {
							c.getOut().writeUTF(received);
						}
					}
				
				}catch (IOException e) {
					System.err.println("Caught IOException");
					e.printStackTrace();
					break;
				}
			
			}
		
		} finally {
			this.isLoggedIn = false;
			try {
				sendMessageToUsers(" " + this.username + " left the chat");
				System.out.println("Closing connection for client: " + this.username);
				this.socket.close();
				in.close();
				out.close();
			} catch (IOException e) {
			System.err.println("Caught IOException while closing");
			}
		}
		
	}
	
	
	private void sendMessageToUsers(String message) throws IOException {
		for (ClientHandler c : ChatServer.clientList) {
			if(c.isLoggedIn) {
				c.getOut().writeUTF(message);
			}
		}
	}
	
	public static boolean duplicateUsername(String username) throws IOException { 
		for (ClientHandler c : ChatServer.clientList) {
			if(c.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
		
	
	public static void showUsers() { 
		for (ClientHandler c : ChatServer.clientList) {
			System.out.println(c.username);
		}
	}
	
	public int getId() {
		return this.id;
	}

	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	
	public String getReceivedName() {
		return receivedName;
	}
	
	public String getUsername() {
		return username;
	}
	


}