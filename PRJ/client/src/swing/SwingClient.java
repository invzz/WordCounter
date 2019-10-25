package swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import interfaces.ClientInt;
import models.Client;

public class SwingClient extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected SwingClient gui = this;
	
	protected JTextField txtAddress;
	protected JTextField txtUserId;
	protected JTextField txtName;
	protected JTextField textArea;
	protected JTextField txtSurname;
	protected JTextField txtLoginId;
	protected JTextArea textLogger;
	protected JTextArea textData;

	protected JButton btnSetAddress;
	protected JButton btnSignIn;
	protected JButton btnSend;
	protected JButton btnLogin;
	protected JButton btnLogout;
	protected JButton btnTop;
	protected JButton btnSignOut;
	
	protected JScrollPane scrollPane_1;
	
	private Client client;
	private boolean status = false;
	

	
		
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingClient main = new SwingClient();
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	
	
	//Constructor
	public SwingClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Word Counter - Client");
		this.setBounds(100, 100, 609, 561);
		getContentPane().setLayout(null);
		
		txtAddress = new JTextField();
		txtAddress.setText("127.0.0.1");
		txtAddress.setBounds(12, 33, 152, 25);
		getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
		
		txtUserId = new JTextField();
		txtUserId.setText("test");
		txtUserId.setColumns(10);
		txtUserId.setBounds(12, 159, 152, 25);
		getContentPane().add(txtUserId);
		
		txtName = new JTextField();
		txtName.setText("tester");
		txtName.setColumns(10);
		txtName.setBounds(12, 190, 152, 25);
		getContentPane().add(txtName);
		
		txtLoginId = new JTextField();
		txtLoginId.setText("test");
		txtLoginId.setColumns(10);
		txtLoginId.setBounds(12, 70, 284, 25);
		getContentPane().add(txtLoginId);
		
		txtSurname = new JTextField();
		txtSurname.setText("testor");
		txtSurname.setColumns(10);
		txtSurname.setBounds(12, 221, 152, 25);
		getContentPane().add(txtSurname);
		
		textArea = new JTextField();
		textArea.setEnabled(false);
		textArea.setText("Mura");
		textArea.setColumns(10);
		textArea.setBounds(12, 273, 152, 25);
		getContentPane().add(textArea);
	
	
		btnSetAddress = new JButton((String) null);
		btnSetAddress.setBounds(176, 33, 120, 24);
		btnSetAddress.setText("Set");
		getContentPane().add(btnSetAddress);
		
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(12, 107, 76, 24);
		getContentPane().add(btnLogin);
		
		btnLogout = new JButton("Logout");
		btnLogout.setEnabled(false);
		btnLogout.setBounds(100, 107, 73, 24);
		getContentPane().add(btnLogout);
		
		
		btnSignIn = new JButton("Sign In");
		btnSignIn.setBounds(176, 159, 120, 87);
		btnSignIn.setEnabled(false);
		getContentPane().add(btnSignIn);
		
		btnSend = new JButton("Send Text");
		btnSend.setEnabled(true);
		btnSend.setBounds(176, 272, 120, 227);
		getContentPane().add(btnSend);
		
		
		btnTop = new JButton("top 3");
		btnTop.setEnabled(false);
		btnTop.setBounds(318, 451, 261, 48);
		getContentPane().add(btnTop);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(308, 12, 289, 425);
		getContentPane().add(scrollPane);
		
		textLogger = new JTextArea();
		scrollPane.setViewportView(textLogger);
		textLogger.setDragEnabled(true);
		textLogger.setEditable(false);
		textLogger.setForeground(Color.GREEN);
		textLogger.setFont(new Font("Dialog", Font.PLAIN, 13));
		textLogger.setBackground(Color.BLACK);
		textLogger.setWrapStyleWord(true);
		textLogger.setLineWrap(true);
		textLogger.setAutoscrolls(true);
		
		btnSignOut = new JButton("Sign Out");
		btnSignOut.setEnabled(false);
		btnSignOut.setBounds(185, 107, 111, 25);
		getContentPane().add(btnSignOut);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 310, 151, 202);
		getContentPane().add(scrollPane_1);
		
		textData = new JTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
				+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, "
				+ "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
				+ "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore "
				+ "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
				+ "sunt in culpa qui officia deserunt mollit anim id est laborum.");
		textData.setLineWrap(true);
		scrollPane_1.setViewportView(textData);
		textData.setWrapStyleWord(true);
		textData.setEnabled(false);
	
	
		// listeners
		
		this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	        	try {
	        		(new Connector(gui,"logOut")).execute();
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        }
	    });
		btnSetAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.enabled(false);
				client = new Client(gui);
				log("Server addres is set to :  "+ gui.getServerIP() );
				gui.enabled(false);
			}
		});
		
				
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"logIn")).execute();
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"logOut")).execute();
			}
		});
	
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"signIn")).execute();
			}
		});
		
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"send")).execute();
			}
		});
		
		btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"top")).execute();
			}
		});
		
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Connector(gui,"remove")).execute();
			}
		});
	}	
	
	
	//logging
	public void log(String s) {
		SwingUtilities.invokeLater(new Runnable(){
            public void run(){
            	textLogger.append(s+"\n");
            	scrollDown();
            	
            }
		});
	}
	
	private void scrollDown(){
	    textLogger.setCaretPosition(textLogger.getText().length());
	}

	//getters and setters
	public String getNameTxt() {
		return this.txtName.getText();
	}
	
	public String getSurnameTxt() {
		return this.txtSurname.getText();
	}
	
	public String getUserId() {
		return this.txtUserId.getText();
	}
	
	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean newStatus) {
		this.status = newStatus;
		
	}

	public String getCurrentId() {
		return this.txtLoginId.getText();
	}
	
	public String getServerIP() {
		return this.txtAddress.getText();
	}
	
	
	public void enabled(boolean b) {
		this.btnLogout.setEnabled(b);
		this.btnLogin.setEnabled(!b);
		this.btnSend.setEnabled(b);
		this.btnSignIn.setEnabled(!b);
		this.btnSignOut.setEnabled(b);
		this.btnTop.setEnabled(b);
		this.textArea.setEnabled(b);
		this.textData.setEnabled(b);
		this.txtLoginId.setEnabled(!b);
		this.txtAddress.setEnabled(!b);
	}

	public ClientInt getClient() {
		return client;
	}
	
	
}