import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * En klass för att testa AP_ComHandler
 * 
 * Denna klass simulerar CMS:n och sitter lyssnar på en port för inkommande http-paket
 * 
 * @author Tommy
 * @version 2009-04-09
 */

public class AP_ComHandler_TestCMS {

	private ServerSocket server; 
	private BufferedReader in; 
	private PrintStream out; 
	
	public static void main(String[] args){
		AP_ComHandler_TestCMS cms = new AP_ComHandler_TestCMS();
	}
	
	public AP_ComHandler_TestCMS(){
		try {
			server = new ServerSocket(8080);
			
			while(true){
				System.err.println("---------------Väntar på anslutning-------------");
				Socket current = server.accept();
				System.err.println("Hittade anslutning");

				in = new BufferedReader(new InputStreamReader(current.getInputStream()));
				//Läs in vad som AP skickar
				System.err.println("Läser");
				String line = in.readLine();
				while(line != null){
					System.err.println(line);
					line = in.readLine();
				}
				System.err.println("Läst klart");
				current.shutdownInput();
				//Bearbeta datan
				System.err.println("Väntar 5 sekunder...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("...väntat 5 sekunder");
				//Svara AP:n
				out = new PrintStream(current.getOutputStream());
				System.err.println("Skickar tillbaka svar");
				String body = "0123456789";
				String svar ="HTTP/1.1 200 OK\nCache-Control: private, max-age=0\nDate: Thu, 09 Apr 2009 12:25:43 GMT\nExpires: -1\nContent-Type: text/html; charset=UTF-8\nSet-Cookie: PREF=ID=5729da8713176457:TM=1239279943:LM=1239279943:S=MyHFaMfU2VTJURg0; expires=Sat, 09-Apr-2011 12:25:43 GMT; path=/; domain=.google.se\nServer: gws\nTransfer-Encoding: chunked\nContent-length: "+(body.getBytes().length)+"\n\n" + body; 
				System.err.println(svar);
				out.print(svar);
				System.err.println("Svarat!");
				current.shutdownOutput();
				System.err.println("Förfrågan färdig");
				
				//Testar att AP:n kan ta emot paket från CMS:n och skicka dem rätt!
				System.err.println("Skickar svar från CC x till CC y");
				Socket AP = new Socket(InetAddress.getByName("127.0.0.1"), 80);
				PrintStream apOut = new PrintStream(AP.getOutputStream());
				apOut.println("HTTP/1.1 200 OK");
				apOut.println("Cache-Control: private, max-age=0");
				apOut.println("Date: Tue, 14 Apr 2009 11:39:46 GMT");
				apOut.println("Expires: -1");
				apOut.println("Content-Type: text/html; charset=UTF-8");
				apOut.println("Server: gws");
				apOut.println("Destination: IB1");
				
				AP.shutdownOutput();
				System.err.println("Färdigskickat");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
