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

	public ClientHandler(Socket socket, int id) throws IOException {
		this.socket = socket;
		this.id = id;
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
		isLoggedIn = true;
	}
	
	
	

	@Override
	public void run() {
		String received;
		while(true) {

			try{
				received = in.readUTF();
				System.out.println(received);

				if(received.equals("/logout")) {
					System.out.println("Client " + this.id + " Exiting");
					break;
				}

				for (ClientHandler c : ChatServer.clientList) {
					if(c.isLoggedIn) {
						System.out.println("Sent to: " + c.getId());
						c.getOut().writeUTF(this.id + " : "  + received);
					}
				}
				
			}catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
		}
		
		
		this.isLoggedIn = false;
		try {
			System.out.println("Closing connection");
			this.socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
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