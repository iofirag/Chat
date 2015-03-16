//3

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.String;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
	private Socket socket = null;
	boolean continueRun;
	boolean massageBoard;
	private ClientGUI clientGui = null;
	// private MessageBoard mb = null;
	private ClientDescriptor clientD = null;
	private InputStream is = null; //
	private DataInputStream dis = null; // to send word
	private OutputStream os = null;
	private DataOutputStream dos = null;

	private String fromBoard = null;
	private String usrMsg = null;
	private String stringToDisconnect = "1n2m3k4l5l6l7;8l9lgfdsdfgwetwert6543v/;l.cnstresdfw443";

	
	public String getStringToDisconnect(){
		return stringToDisconnect;
	}
	public void setStringToDisconnect(String str){
		stringToDisconnect = str;
	}
	
	public DataOutputStream getDataOutputStream() { 
		return dos; 
	}

	public boolean isMassageBoard() {
		return massageBoard;
	}

	public void setMassageBoard(boolean massageBoard) {
		this.massageBoard = massageBoard;
	}

	public String getUsrMsg() {
		return usrMsg;
	}

	public void setUsrMsg(String usrMsg) {
		this.usrMsg = usrMsg;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ClientDescriptor getClientD() {
		return clientD;
	}

	public void setClientD(ClientDescriptor clientD) {
		this.clientD = clientD;
	}

	public boolean isContinueRun() {
		return continueRun;
	}

	public void setContinueRun(boolean bool) {
		this.continueRun = bool;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public String getFromBoard() {
		return fromBoard;
	}

	public void setFromBoard(String buff) {
		this.fromBoard = buff;
	}

	public ClientGUI getClientGui() {
		return clientGui;
	}

	public void setClientGui(ClientGUI clientGui) {
		this.clientGui = clientGui;
	}
	
	@Override
	public void removeConsumer(StringConsumer sc) {
		// TODO Auto-generated method stub
	}
	
	
	//if disconnect send string
	public void disconnect(){
		try{
			if (socket !=null)
				this.socket.close();
			
			if (clientGui !=null){				//GUI
				System.out.println("client: disconnecting..");
			}
			else{								//server
				System.out.println("server: disconnecting..");
				clientD.getMb().removeClient(this);
			}
			if (dis!=null)
				dis.close();
			if (is!=null);
				is.close();
			if (dos!=null);
				dos.close();
			if (os!=null);
				os.close();
			if (socket!=null);
				socket.close();
		} catch(IOException e){
			e.printStackTrace();
			this.continueRun=false;
		}
	}
	
//read
	public void run() {
			while (continueRun) {
				if (socket.isConnected()){
					try{
						if (clientGui != null)												///server ---> GUI
						{									//to GUI
							clientGui.getTextArea().append(dis.readUTF() + "\n");
						}
						else  {															///server <--- GUI
							consume(dis.readUTF());			//to server
						}
					}
					catch (IOException e){					//if have a problem with the server
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						System.out.println("error, force colsing connection.");
						continueRun=false;
					}
				}
				else{
					continueRun=false;
				}
			}
			disconnect();
	}


	public ConnectionProxy(Socket socket) {
		super();
		this.socket = socket;
		continueRun = true;
		//
		try {
			is = socket.getInputStream();
			System.out.println("***input stream was created...");
			dis = new DataInputStream(is);
			System.out.println("***data input stream was created...");
			os = socket.getOutputStream();
			System.out.println("***output stream was created...");
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void consume(String str) {					//message from proxy to ClientDescriptor	(server func)	add the reference to thiss to gui
		if (clientGui !=null){
			
		}
		else{
			if (str.compareTo(this.getStringToDisconnect()) != 0)
				clientD.consume(str);
			else{
				disconnect();
			}
		}
	}

	@Override
	public void addConsumer(StringConsumer sc) {
		// TODO Auto-generated method stub	
	}
}