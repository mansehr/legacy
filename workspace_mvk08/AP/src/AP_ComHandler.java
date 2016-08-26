import java.io.*;
import java.net.*;

/***
 * The AP_ComHandler class handles all communication that passes
 * the AP. All communication is done with sockets. The AP listens 
 * for incoming connections on port 80. 
 *
 */
public class AP_ComHandler implements Runnable{
	private ServerSocket ss;
	
	/**
	 * This is the compulsory run-method in the AP_ComHandler thread. 
	 * The method has a serversocket that listens on port 80 for incoming
	 * connections. When a connection is found the method creates a new 
	 * thread (socketThread) to handle the connection and then continues to listen 
	 * for new connections. 
	 * 
	 * Every connection gets it own thread in order to not block AP_ComHandler from
	 * receiving new connections. When a connections has fulfilled its meaning the 
	 * thread is closed. 
	 */
	public void run() {
		try {
			// Creates a new serversocket that listens on port 80
			ss = new ServerSocket(80);
		} // Captures any IOExceptions that could have occurred during the creation of the serversocket
		catch (IOException e) {
			AP_Settings.getLogg().writeToLogg("Failed to start the ServerSocket. ("+e+")"
					,"AP_ComHandler","run()");
			System.exit(1);
		}
		
		// A while loop that listens after new connections
		while(AP_Settings.getAliveComHandler()){
			//------------------ From --> To --> Forward to -----------------------//
			//------------------   XX --> AP --> YY -------------------------------//
			if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\tWaiting for connection...");}
			Socket current = null;
			try {
				current = ss.accept();
				if(AP_Settings.getAliveComHandler()){ //Skip if marked for shutdown
					if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\tFound connection!");}
					// When a connection is found a new socketThread is created to handle the connection
					// so that AP_ComHandler can listen after new connections. 
					new Thread(new SocketThread(current)).start();
				}
			} // Catches any IOExecption that could have occurred during the accepting of a new connection. 
			catch (IOException e) {
				if(!ss.isClosed()){
					AP_Settings.getLogg().writeToLogg("Failed to accept incoming connection. ("+e+")"
							,"AP_ComHandler","run()");
				}else{
					// Not an error, the socket was closed to terminate this thread
				}
			}
		}
	}
	
	public void interruptRun(){
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a private class that handles socket connections that the AP_ComHandler receives. 
	 */
	private class SocketThread implements Runnable{
		private Socket socket;
		
		public SocketThread(Socket socket){
			this.socket = socket;
		}

		public void run() {
			/**
			 * This is the compulsory run-method for the socketThread class. The method determines
			 * the type of the recieved connection and then calls the appropriate method to handle
			 * that type of connection. 
			 * 
			 * It works by reading the first line from the stream, parsing that line to determine
			 * how it should handle the connection. 
			 * The alternatives are two: 
			 * 	- A GET-request from a CC
			 *  - A HTTP-package from the CMS
			 */
			try{
				// Creates Input/Output stream readers and writers. 
				PrintStream currentOut  = new PrintStream(socket.getOutputStream());
				BufferedReader currentIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String firstLine = currentIn.readLine();
	
				// Divides the firstline on the space character
				String[] firstLineArray = firstLine.split(" ");
				// Parses the firstline and calls the appropriate method
				if(firstLine.substring(0,3).equals("GET")){
					// Received GET-request from a CC
					//---------------- CC --> AP --> CMS -----------------------------//
					getRequest(firstLineArray, socket, currentIn, currentOut);				
				}else if(firstLine.substring(0,4).equals("HTTP")){
					// Received HTTP-package from the CMS
					//---------------- CMS --> AP --> CC -----------------------------//
					receivedHttpPackage(firstLineArray, socket, currentIn, currentOut);
				}else{
					AP_Settings.getLogg().writeToLogg("Unknown package recieved. Firstline did not contain HTTP|GET ("+firstLine+")",
							"SocketThread","run()");
				}
				
				if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\tSocket is closed.");}
			}catch(IOException e){
				AP_Settings.getLogg().writeToLogg("Input/Output error ("+e+")","SocketThread","run()");
			}
		}
		/**
		 * Method that handles all communications when a GET-request is received.
		 * It is assumed that the request is from an CC.
		 * 
		 * @param firstLineArray 	The firstLine in the GET-request header divided on space.
		 * @param current			The socket that the CC (the sender of the GET-request) is connected to.
		 * @param currentIn			The sockets inputstream (BufferedReader)
		 * @param currentOut		The sockets outputstream (PrintStream)
		 */
		public void getRequest(String firstLineArray[], Socket current, BufferedReader currentIn, PrintStream currentOut){
			// Declares necesary variables 
			Socket cmsSocket;			// A socket to the CMS
			//BufferedReader cmsIn;		// A inputstream to the CMS socket
			DataInputStream cmsIn; 
			PrintStream cmsOut;			// A outputstream to the CMS socket
			
			try {
				// Opens a socket to the CMS
				cmsSocket = new Socket(AP_Settings.getCmsAdr(), 80);
			} catch (IOException e) {
				AP_Settings.getLogg().writeToLogg("Failed to open a socket to CMS. ("+e+")","SocketThread","getRequest()");
				return;
			}
			try{
				cmsIn = new DataInputStream(cmsSocket.getInputStream());//new BufferedReader(new InputStreamReader(cmsSocket.getInputStream()));
				cmsOut = new PrintStream(cmsSocket.getOutputStream());
			}catch(IOException e) {
				AP_Settings.getLogg().writeToLogg("Failed to get Input|Output stream from socket. ("+e+")","SocketThread","getRequest()");
				return;
			}
			
			// Declarers variables for reading the HTTP-package body
			int contentLength = 0; 
			
			// Appends the ap_id on the firstline in the GET-request
			if(firstLineArray[1].contains("?") ){
				// Case 1, if there already exist get-variables 
				firstLineArray[1] = firstLineArray[1]+"&ap_id="+AP_Settings.getApID();
			}else{
				// Case 2, if it dosen't exist any get-variables
				firstLineArray[1] = firstLineArray[1]+"?ap_id="+AP_Settings.getApID();
			}
			String firstLine = "";
			// Reassembles the first line of the GET-request
			for(int i = 0; i < firstLineArray.length; i++){
				firstLine += firstLineArray[i]+" ";
			}
			if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t------- Recieved from CC: -------");}
			//if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+firstLine);}
			StringBuilder sb = new StringBuilder();
			sb.append(firstLine  + "\r\n");
			//cmsOut.println(firstLine);
			
			try{
				// Reads the rest of the GET-request from the sending CC and 
				// simultaneously send the request to the CMS
				String line = currentIn.readLine();
				while(line!= null){
					if(line.equals("")){
						break;
					}
					if(line.startsWith("Connection")){
						line = "Connection: Close";
					}
					if(!line.startsWith("Accept-Encoding")){
						sb.append(line + "\r\n");
					}
					//cmsOut.println(line);
					//if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+line);}
					
					line=currentIn.readLine();
					
				}
				cmsOut.println(sb);
				if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+sb);}
				cmsSocket.shutdownOutput();
				
				// Waiting for answer from the CMS
				if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t------- Answer from CMS: -------"); }
				
				// Reads the answer from the CMS and 
				// simultaneously send the request to the CC
				// Reading HTTP-headings 
				sb = new StringBuilder();
				line = cmsIn.readLine();
				
				while(line!= null){
					//currentOut.println(line);
					if(line.equals("")){
						break; 
					}
					sb.append(line + "\r\n");
					// If we read a blank row the header section is finished. 
					
					//if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+line);}
					// Checks after the content-length header which is needed to determine
					// how big the body is. That is when to stop reading from the stream. 
					if(line.startsWith("Content-Length")){
						String[] contentArray = line.split(" ");
						contentLength = Integer.parseInt(contentArray[1]);
					}
					line = cmsIn.readLine();
					
				}
				currentOut.println(sb);
				if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+sb);}
				
				// Reading HTTP-body
				// Reads the appropriate amount of chars/bytes specified in the content-length header.
				//DataInputStream dis = new DataInputStream(cmsSocket.getInputStream());
				
				DataOutputStream dos = new DataOutputStream(current.getOutputStream());
				byte[] body = new byte[contentLength];
				cmsIn.read(body);
				//dis.readFully(body);
				if(AP_Settings.DEBUG_AP_COMHANDLER){System.err.println(new String(body) );}
				dos.write(body);
				
				//currentOut.print(new String(body));
				
				//current.shutdownOutput();
				
			}catch(IOException e){
				AP_Settings.getLogg().writeToLogg("Failed while dealing with Input|Output stream from socket. ("+e+")","SocketThread","getRequest()");
				return;
			}
		}
		
		/**
		 * This method handles the case when the AP recieves a HTTP-package from
		 * the CMS that should be forwarded to the correct CC. 
		 * 
		 * @param firstLineArray 	The firstLine in the HTTP-package header divided on space.
		 * @param current			The socket that the CC (the sender of the GET-request) is connected to.
		 * @param currentIn			The socktes inputstream (BufferedReader)
		 * @param currentOut		The sockets outputstream (PrintStream)
		 */
		public void receivedHttpPackage(String firstLineArray[], Socket current, BufferedReader currentIn, PrintStream currentOut){
			//Declaring necessary variables
			String firstLine ="";
			String ccDestination = null; 
			ComChannel comChan = null; 
			int contentLength =0; 
			
			// Reassembles the first line of the HTTP-package
			for(String temp : firstLineArray){
				firstLine = firstLine + temp + " "; 
			}
			if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t------- Recieved from CMS: -------");}
			if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+firstLine);}
			/*
			 * Everything read from the inputstream is appended to a StringBuilder
			 * This due to that we don't know where to send to package until we have
			 * read the destination header. 
			 */
			StringBuilder sb = new StringBuilder(); 
			sb.append(firstLine);
			try{
				String line = currentIn.readLine();
				while(line != null){
					if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+line);}
					sb.append(line+"\n");
					//Checks after the destination header
					if(line.startsWith("Destination:", 0)){
						String[] destinationArray = line.split(" ");
						ccDestination = destinationArray[1];
					}
					// If we read a blank row the header section is finished. 
					if(line.equals("")){
						break; 
					}
					// Checks after the content-length header which is needed to determine
					// how big the body is. That is when to stop reading from the stream. 
					if(AP_Settings.DEBUG_AP_COMHANDLER){ System.err.println("\t"+line);}
					if(line.startsWith("Content-length")){
						String[] contentArray = line.split(" ");
						contentLength = Integer.parseInt(contentArray[1]);
					}
					line = currentIn.readLine();
				}
				// Reading HTTP-body
				// Reads the appropriate amount of chars/bytes specified in the content-length header.
				char[] body = new char[contentLength];
				currentIn.read(body);
				System.err.println("Body: " + new String(body) +" length: " +  body.length);
				currentOut.print(new String(body));
				
			}catch(IOException e){
				AP_Settings.getLogg().writeToLogg("Failed while dealing with Input stream from socket. ("+e+")",
						"SocketThread","receivedHttpPackage()");
				return;
			}
			
						// For-each loop to get the CC corresponding to the CC-id specified in the destination header. 
			for(ComChannel temp : AP_Settings.getComchans()){
				if(temp.id.equals(ccDestination)){
					comChan = temp; 
				}
			}
			// If we don't find a CC with an id that equals to ccDestination the program
			// we write the error to the logg and FIXME
			if(comChan == null){
				AP_Settings.getLogg().writeToLogg("Requested ComChannel not found ("+ccDestination+")", "SocketThread", "receivedHttpPackage()");
				//TODO vad gr vi om vi inte hittar destinationen, vi kan inte fortstta d... Avsluta p ngot stt
			}
			else{
				// The address is set to null which equals loopback address. 
				Socket ccSocket;
				try{
					//Opens a socket to the destionation CC
					ccSocket = new Socket((String) null, comChan.port);
				}catch(IOException e){
					AP_Settings.getLogg().writeToLogg("Failed to open socket to reciving ComChannel ("+e+")", "SocketThread", "receivedHttpPackage()");
					return;
				}
				try{
					//Opens an outpustream to the destination CC
					PrintStream ccOut = new PrintStream(ccSocket.getOutputStream());
					
					//Sends the package to the CC
					ccOut.print(sb);
					ccSocket.shutdownOutput();
				}catch(IOException e){
					AP_Settings.getLogg().writeToLogg("Failed while dealing with OutPut stream from socket. ("+e+")", "SocketThread", "receivedHttpPackage()");
				}
			}
		}
	}
}
