package client;

import java.io.IOException;

import javax.swing.JTextArea;

	public class Read extends Thread{
		JTextArea textArea;
		Client client;

			public Read(Client client, JTextArea textArea) {
				this.client = client;
				this.textArea = textArea;
			}


			@Override 
			public void run() {

				while (true) {
					try {	
						String recieved = client.getIn().readUTF();			
						System.out.println(recieved);
						textArea.append(recieved);

					} catch (IOException e) {
						System.out.println("Caught IOException when reading");
						break;
					}

				}
			}


	}