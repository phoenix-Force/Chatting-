package Pack1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;




public class chatting {
	private JTextField msg;
	private JTextField tip;
	private JTextField tpn;
	String fmsg = null;
	Socket s;
	ServerSocket ss;
	public static DataInputStream din;
	public static DataOutputStream dout;
	int flag = 0;

	private JFrame frmClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chatting window = new chatting();
					window.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public chatting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Whatsapp Clone");
		frmClient.getContentPane().setBackground(new Color(0,94,84));
		frmClient.getContentPane().setForeground(new Color(0, 0, 0));
		frmClient.setBounds(100, 100, 637, 442);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 57, 601, 289);
		scrollPane.getHorizontalScrollBar().setForeground(new Color(128,128,128));
		scrollPane.getVerticalScrollBar().setForeground(new Color(64,64,64));
		frmClient.getContentPane().add(scrollPane);
		
		final JTextArea txtar = new JTextArea();
		scrollPane.setViewportView(txtar);
		txtar.setWrapStyleWord(true);
		txtar.setRows(12);
		txtar.setForeground(new Color(51, 51, 0));
		txtar.setFont(new Font("Verdana", Font.PLAIN, 18));
		txtar.setEditable(false);
		txtar.setBackground(Color.WHITE);
		
		final JRadioButton radio = new JRadioButton("Sender");
		radio.setBackground(new Color(255, 255, 204));
		radio.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 22));
		radio.setBounds(371, 15, 89, 24);
		frmClient.getContentPane().add(radio);
		
		msg = new JTextField();
		msg.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode()==KeyEvent.VK_ENTER) {
					
					(new Thread() {
						  public void run() {
						try{
								String txt =msg.getText();
								if(txt!=null) {
								dout = new DataOutputStream(s.getOutputStream());
								dout.writeUTF(txt);
								txtar.append("you:"+" "+txt+"\n");
								msg.setText(null);
								}
							}catch(Exception e){
							//JOptionPane.showMessageDialog(null,"1"+ e);
							}
						  }
						 
					 }).start();
					
					
				}
				
				
			}
		});
		msg.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		msg.setBounds(10, 358, 489, 35);
		frmClient.getContentPane().add(msg);
		msg.setColumns(10);
		 
		
		
		
		JButton sendmsg = new JButton("Send");
		sendmsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				(new Thread() {
					  public void run() {
					try{
							String txt =msg.getText();
							if(txt!=null) {
							dout = new DataOutputStream(s.getOutputStream());
							dout.writeUTF(txt);
							txtar.append("you:"+" "+txt+"\n");
							msg.setText(null);
							}
							else {
								JOptionPane.showMessageDialog(null, "Enter Some Messege");
							}
						}catch(Exception e){
							//JOptionPane.showMessageDialog(null,"2"+e);
						}
					  }
					 
				 }).start();
				}
		});
		sendmsg.setForeground(Color.WHITE);
		sendmsg.setBackground(new Color(0, 153, 204));
		sendmsg.setFont(new Font("Times New Roman", Font.BOLD, 18));
		sendmsg.setBounds(509, 357, 102, 35);
		frmClient.getContentPane().add(sendmsg);
		
		tip = new JTextField();
		tip.setFont(new Font("Times New Roman", Font.BOLD, 19));
		tip.setBounds(10, 11, 199, 35);
		frmClient.getContentPane().add(tip);
		tip.setColumns(10);
		
		tpn = new JTextField();
		tpn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tpn.setBounds(231, 12, 122, 35);
		frmClient.getContentPane().add(tpn);
		tpn.setColumns(10);
		
		final JButton cnct = new JButton("Connect");
		cnct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				(new Thread() {
					  public void run() {
						 try{
						  if(cnct.getText()=="Disconnect"){
							  JOptionPane.showMessageDialog(null, " Disconnected Successfully");
							  cnct.setBackground(new Color(51,102,51));
								cnct.setText("Connect");
						  }
							  s.close();
							  ss.close();
							  System.exit(MAX_PRIORITY);
						  }catch(Exception e){
								//JOptionPane.showMessageDialog(null,"3"+ e);
						  }
					    
			if(radio.isSelected() && cnct.getText()=="Connect"){
				JOptionPane.showMessageDialog(null, "I AM A CLIENT");
				try{
						InetAddress addr = InetAddress.getByName(tip.getText());
						JOptionPane.showMessageDialog(null, addr);
						
						int port=Integer.parseInt(tpn.getText());
						//JOptionPane.showMessageDialog(null,port);
						
						
						 s = new Socket(addr,port);
						JOptionPane.showMessageDialog(null,"Connecting");
						if(s.isConnected()){
							cnct.setBackground(Color.RED);
							cnct.setText("Disconnect");
						}
					}catch(Exception e){
					//JOptionPane.showMessageDialog(null,"4"+ e);
					}
				}
			else if( cnct.getText()=="Connect"){
				try{
				JOptionPane.showMessageDialog(null, "I AM A SERVER");
						JOptionPane.showMessageDialog(null, "waiting For Connection");
						int port=Integer.parseInt(tpn.getText());
					    ss = new ServerSocket(port);
						s = ss.accept();
						if(s.isConnected()){
							cnct.setBackground(Color.RED);
							cnct.setText("Disconnect");
						}
						JOptionPane.showMessageDialog(null, "Connected");
						
						
						
					}catch(Exception e){
						//JOptionPane.showMessageDialog(null,"5"+ e);
					}
				}
					  }
					 }).start();
			}		  		  
		});
		
		
		
		cnct.setForeground(Color.WHITE);
		cnct.setBackground(new Color(51, 102, 51));
		cnct.setFont(new Font("Times New Roman", Font.BOLD, 18));
		cnct.setBounds(476, 11, 135, 35);
		frmClient.getContentPane().add(cnct);
		
		
		
		
		
		
		(new Thread() {// new Inline Thread
			public void run(){
				while(true) {
					try {
						din =new DataInputStream(s.getInputStream());
						String msg = din.readUTF();
						txtar.append("Friend:"+" "+msg+ "\n");
					} catch (Exception e) {
						//JOptionPane.showMessageDialog(null,"6"+ e);
					}
					
					
				}
			}
		}).start();

		
		
		
	}

}
