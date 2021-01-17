package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import chatServer.ChatServer;



public class ClientHandler implements Runnable {
	private int id;
	private DataInputStream in;
	private DataOutputStream out;
	public boolean isLoggedIn;
	Socket socket;
	
	public ClientHandler(Socket socket, int id, DataInputStream in, DataOutputStream out) {
		this.socket = socket;
		this.id = id;
		this.in = in;
		this.out = out;
		isLoggedIn = true;
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

	@Override
	public void run() {
		String received;
		while(true ) {
			
			try{
				received = in.readUTF();
				System.out.println(received);
				
				if(received.equals("/logout")) {
					this.isLoggedIn = false;
					System.out.println("Exiting");
					this.socket.close();
					break;
				}
			
				for (ClientHandler c : ChatServer.clientList) {
					System.out.println("Sent to: " + c.getId());
					if(c.isLoggedIn) {
						c.getOut().writeUTF(this.id + " : "  + received);
					}
				}
			
			
			}catch (EOFException e) {
				System.out.println(" client left the chat");
				try {
					this.out.close();
					this.in.close();
					this.isLoggedIn = false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			
			catch (IOException e) {
				
				e.printStackTrace();
				
			}
		
		}
	}
	
	
}
