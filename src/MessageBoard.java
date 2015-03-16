import java.io.IOException;

//4

public class MessageBoard implements StringConsumer, StringProducer {
	int MAXusers = 50;
	private ConnectionProxy proxyArr[] = null;

	public MessageBoard() {
		super();
		this.proxyArr = new ConnectionProxy[MAXusers];
	}

	public void saveUserInArr(ConnectionProxy newUser) {
		boolean flag=false;
		for (int i = 0; i < this.MAXusers; i++) {
			if (proxyArr[i] == null) {
				proxyArr[i] = newUser;
				flag=true;
				break;
			}
		}
		if (flag == false){
			try {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				newUser.getDos().writeUTF("server full, try later.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				newUser.getDos().writeUTF("*****you are disconnected*****");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			newUser.disconnect();
		}
	}

	public void removeClient(ConnectionProxy cp){
		String u =cp.getClientD().getName();
		cp.setClientD(null);
		//cp.getClientD().setMb(null);					///?? vaild?
		for (int i=0; i<this.MAXusers; i++){
			//System.out.println(this.proxyArr[i]);
			if (this.proxyArr[i] == cp){
				this.proxyArr[i]=null;
				System.out.println("server close session completly.");
			}
		}
		consume ("> "+u+" has left the room.");
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
		for (int i = 0; i < MAXusers; i++) {
			if (proxyArr[i] != null) {
				try{
					proxyArr[i].getDos().writeUTF(str);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}