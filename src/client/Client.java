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
	
	
//	public static void main(String[] args) {
//		try {
//			Client  Client = new Client();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	
	
	
	
	public DataInputStream getIn() {
		return in;
	}


	public DataOutputStream getOut() {
		return out;
	}


	public Socket getSocket() {
		return socket;
	}


	public Client() throws UnknownHostException, IOException {
		
		socket = new Socket("127.0.0.1", ServerPort);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		//Read read = new Read(this, textArea);
	//	read.start();
		System.out.println("Connected");
		 
	}
	
	public void disconnect() throws IOException {
		//sendMessage("/logout");
		socket.close();
		in.close();
		out.close();
	}
	
	
//	public void sendMessage(String message) {
//		Thread sendMessage = new Thread(new Runnable() {
//			@Override 
//			
//			public void run() {
//				while(true) {
//					try {
//						out.writeUTF(message);
//						
//						
//					} catch(IOException e) {
//						e.printStackTrace();
//					}
//					
//					
//				}
//			}
//			
//		});
//		
//		
//	}
	
}
