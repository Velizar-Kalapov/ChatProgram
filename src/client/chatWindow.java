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



	public chatWindow() throws UnknownHostException, IOException {
		initialize();
		client = new Client(textArea);
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

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null) {
				 String message = textField.getText();
				 if(message.length() > 0) {

					 try {
						 client.getOut().writeUTF(message);
						 textField.setText("");
					 } catch (IOException e1) {
						 e1.printStackTrace();
					 }

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
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(textArea);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    		try {
						client.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}
		            System.exit(0);

		    }
		});


	}
}