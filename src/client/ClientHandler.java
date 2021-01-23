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
	
	

	public ClientHandler(Socket socket) throws IOException {
		this.socket = socket;
		//this.id = id;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		
		String receivedName = in.readUTF();
		
		int duplicateCount = 0;
		
		for (ClientHandler c : ChatServer.clientList) {
			if(c.username.equals(receivedName)) {
				duplicateCount++;
			}
		}
		
		if(duplicateCount > 0) {
			receivedName = new StringBuilder(receivedName).append("(").append(duplicateCount).append(")").toString();
		}
		
		this.username = receivedName;
		
		isLoggedIn = true;
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
							c.getOut().writeUTF(this.username + " : "  + received);
						}
					}
				
				}catch (IOException e) {
					System.err.println("Caught IOException");
					break;
				}
			
			}
		
		} finally {
			this.isLoggedIn = false;
			try {
				System.out.println("Closing connection for client: " + this.username);
				this.socket.close();
				in.close();
				out.close();
			} catch (IOException e) {
			System.err.println("Caught IOException while closing");
			}
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


}