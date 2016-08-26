import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Calendar;


public class CheckHost implements Runnable{

	String host;
	
	public CheckHost(String host) {
		// TODO Auto-generated constructor stub
		this.host = host;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Enter host you want to http-test: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		String host = null;
		try {
			host = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(host != null) {
			CheckHost ch = new CheckHost(host);
			ch.run();
		}
	}

	@Override
	public void run() {
		System.out.println("Testing connection on host "+host);
		Socket s = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(host+".log", false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true ) {
			String message = "";
			try {
				s = new Socket(host, 80);
				BufferedWriter br = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				br.write("GET / HTTP1.1\r\n");
				br.write("Host: "+host+"\r\n");
				br.write("\r\n");
				
				message = Calendar.getInstance().getTime()+" Connection correct";
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				message = Calendar.getInstance().getTime()+" Failed connection. Exception: "+e.getMessage();
			}
			finally {
				try {
					System.out.println(message);
					if(fw != null) {
						fw.write(message);
					}
					if(s != null)
						s.close();
					
					for(int i = 50; i > 0; i--) {
						System.out.print("\rTime left to next test: "+i+"s ");
						Thread.sleep(1000);
					}
					System.out.print("\r");
					
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}

}
