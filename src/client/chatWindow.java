package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class chatWindow {

	private JFrame frame;
	private JTextField textField;
	private Client client;
	private JTextArea textArea;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatWindow window = new chatWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	public chatWindow() {
			
				initialize();
			//	client = new Client();
				new Read().start();
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
	}
	
	
	class Read extends Thread{
		@Override 
		public void run() {
			try {
				client = new Client();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (true) {
				
				try {	
					String recieved = client.getIn().readUTF();			
					System.out.println(recieved);
					textArea.append(recieved + "\n");
				
				//}catch (SocketException e ) {
			//		System.out.println("socket closed");
				}catch (IOException e) {
					e.printStackTrace();
					break;
				}
				
			}
		}
	
	}
	
	
	
	
	
	
	
	
	


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 219, 182, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null) {
				 String message = textField.getText();
				 
				 
				 try {
					client.getOut().writeUTF(message);
				 } catch (IOException e1) {
					e1.printStackTrace();
				 }
				 
				}
				
			}
		});
		btnNewButton.setBounds(202, 218, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 400, 199);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		try {
		    			//client.getOut().writeUTF(message);
						client.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            System.exit(0);
		        
		    }
		});
		
		
	}
}
