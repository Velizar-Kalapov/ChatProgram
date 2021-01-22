package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChatWindow implements Runnable {

	public JFrame frame;
	private JTextField textField;
	private Client client;
	private JTextArea textArea;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					ChatWindow window;
					try {
						window = new ChatWindow();
						window.frame.setVisible(true);
						window.client = new Client(window.textArea);
					} catch (IOException e) {
						System.err.println("Caught IOException");
						System.exit(0);
					}
				
			}
		});
	}
	
	@Override
	public void run() {
		ChatWindow chatWindow;
		try {
			chatWindow = new ChatWindow();
			chatWindow.frame.setVisible(true);
			chatWindow.client = new Client(chatWindow.textArea);
		} catch (IOException e) {
			System.out.println("IOException when creating chat window");
		}
		
	}

	public ChatWindow() throws UnknownHostException, IOException {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 409, 305);
		//frame.setResizable(false);
		frame.setForeground(new Color(44, 62, 80));
		frame.getContentPane().setBackground(new Color (44, 62, 80));
		frame.getContentPane().setForeground(new Color (44, 62, 80));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(10, 219, 182, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBackground(new Color(41, 128, 185));
		btnNewButton.setFocusable(false);
		btnNewButton.setBorder(null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null) {
				 String message = textField.getText();
				 if(message.length() > 0) {

					 try {
						 client.getOut().writeUTF(message);
						 textField.setText("");
					 } catch (IOException e1) {
						 System.err.println("Caught IOException while sending message");
					 }

				}
				} 	
			}
		});
		btnNewButton.setBounds(202, 218, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 10, 281, 199);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBorder(null);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textArea);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		try {
						client.disconnect();
					} catch (IOException e) {
						System.err.println("Caught IOException while disconnecting");
					}

		    }
		});


	}

}