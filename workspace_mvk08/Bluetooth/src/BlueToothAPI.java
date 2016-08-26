import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.imageio.ImageIO;
import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;
import javax.swing.ImageIcon;

/**
 * 000FDE99BECA davhe se om det ändras???TODO
 * Fungera som en client och server. Behöver vara trådat.
 * Lyssna efter inkommande förfrågning som ska skickas vidare till transport delen.
 * kommer att få meddelande från transport delen som ska skickas ut till PC, Mobil.
 * 
 * @author David He
 *
 */
public class BlueToothAPI implements Runnable{
	final static Object searchEvent = new Object();
	private String filename;
	private byte[] data;
	private String mobileName;
	private String type;
	private HashMap<String, String> serviceFound;
	
	public BlueToothAPI(String mobileName, String filename, byte[] data, String type, HashMap<String, String> serviceFound){
		this.filename = filename;
		this.data = data;
		this.mobileName = mobileName;
		this.type = type;
		this.serviceFound = serviceFound;
		Thread t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		//BlueToothAPI.sendToDevice("000FDE99BECA:6", null, "text/plain");
    }
	
	public void run() {
		try {
			sendToDevice();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void sendToDevice() throws IOException{
		try {
			String[] tmp = new String[1];
			tmp[0] = mobileName;
			String url = "";
			if(!serviceFound.containsKey(mobileName)){
				
					ServicesSearch.main(tmp);
					url = ServicesSearch.serviceFound.get(mobileName.toLowerCase());
				synchronized(searchEvent) {
					this.serviceFound.put(mobileName, url);
					this.searchEvent.notifyAll();
				}
			}else{
				url = serviceFound.get(mobileName);
			}
			//System.err.println(url);
			sendFile(url);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendFile(String url){
		ClientSession clientSession;
		try {
			clientSession = (ClientSession) Connector.open(url);
			HeaderSet hsConnectReply = clientSession.connect(null);
	        if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK) {
	            System.out.println("Failed to connect");
	            return;
	        }
	        System.out.println("Sending file...");
	        HeaderSet hsOperation = clientSession.createHeaderSet();
	        hsOperation.setHeader(HeaderSet.NAME, filename);
	        hsOperation.setHeader(HeaderSet.TYPE, type);
        	System.out.println("Sending a "+type+" type...");
        	
	        hsOperation.setHeader(HeaderSet.LENGTH, new Long(data.length));
	        //Create PUT Operation
	        Operation putOperation = clientSession.put(hsOperation);

	        // Send some text to server
	        OutputStream os = putOperation.openOutputStream();
	        os.write(data);
	        os.close();

	        putOperation.close();

	        clientSession.disconnect(null);

	        clientSession.close();
	        
	        System.out.println("data been sent!");
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}