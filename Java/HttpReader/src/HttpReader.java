import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpReader {
	public static void main(String[] args) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(80);
		
			boolean run = true;
			while(run) {
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String str;
				while((str = br.readLine()) != null) {
					System.out.println(str);	
				}
				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
