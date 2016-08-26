import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class BT_ComHandler  implements Runnable{
	private ServerSocket ss;
	private static final boolean DEBUG = true; 
	private static final HashMap<String, String> serviceFound = new HashMap<String, String>();
	
	public BT_ComHandler(){
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BT_ComHandler BTC = new BT_ComHandler();
		BTC.run();
	}

	public void run() {
		try {
			startServerSocket();
		} catch (IOException e) {
			//TODO more specific, perhaps move exception handling into submethods.
			System.err.println("IOexception caught!");
		}
	}
	
	private void startServerSocket() throws IOException{
		ss = new ServerSocket(BT_init.getPort());
		
		while(true){
			if(DEBUG){ System.err.println("Waiting for connection...");}
			Socket current = ss.accept();
			
			if(DEBUG){ System.err.println("Found connection!");}

			if(DEBUG){ System.err.println("Creating new thread! "); }
			(new Thread(new SocketThread(current))).start(); //
			
		}
	}
	
	private class SocketThread implements Runnable{
		private Socket socket;
		
		public SocketThread(Socket socket){
			this.socket = socket;
		}

		public void run() {
			try {
				InputStream is = socket.getInputStream();
		        DataInputStream dis = new DataInputStream(is);
		        String str, type="", filename="";
		        String regex = "[:/;= ]";
		        String mobileName = "";
		        int size = 0;
		        //Read header, get Content-Type and Content-Length
		        while ((str = dis.readLine()) != null) {
		        	String[] tmp = null;
		        	if(str.toLowerCase().startsWith("content-type:")) {
		            	tmp = str.split(regex);
		            	if(DEBUG){
		            		System.out.println(tmp[2]);
		            		System.out.println(tmp[3]);
		            	}
		            	if(tmp[2].toLowerCase().equals("image")) {
		            		type = "image/jpeg";
		            		filename = "image.jpg";
		            	}else if(tmp[2].toLowerCase().equals("video")){
		            		type = "video/mpeg";
		            		filename = "video.mpeg";
		            	}else{
		            		type = "text/plain";
		            		filename = "list.txt";
		            	}
		            }
		            if(str.toLowerCase().startsWith("content-length:")) {
		            	tmp = str.split(regex);
		            	
		            	if(DEBUG)System.out.println(Integer.parseInt(tmp[2]));
		            	
		            	size = Integer.parseInt(tmp[2]);
		            }
		            if(str.toLowerCase().startsWith("additional-data:")) {
		            	tmp = str.split(regex);
		            	mobileName = tmp[2];
		            }
		            System.err.println(str);
		            if(str.equals("")){
		            	break;
		            }
		        }
		        //Read Body save as a byte[].
		        byte[] data = new byte[size];
		        //read BODY into byte[].
		        dis.readFully(data);

		        dis.close();
		        
		        //Send the file to the mobile phone.
		        BlueToothAPI BTAPI = new BlueToothAPI(mobileName, filename, data, type, serviceFound);
		    } catch (IOException e) {
		    }
		}
	}
}
