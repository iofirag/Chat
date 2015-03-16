import java.io.IOException;

//6

public class ClientDescriptor implements StringConsumer, StringProducer
{
	private String name = null;
	private String msg = null;
	private MessageBoard mb = null;
	private ConnectionProxy connectionP = null;
	
	public ConnectionProxy getConnectionP(){
		return connectionP;
	}
	public void setConnectionP(ConnectionProxy cp){
		this.connectionP = cp;
	}
	public ClientDescriptor() {
		super();
	}
	public ClientDescriptor(String name) {					///to chack if needed this func
		super();											//
		this.name = name;									//
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MessageBoard getMb() {
		return mb;
	}
	public void setMb(MessageBoard mb) {
		this.mb = mb;
	}
	
	public void wellcomM(){
		if (name!=null)
			mb.consume("> "+name + " is comming to the room.");
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
		if (mb != null) {
			if (name == null){
				name = new String (str) ;
			}
			else{
				String completeMsg = (name+": "+str);
				mb.consume(completeMsg);
			}
		}
	}
}
