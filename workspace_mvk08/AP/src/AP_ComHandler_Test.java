import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

/***
 * En klass fr att testa AP_ComHandler 
 * 
 * Klassen simulerar en CC, den skickar ett 
 * 
 * @author Tommy
 * @version 2009-04-09
 */

public class AP_ComHandler_Test  {

	private Socket s;
	private PrintStream out;
	private BufferedReader in;
	
	public static void main(String[] args){
		AP_ComHandler_Test test = new AP_ComHandler_Test();
	}
	
	public AP_ComHandler_Test(){
		try {
			System.err.println("Ansluter");
			s = new Socket((String) null, 80);
			s.setKeepAlive(true);
			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.err.println("isBound: "+ s.isBound());
			System.err.println("isBound: "+ s.isConnected());
			out.print("GET / HTTP/1.1\nUser-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.0; sv-SE; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8 (.NET CLR 3.5.30729)\nHost: www.google.com\nAccept: */*\n");
			
			System.err.println("Skrev fljande till socketen:");
			System.err.println("GET /?just=crap HTTP/1.1\nUser-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.0; sv-SE; rv:1.9.0.8) Gecko/2009032609 Firefox/3.0.8 (.NET CLR 3.5.30729)\nHost: www.google.com\nAccept: */*\n");
			
			s.shutdownOutput();
			//Lser svar
			System.err.println("Vntar p svar");
			String line = in.readLine();
			while(line!=null){
				System.err.println(line);
				line = in.readLine();
			}
			ServerSocket ss = new ServerSocket(86);
			
			while(true){
				//TODO fixa klart test exemplet
				Socket current = ss.accept();
				BufferedReader currentIn = new BufferedReader(new InputStreamReader(current.getInputStream()));
				line = currentIn.readLine();
				while(line != null){
					System.err.println(line);
					line = currentIn.readLine();
				}
			}
			
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		} 
	}
	
}
//GET / HTTP/1.1
//?just=crap