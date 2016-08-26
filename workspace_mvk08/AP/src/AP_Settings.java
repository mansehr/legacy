import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Container for the variables that should be accessible from all
 * parts of the program.
 *  
 * @author hMattias
 */
public class AP_Settings {
	// Ap settings
	private static InetAddress cmsAdr;
	private static String apID;
	private static ArrayList<ComChannel> comchans;
	private static AP_Logger logg = new AP_Logger();
	
	// Debugging variables
	public static final boolean DEBUG_AP_CMS = true;
	public static final boolean DEBUG_AP_INIT = false;
	public static final boolean DEBUG_AP_COMHANDLER = true;
	
	// Variables related to threads
	private static boolean aliveCMS;
	private static boolean aliveComHandler;
	
	public AP_Settings(){
		aliveCMS=true;
		aliveComHandler=true;
	}
	
	public static boolean getAliveCMS(){
		return aliveCMS;
	}
	
	public static void setAliveCMS(boolean alive){
		aliveCMS = alive;
	}
	
	public static boolean getAliveComHandler(){
		return aliveComHandler;
	}
	
	public static void setAliveComHandler(boolean alive){
		aliveComHandler=alive;
	}
	/**
	 * @return the cmsAdr
	 */
	public static InetAddress getCmsAdr() {
		return cmsAdr;
	}

	/**
	 * @param cmsAdr the cmsAdr to set
	 */
	public static void setCmsAdr(InetAddress cmsAdr) {
		AP_Settings.cmsAdr = cmsAdr;
	}

	/**
	 * @return the apID
	 */
	public static String getApID() {
		return apID;
	}

	/**
	 * @param apID the apID to set
	 */
	public static void setApID(String apID) {
		AP_Settings.apID = apID;
	}

	/**
	 * @return the comchans
	 */
	public static ArrayList<ComChannel> getComchans() {
		return comchans;
	}

	/**
	 * @param comchans the comchans to set
	 */
	public static void setComchans(ArrayList<ComChannel> comchans) {
		AP_Settings.comchans = comchans;
	}

	/**
	 * @return the logg
	 */
	public static AP_Logger getLogg() {
		return logg;
	}
}
