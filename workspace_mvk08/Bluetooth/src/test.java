import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class test {
	private static Socket s;
	private static PrintStream out;
	private static BufferedReader in;
	private static final String mobileName = "Mattias";
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		sendImage();
		//sendText();
	}
	
	public static void sendImage() throws IOException{
		BufferedImage BI = readImage();
		// O P E N
		ByteArrayOutputStream baos = new ByteArrayOutputStream( 1000 );

		// W R I T E
		ImageIO.write( BI, "jpeg", baos );

		// C L O S E
		baos.flush();
		byte[] data = baos.toByteArray();
		String http = "HTTP/1.1 200 OK\r\n"+
					"Date: Tue, 28 Apr 2009 13:12:55 GMT\r\n"+
					"Content-Type: image/jpeg\r\n"+
					"Content-Length: "+data.length+"\r\n"+
					"Additional_data: "+mobileName+"\r\n";
		http+="\r\n";
		//http+= (new String(data));
		System.err.println("Ansluter");
		s = new Socket((String) null, 88);
		s.setKeepAlive(false);
		out = new PrintStream(s.getOutputStream());
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.err.println("isBound: "+ s.isBound());
		System.err.println("isBound: "+ s.isConnected());
		out.print(http);
		out.write(data);
		System.out.write(data);
        s.shutdownOutput();
		baos.close();
	}
	
	public static BufferedImage readImage(){
		Image image = null;
		BufferedImage bufferedImage = null;
	    try {
	        // Read from a file
	        File file = new File("images.jpeg");
	        image = ImageIO.read(file);
	        image = new ImageIcon(image).getImage();
	        bufferedImage = new BufferedImage ( image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_INT_BGR  );
	        bufferedImage.createGraphics().drawImage( image, 0, 0, null );
	        
	    } catch (IOException e) {
	    }
	    return bufferedImage; 
	}
	
	public static void sendText(){
		try {
	        // Create a URLConnection object for a URL
	        URL url = new URL("http://www.google.se:80");
	        URLConnection conn = url.openConnection();
	    
	        // List all the response headers from the server.
	        // Note: The first call to getHeaderFieldKey() will implicit send
	        // the HTTP request to the server.
	        
	        String http = "";
	        for (int i=0; ; i++) {
	            String headerName = conn.getHeaderFieldKey(i);
	            String headerValue = conn.getHeaderField(i);
	    
	            if (headerName == null && headerValue == null) {
	                // No more headers
	                break;
	            }
	            if (headerName == null) {
	                // The header value contains the server's HTTP version
	            }
	            http += headerName+": "+headerValue+"\n";
	            
	        }
	        String test = "Det funkar idioter\r\n";
	        byte[] data = test.getBytes();
	        int size = data.length;
	        http+=("Content-Length: "+size+"\r\n");
	        http+="\r\n";
	        http+=test;
	        System.err.println("size ="+size);
	        System.err.println("Ansluter");
			s = new Socket((String) null, 85);
			s.setKeepAlive(false);
			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.err.println("isBound: "+ s.isBound());
			System.err.println("isBound: "+ s.isConnected());
			out.print(http);
	        s.shutdownOutput();
	    } catch (Exception e) {
	    }
	}
}
