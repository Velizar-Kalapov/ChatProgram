package client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import chatServer.ChatServer;

public class Client {

	static int ServerPort = 9090;
	DataInputStream in;
	DataOutputStream out;
	Socket socket;
	Boolean isLogged;

	public Client() throws UnknownHostException, IOException {

		socket = new Socket("127.0.0.1", ServerPort);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Connected");

	}
	
	
	
	public Client(JTextArea textArea) throws UnknownHostException, IOException {

		socket = new Socket("127.0.0.1", ServerPort);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Connected");
		
		isLogged = true;
		
		Thread thread = new Thread(new Runnable() {
			@Override 
				public void run() {
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
							e.printStackTrace();
							break;
						}
					}
					try {
						socket.close();
						in.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
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


}