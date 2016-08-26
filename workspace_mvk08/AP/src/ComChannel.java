/**
 * This container class represents a communication channel and
 * stores the type, id status and port. 
 * @author hMattias
 *
 */
public class ComChannel {
	public String type;
	public String id;
	public int status;
	public int port;
	
	public ComChannel(String type, String id, int port, int status){
		this.id=id;
		this.type=type;
		this.port=port;
		this.status=status;
	}
	
	/**
	 * This channel will convert the status-int to a 
	 * string and return it.
	 * @return the status of the cc: inactive, online, not_responding, config_error
	 * @throws ConfigFileException 
	 */
	public String getStatus() throws ConfigFileException{
		switch(status){
			case(0): return "inactive"; 
			case(1): return "online"; 
			case(2): return "not_responding";
			case(3): return "config_error";
			default: throw new ConfigFileException("Incorrect value in ComChannel: "+toString()); 
		}
	}
	
	/**
	 * @return the id, type, statusNr and port of this ComChannel.
	 */
	public String toString(){
		return "id="+id+" type="+type+" statusNr="+status+" port="+port; 
	}
	
 }
