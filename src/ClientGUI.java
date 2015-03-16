import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.net.*;
import java.io.*;

//5

public class ClientGUI implements StringConsumer, StringProducer {
	private JFrame frame;									//pp
	private JLabel labelIp, labelPort, labelName;
	private JButton btConnect, btDisconnect, btSend;
	private JButton btLeft, btCenter, btRight;
	private JTextField tfIp, tfPort, tfLine, tfName; // tfLine
	private JPanel panelNorth, panelSouth, panelCenter;			//pp
	private String[] langs = { "English", "׳¢׳‘׳¨׳™׳×" };
	private JComboBox cbLang;
	private JTextArea clipBoard; // **//					//pp
	private JScrollPane sb;									//pp
	private ClientGUI thiss;
	public Socket socket = null;
	private ConnectionProxy proxy = null;
	private String usrMessage = null;
	public int cunterLines=1000000000;
	private String stringToDisconnect = "1n2m3k4l5l6l7;8l9lgfdsdfgwetwert6543v/;l.cnstresdfw443";

	public JButton getBtDisconnect(){
		return this.btDisconnect;
	}
	public String getStringToDisconnect(){
		return stringToDisconnect;
	}
	public void setStringToDisconnect(String str){
		stringToDisconnect = str;
	}
	public JTextArea getTextArea() {
		return clipBoard;
	}

	@Override
	public void addConsumer(StringConsumer sc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeConsumer(StringConsumer sc) {
		// TODO Auto-generated method stub
	}

	@Override
	public void consume(String str) {
		try {
			proxy.getDataOutputStream().writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void clipBoardWriter(String str) {
		this.clipBoard.append(str + "\n");
	}

	public ClientGUI() {
		// creating GUI components
		frame = new JFrame("Chat Client");
		btConnect = new JButton("Connect!");
		btDisconnect = new JButton("Disconnect");
		btSend = new JButton(" [Send] "); // ג–÷ ג†’ >
		tfIp = new JTextField(10);
		tfPort = new JTextField(3);
		labelIp = new JLabel("Server ip:");
		labelPort = new JLabel("Port:");
		labelName = new JLabel("Name:");
		tfName = new JTextField(5);
		cbLang = new JComboBox(langs);
		tfLine = new JTextField(30); // **//
		clipBoard = new JTextArea(11, 45);
		sb = new JScrollPane(clipBoard);
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		panelCenter = new JPanel();

		thiss = this;

		sb.getVerticalScrollBar().addAdjustmentListener(						//JScrollpane - Force autoscroll to bottom
				new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						clipBoard.select(clipBoard.getHeight() +thiss.cunterLines, 0);
					}
				});

		// getting frame content pane
		//Container container = frame.getContentPane();										///??

		// assigning background colors for each one of the panels
		panelNorth.setBackground(Color.lightGray);
		panelCenter.setBackground(Color.lightGray);
		panelSouth.setBackground(Color.lightGray);
		// ////tfLine.setBackground(Color.white);

		// assigning layout managers for each one of the containers
		// panelNorth.setLayout(new BorderLayout());
		// panelSouth.setLayout(new BorderLayout());
		// panelCenter.setLayout(new BorderLayout());
		
		//container.setLayout(new BorderLayout());										//????
		frame.setLayout(new BorderLayout());										//????

		// placing the components above the containers
		panelNorth.add(cbLang);
		panelNorth.add(labelIp);
		panelNorth.add(tfIp);
		panelNorth.add(labelPort);
		panelNorth.add(tfPort);
		panelNorth.add(labelName);
		panelNorth.add(tfName);
		panelNorth.add(btConnect);
		panelNorth.add(btDisconnect);

		panelCenter.add(sb); // **//
		panelSouth.add(tfLine);
		panelSouth.add(btSend);
		frame.add(panelNorth, BorderLayout.NORTH);
		frame.add(panelCenter, BorderLayout.CENTER);
		frame.add(panelSouth, BorderLayout.SOUTH);

		// visible start
		btDisconnect.setVisible(false);
		tfLine.setVisible(false);
		btSend.setVisible(false);
		clipBoard.setLineWrap(true);
		clipBoard.setEnabled(false);
		// clipBoard.setEditable(false);
		
		tfIp.setText("localhost");
		tfPort.setText("1900");
		
		// *********************************************************
		// handling language action events
		ActionListener Llang = new ActionListener() // take the lang user choose
		{
			public void actionPerformed(ActionEvent event) {
				if (cbLang.getSelectedIndex() == 1) {
					clipBoard
							.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // for
																							// Hebrew
					sb.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // for
																					// Hebrew
					tfLine.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					tfName.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					btConnect.setLabel("׳”׳×׳—׳‘׳¨!");
					btDisconnect.setLabel("׳”׳×׳ ׳×׳§");
					btSend.setLabel(" ׳©׳�׳— "); // ג–÷ ג†’ >
					labelName.setText(" ׳›׳™׳ ׳•׳™: ");
				} else {
					clipBoard
							.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); // for
																							// Hebrew
					sb.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); // for
																					// Hebrew
					tfLine.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					tfName.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					btConnect.setLabel("Connect!");
					btDisconnect.setLabel("Disconnect");
					btSend.setLabel(" [Send] "); // ג–÷ ג†’ >
					labelName.setText(" Name: ");
				}
			}
		};
		
		
		//Listeners
		// *********************************************************
		// Handling key pressed Enter event //
		tfName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btConnect.doClick();
				}
			}
		});
				
		// *********************************************************
		//Handling key pressed Enter event										//
		tfLine.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btSend.doClick();
				}
			}
		});
		
		// *********************************************************
		// handling connect action events
		ActionListener Lconnect = new ActionListener() // take the ip & port
		{
			public void actionPerformed(ActionEvent event) {
				boolean connect = false; // for chack change to true
				long i = 0;
				//
				cbLang.setVisible(false);
				//tfIp.setText("localhost"); // temp
				//tfPort.setText("1900"); // temp
				// tfName.setText("OFIR"); //temp
				String ip = tfIp.getText(); // -----------------------------------------------------------------||
				String port = tfPort.getText(); // port =
												// Integer.parseInt(tfPort.getText());-----------------------||
				int portInt = Integer.parseInt(port);
				String name = tfName.getText(); // -----------------------------------------------------------------||
				//
				btConnect.setEnabled(false);
				if (ip.isEmpty() || port.isEmpty() || name.isEmpty()) { // can
																		// chack
																		// more
																		// things
																		// (-num),
																		// (characters)
					connect = false;
					clipBoard.append("***Add first: ip, port, name\n");
				} else {
					while (i < 5 && connect != true) {
						if (i == 0) {
							clipBoard.append("***Connecting to the server at.."
									+ ip + " : " + port + "***\n");
						} else {
							clipBoard.append("***(" + (i + 1)
									+ ") Connecting to the server at.." + ip
									+ " : " + port + "***\n");
						}
						// ----------add here connection to
						// server-------------------------------
						//
						try {
							socket = new Socket(ip, portInt);
							proxy = new ConnectionProxy(socket);
							proxy.setClientGui(thiss);
							proxy.start();
							clipBoard.append("***socket was created...\n");
							connect = true;
						} catch (IOException e) {
							e.printStackTrace();
						}
						i++;
					}
				}

				if (connect) {
					frame.setSize(580, 310);
					consume(name);
					clipBoard.append("***You are connected!***\n");
					//
					//
					btConnect.setVisible(false);
					btDisconnect.setVisible(true);
					tfIp.setEnabled(false);
					//
					tfPort.setEnabled(false);
					//
					tfName.setBackground(Color.green);
					tfName.setEnabled(false);
					tfName.setDisabledTextColor(Color.black);
					//
					btConnect.setVisible(false);
					tfLine.setVisible(true);
					btSend.setVisible(true);
					tfLine.requestFocus();
					panelSouth.setBackground(Color.white);
					clipBoard.setDisabledTextColor(Color.black);
				} else {
					btConnect.setEnabled(true);
					clipBoard.append("******Not connected!");
					tfName.setBackground(Color.orange);
				}
			}
		};

		// ****************************************************************
		// handling disconnect action events
		ActionListener Ldisconnect = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proxy.disconnect();
				cbLang.setVisible(true);
				// ----add more
				tfName.setEnabled(true);
				tfName.setBackground(Color.white);
				btConnect.setEnabled(true);
				btDisconnect.setVisible(false);
				btConnect.setVisible(true);
				tfLine.setVisible(false);
				btSend.setVisible(false);
				tfIp.requestFocus();
				//
				tfIp.setEnabled(true);
				tfPort.setEnabled(true);
				//
				frame.setSize(580, 275);
				panelSouth.setBackground(Color.lightGray);
				
				//disconnect
				// ----add more
				//proxy.continueRun=false;
				clipBoard.append("******you are disconnected******"+"\n");
			}
		};
		// ****************************************************************
		// handling send action events
		ActionListener Lsend = new ActionListener() // take the message
		{
			public void actionPerformed(ActionEvent event) {
				if (thiss.socket.isConnected()){
					usrMessage = tfLine.getText();
	
					if (!usrMessage.isEmpty()    ){ // do no send empty message          if (!usrMessage.isEmpty() && (usrMessage.compareTo(proxy.getClientGui().getStringToDisconnect()) !=0  ) ){ // do no send empty message
						consume(usrMessage);
						tfLine.setText(null);
						tfLine.requestFocus();
					}
				}
			}
		};
		// ******************************
		// *****************************
		cbLang.addActionListener(Llang);
		btConnect.addActionListener(Lconnect);
		btDisconnect.addActionListener(Ldisconnect);
		btSend.addActionListener(Lsend);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
	}

	public void play() {
		frame.setSize(580, 275);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		ClientGUI gui = new ClientGUI();
		gui.play();
	}

}

/*
 * public void start() { frame.addWindowListener(new WindowAdapter() { public
 * void windowClosing(WindowEvent event) { frame.setVisible(false);
 * frame.dispose(); System.exit(0); } });
 * 
 * //when play frame.setSize(400, 300); frame.setVisible(true);
 * 
 * }
 * 
 * public static void main(String[] args) { // TODO Auto-generated method stub
 * 
 * SwingUtilities.invokeLater(new Runnable() {
 * 
 * @Override public void run() { ClientGUI gui = new ClientGUI(); gui.start();
 * 
 * }
 * 
 * }
 * 
 * );
 * 
 * }
 * 
 * }
 */
