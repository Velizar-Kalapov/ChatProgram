package chatServer;

import java.io.IOException;
import java.net.UnknownHostException;

import client.ChatWindow;

public class App {

	static int port; 
	
	public static void main(String[] args) throws UnknownHostException, IOException {	
			Thread serverThread = new Thread(new Runnable() {
				@Override 
					public void run() {
						try  {
							ChatServer chatServer = new ChatServer();
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("IOException when creating server");
						}		
					}
			});
			
			serverThread.start();
			
			Thread user1 = new Thread(new ChatWindow());
			Thread user2 = new Thread(new ChatWindow());
			
			user1.start();
			user2.start();

	}
	
	

}
