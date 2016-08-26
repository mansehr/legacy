import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This class will handle traffic that only concern the AP and the CMS.
 * It will periodically send the list of its CC to the CMS. 
 * @author hMattias
 *
 */
public class AP_CMS implements Runnable{
	private String postHttp = "POST /ap_update.php HTTP/1.1\r\n" +
									 "Host: localhost\r\n"+
									 "Connection: close\r\n"+
									 "Content-Length: ";
	private final String IND = "     "; // indent for xml
	private WaitTime wait = new WaitTime();
	// Time interval between periodic sends to the CMS 
	private final long TIMER = 5 * 60 * 1000;
	
	
	/*
	 * This method will create a String containing the xml
	 * representation of all cc according to specification
	 * Either AP_CC_LIST:
	 * <list ap_id="{AP_ID}">
     *   <channel id="{CC_ID}" type="{CC_TYPE}" status="{CC_STATUS}" />
     *   .
     *   .
     *   .
     * </list>
     * Or if there are no cc:
     * <list /> 
	 */
	private String getAP_CC_LIST(){
		StringBuilder sb = new StringBuilder();
		if(AP_Settings.getComchans().size()<1){
			sb.append("<list />");
		}else{
			sb.append("<list ap_id=\""+AP_Settings.getApID()+"\">\n");
			for(ComChannel c : AP_Settings.getComchans()){
				try{
					sb.append(IND + "<channel id=\"" + c.id + "\" type=\""+
							c.type + "\" status=\"" + c.getStatus() + "\" />\n");
				}catch(ConfigFileException e){ 
					// Bad cc, write to logg and then skip it...
					AP_Settings.getLogg().writeToLogg("ComChannel ("+c.toString()+") contains an invalid statusnumber that does not match any status.","AP_CMS","getAP_CC_LIST()");  
				}
			}
			sb.append("</list>");
		}
		
		return sb.toString();
	}
	
	public void run() {
		while(AP_Settings.getAliveCMS()) {
			// Make a connection to the CMS and send the configuration information.
			
			Socket socket = null;
			try {
				socket = new Socket(AP_Settings.getCmsAdr(),81);
			} catch (IOException e) {
				AP_Settings.getLogg().writeToLogg("Failed to connect to the CMS using Socket socket = new Socket("+AP_Settings.getCmsAdr()+",80);",
						"AP_CMS","run()");
			}
			if(socket != null && socket.isConnected()){
				sendToCMS(socket);
				recieveFromCMS(socket);
			}else{
				AP_Settings.getLogg().writeToLogg("Failed to connect to the CMS using Socket socket = new Socket("+AP_Settings.getCmsAdr()+",80);",
						"AP_CMS","run()");
			}

			// Wait for the set number of milliseconds until sending again
			wait.waitTimer(TIMER);
//			try {
//				Thread.sleep(TIMER);
//			} catch (InterruptedException e) {
//				AP_Settings.getLogg().writeToLogg("Thread was interrupted during sleep. Message: ("+e+")",
//						"AP_CMS","run()");
//			}
		}
	}
	
	public void interruptWait(){
		wait.awake();
	}
	
	/*
	 * This method will read the response from the CMS. If there are any 
	 * errors they will be logged. Examples of responses:  
	 * <response code="200" />
	 * 	
	 * <response code="300">
	 * 	    <error_message> ... </error_message>
	 * </response>
	 * 	
	 * <response code="301">
	 *     <error_message> ... </error_message>
	 * </response>
	 */
	private void recieveFromCMS(Socket socket) {
		try{
			if(AP_Settings.DEBUG_AP_CMS){ System.err.println("\t---------Printint CMS response:------"); }
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = "";
			StringBuilder response = new StringBuilder();
			while((line=in.readLine())!=null){
				if(AP_Settings.DEBUG_AP_CMS) { System.err.println("\t"+line); }
				response.append(line);
			}
			int responceCode = extractResponseCode(response);
			// If there is an error (IE response code > 200)
			if(responceCode>200){
				extractErrorMessage(response);
			}
		}catch (IOException e) {
			AP_Settings.getLogg().writeToLogg("Error handling BufferedReader. Message: ("+e+")",
					"AP_CMS","recieveFromCMS()");
			System.exit(1);
		}
	}
	
	/*
	 * This method will log an error messages that is stored in a StringBuilder. 
	 */
	private void extractErrorMessage(StringBuilder sb) {
		 int indexStart=sb.indexOf("<error_message>")+15; //+15 to compensate for '</error_message>'
		 if(indexStart!= -1){
			 int indexEnd=sb.indexOf("</error_message>");
			 try{
				 String message = sb.substring(indexStart,indexEnd);
				 AP_Settings.getLogg().writeToLogg("Recieved errormessage from CMS: ("+message+")",
						 "AP_CMS", "extractErrorMessage()");
			 }catch(IndexOutOfBoundsException iobe){
				 AP_Settings.getLogg().writeToLogg("Recieved an invalid errormessage from CMS (IndexOutOfBoundException caught)",
						 "AP_CMS", "extractErrorMessage()");
			 }
		 }else{
			 // No error message
		 }
	}

	/*
	 * This method will extract the response code from a message following
	 * the following syntax: <response code="300">. And return it. If there
	 * is no response code or if there is some other error the return value
	 * will be -1 and the error will be logged.
	 */
	private int extractResponseCode(StringBuilder sb){
		if(sb.length()==0){
			AP_Settings.getLogg().writeToLogg("No responce recieved from CMS. ("+AP_Settings.getCmsAdr()+")",
				 "AP_CMS", "extractErrorMessage()");
			return -1;
		}
		int index = sb.indexOf("<response code=\"")+16; //+16 to compensate for the '<response code="'
		String errorCode = "";
		try{
			errorCode = sb.substring(index, index+3);
			return Integer.parseInt(errorCode);
		}catch(NumberFormatException nfe){
			AP_Settings.getLogg().writeToLogg("Invalid responceCode from CMS: ("+errorCode+")",
					"AP_CMS", "extractResponseCode()");
			return -1;
		}catch(StringIndexOutOfBoundsException siobe){
			AP_Settings.getLogg().writeToLogg("Invalid responceCode from CMS. (StringIndexOutOfBoundsException caught)",
					"AP_CMS", "extractResponseCode()");
			return -1;
		}
	}

	/*
	 * This method will send the list of cc to the CMS.
	 */
	private void sendToCMS(Socket socket) {
		PrintStream out = null;
		try {
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			AP_Settings.getLogg().writeToLogg("Error handling printStream. Message: ("+e+")",
					"AP_CMS","sendToCMS()");
			System.exit(1); 
		} 
		// Send the AP_CC_LIST to the CMS
		String ccList = getAP_CC_LIST();
		String message = postHttp+ccList.length()+"\r\n\r\n"+ccList;
		out.print(message);
		if(AP_Settings.DEBUG_AP_CMS){ System.err.println("\t----SENDING TO CMS ("+AP_Settings.getCmsAdr()+")----\n"+
				"\t"+message.replaceAll("\\\n", "\\\n\\\t"));}
	}
}

class WaitTime{
	private final Object lock = new Object();
	public synchronized void waitTimer(long milisec){
		try {
			synchronized(lock) {
				lock.wait(milisec);
			}
			
		} catch (InterruptedException e) {
			System.out.println("Thread interupt");
		}
	}
	public void awake() {
		synchronized(lock) {
			lock.notifyAll();
		}
	}
}