import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.infowireless.services.SmsMessageWebServiceStub;
import com.infowireless.services.SmsMessageWebServiceStub.SendMessage;
import com.infowireless.services.SmsMessageWebServiceStub.SendMessageResponse;
import com.infowireless.services.SmsMessageWebServiceStub.SmsMessage;
import com.infowireless.services.SmsMessageWebServiceStub.SmsStatus;

/*
 * Must compile with axis2 library(axis2-1.4.1.jar)
 * 
 */
public class SmsSender implements Runnable {
	
	/* Static variables */
	private final static String USER_ID = "mvk08";
	private final static String PASSWORD = "josefine";
	private static int port = 85;
	private static String sender = "DUnit";
	private static boolean SEND_TO_SERVER = false;
	private static int clients = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		if(args.length >= 1) {
			port = Integer.parseInt(args[0]);
		} 
		if(args.length >= 2) {
			sender = args[1];
		}
		if(args.length >= 3) {
			if(args[2].equals("1")) {
				SEND_TO_SERVER = true;
			} 
		} 
		
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("KK SMS-Sender started listening for incomming connections.");
			System.out.println("Port: "+port);
			System.out.println("Sender: " + sender);
			if(SEND_TO_SERVER) {
				System.out.println("Sending messages to server");
			} else {
				System.out.println("NOT sending messages to server");
			}
			System.out.println("Terminate program with Ctrl+C");	
			
			while(true) {	
				Socket s = ss.accept();
				new Thread(new SmsSender(s)).start();
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/* SmsSender class */ 
	
	private Socket s;
	private int client_id;
	private PrintWriter pw;	
	private StringBuilder log_builder;
	private String fileName;
    private String status_str;
    private SmsMessage smsMessage;
		
	public SmsSender(Socket socket){
		this.s = socket;
		clients++;
		client_id = clients;
		log_builder = new StringBuilder();
		fileName = "";
		status_str = "";
		
		smsMessage = new SmsMessage();
		smsMessage.setSenderId(sender);
		smsMessage.setUsername(USER_ID);
		smsMessage.setPassword(PASSWORD);
		try {
			pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			log(e.getMessage());
		}
	}

	public void run() {
		log("____New Message____");
		log("-- Sender: "+s.getInetAddress().getHostAddress());
		
		StringBuffer sb = new StringBuffer();
		String str = null;
		BufferedReader br;
		
		try {
			//"ISO-8859-1"
			br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
			
			while((str = br.readLine()) != null) {	// Read http headers
				log(str);
				if(str.equals("")) {
					break;
				} 
				if(str.toLowerCase().startsWith("additional-data:")) {
					String recipient = str.substring("additional-data:".length()).trim();
					smsMessage.setRecipient(recipient);					
				}
			}
			
			if(smsMessage.getRecipient().equals("")) {
				response(362, "Missing recipient data, no additional-data: in header?");
				return;
			}
				
			
			while((str = br.readLine()) != null) {	// Read http body <XML Schema>
				sb.append(str+"\n");
				log(str);
			}
			smsMessage.setMessage(sb.toString());			
				        
	        // We have all the variables now we can try to send the message
	                
	        if(SEND_TO_SERVER) {
	        	SmsMessageWebServiceStub sms = new SmsMessageWebServiceStub();
				SendMessage sendMessage0 = new SendMessage();
				
				sendMessage0.setSmsMessage(smsMessage );
				SendMessageResponse mr;
				mr = sms.sendMessage(sendMessage0 );
				SmsStatus status = mr.getSmsStatus();
				
				fileName = "sentMessages.log";
				status_str = "Status: "+status.getStatus()+"\r\nMessageId:"+status.getId();
				log(status_str);
	        	log("-- Message sent to server.");
	        } else {
	        	fileName = "unSentMessages.log";
	        	status_str = "Status: Unsent";
	        	log("-- Message not sent to servern but stored i message file.");
	        }
	        response(200, null);
	        
	        
	            
	        br.close();
		}
		 catch (AxisFault e) {
			response(361, "Failed to connect to the server");
			e.printStackTrace();
		} catch (RemoteException e) {
			response(361, "Failed to send message to the server");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			response(362, "Failed to encode the data");
			e.printStackTrace();
		} catch (IOException e) {
        	log("-- Failed to send sms. Sender: "+s.getInetAddress().getHostAddress());
        	log("-- Message: "+e.getMessage());
        	response(399, e.getMessage());
        	e.printStackTrace();
			return;
		}  finally {				
			pw.close();
			log("\n");
			printLog();
		}
	}
	
	/**
	 * Response prints the response to the client
	 * @param code - the specified response code, if 200 message is ignored
	 * @param message - Message to the client
	 */
	private void response(int code, String message) {
		
		if(code == 200) {
			pw.println("<response code=\"200\" />");
		} else {
			pw.println("<response code=\""+code+"\">");
			pw.println("<error_message>"+message+"</error_message>");
			pw.println("</response>");
		}
		log("-- Response(code: "+code+", Message: "+message+")");
	}
	
	private void log(String message) {
		System.out.println("client: "+client_id+", "+message);	
		log_builder.append(message+"\r\n");
	}
	
	private void printLog() {
		FileWriter f;
		try {
			f = new FileWriter(fileName,true);
		
	        synchronized(f) {
	        	SimpleDateFormat dateformat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	        	f.append(dateformat.format(new Date()));
	        	f.append(status_str);
	        	f.append(log_builder);
	        	f.flush();
	        	f.close();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

