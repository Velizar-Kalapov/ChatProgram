package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

import chatServer.ChatServer;

public class Client {

	private static int ServerPort = 9090;
	private static String host = "localhost";
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;
	private Boolean isLogged;
	private String name;

	public Client() throws UnknownHostException, IOException {
		socket = new Socket(host, ServerPort);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Connected");

	}
	
	
	
	public Client(String name, JTextArea textArea) throws UnknownHostException, IOException {
		
		if (name == null || name.trim().equals("")) {
			this.name = new String("Guest");
		}else {
			this.name = name;
		}
		
		socket = new Socket(host, ChatServer.getPort());
		
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF(this.name);
		
		isLogged = true;
		
		Thread thread = new Thread(new Runnable() {
			@Override 
				public void run() {
				
				try {
					
					textArea.append(" Welcome " + name + "\n");
					
					while(true) {
						try {
							while (isLogged) {
								if(in.available() > 0) {	
									String recieved = in.readUTF();
									textArea.append(recieved + "\n");
								}
							} 
							break;
							
						} catch (IOException e) {
							System.err.println("Caught IOException while reading");
							break;
						}
					}
					
				}finally {
						try {
							socket.close();
							in.close();
							out.close();
						} catch (IOException e) {
							System.err.println("Caught IOException while closing");
						}
				}		
			   }
				
		});
		
		thread.start();
		
	}
	

	public void disconnect() throws IOException {
		out.writeUTF("/logout");
		isLogged = false;
	}
	
	
	public DataInputStream getIn() {
		return in;
	}


	public DataOutputStream getOut() {
		return out;
	}


	public Socket getSocket() {
		return socket;
	}
	
	public static void setServerPort(int port) {
		ServerPort = port;
	}

	public String getName() {
		return name;
	}

}