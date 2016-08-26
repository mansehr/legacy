import java.io.*;

/**
 * This program will load all needed settings and then start two
 * new threads; one for the AP_CMS handling communication to the CMS
 * and one for the AP_ComHandler handling communication between cc
 * and the CMS.
 * @author Mattias Hägglund
 */
public class AP_Main implements Runnable{
	private Thread chThread;
	private static AP_Init init;
	private Thread cmsThread;
	private AP_CMS cms;
	private AP_ComHandler ch;
	
	public void run() {
		// Start the functions
		startApFunctions(true, true);
		
		// User interface loop
		while(true){
			printMenu();
			readCommand();
		}
	}

	private void startApFunctions(boolean comhandlerB, boolean cmsB) {
		// Load settings
		ap_Init();

		// Start communication handler thread 
		if(comhandlerB){
			AP_Settings.setAliveComHandler(true);
			ch=new AP_ComHandler();
			chThread = (new Thread(ch));
			chThread.start();
		}
		
		// Start CMS handler thread
		if(cmsB){
			AP_Settings.setAliveCMS(true);
			cms = new AP_CMS();
			cmsThread = (new Thread(cms));
			cmsThread.start();
		}
	}
	
	private void menuResetComHandler() {
		System.out.println("Trying to restart the ComHandler process...");
		//Shutting down current threads
		try{
			AP_Settings.setAliveCMS(false);
			cms.interruptWait();
			cmsThread.join();
		}catch(InterruptedException ie){
			System.out.println("Failed to restart AP!");
			System.out.println(ie);
			return;
		}
		startApFunctions(false,true);
		System.out.println("...Restarted.");
	}

	private void menuResetCMS() {
		System.out.println("Trying to restart the process that updates to CMS...");
		//Shutting down current threads
		try{
			AP_Settings.setAliveCMS(false);
			cms.interruptWait();
			cmsThread.join();
		}catch(InterruptedException ie){
			System.out.println("Failed to restart AP!");
			System.out.println(ie);
			return;
		}
		startApFunctions(false,true);
		System.out.println("...Restarted.");
	}
	
	/*
	 * This method will wait for input and linebreak and then process the command.
	 */
	private void readCommand(){
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		try {
			menuCommand(read.readLine());
		} catch (IOException e) {
			System.err.println("An error occured while processing the input.");
		}
	}
	
	/*
	 * This method contains all valid menu commands. If an invalid command
	 * is used it will print out an error message to System.out
	 */
	private void menuCommand(String command){
		try{
			int com = Integer.parseInt(command);
			switch(com){
			case(0):menuExit();
			case(1):menuStatus();break;
			case(2):menuLatestLogEntry();break;
			case(3):toggleShowLog();break;
			case(4):menuResetCMS();break;
			case(5):System.out.println("Not implemented yet");break;
			case(6):System.out.println("Not implemented yet");break;
			default:System.out.println("The command must be a number between 0 and 6");
			}
		}catch(NumberFormatException nfe){
			System.out.println("The command must be a number between 0 and 6");
		}
	}

	/*
	 * This method prints the menu to System.out.
	 */
	private void printMenu() {
		System.out.println();
		System.out.println("+--------------------------+");
		System.out.println("|Choose your option:       |");
		System.out.println("| (1) Status               |");
		System.out.println("| (2) Latest log entry     |");
		System.out.println("| (3) Toggle active logging|");
		System.out.println("| (4) Restart CMSHandler   |");
		System.out.println("| (5) Restart ComHandler   |");
		System.out.println("| (6) Restart Both         |");
		System.out.println("| (0) Exit AP              |");
		System.out.println("+--------------------------+");
		System.out.print("> "); 
	}

	/*
	 * This method prints the latest log entry to System.out.
	 */
	private void menuLatestLogEntry(){
		System.out.println("Latest log entry:\n"+ AP_Settings.getLogg().getLatestEntry());
	}
	
	/*
	 * This method prints out the status of this AP to System.out
	 */
	private void menuStatus(){
		System.out.println("Status of this AP ("+AP_Settings.getApID()+"):");
		System.out.println("-Registred CMS address: "+AP_Settings.getCmsAdr());
		System.out.println("-Number of cc registered: "+AP_Settings.getComchans().size());
		for(ComChannel cc : AP_Settings.getComchans()){
			System.out.println("---"+cc.toString());
		}
		System.out.println("-Active threads: "+Thread.activeCount());
		System.out.println("-Errorlogging is active.");
		if(AP_Settings.getLogg().getLogStatus()){
			System.out.println("-Errorlogging is set to show log in consol.");
		}else{
			System.out.println("-Errorlogging is set to not show log in consol.");
		}
		System.out.println("-Latest log entry:\n"+ AP_Settings.getLogg().getLatestEntry());
	}
	
	/*
	 * This method toggles the "show log in console"-function and prints 
	 * its status to System.out.
	 */
	private void toggleShowLog(){
		AP_Settings.getLogg().toggleLog();
		if(AP_Settings.getLogg().getLogStatus()){
			System.out.println("Errorlogging is set to show log in consol.");
		}else{
			System.out.println("Errorlogging is set to not show log in consol.");
		}
	}
	
	/*
	 * This method shuts down the AP.
	 */
	private void menuExit(){
		System.out.println("Shutting down AP: "+AP_Settings.getApID());
		System.exit(0);
	}
	
	/*
	 * This method will load the settings using AP_Init.
	 * If there was an error in loading the settings the program
	 * will exit. All errors are logged in the error log.
	 */
	private void ap_Init() {
		init = new AP_Init();
		
		if(init.sucessfulLoad()){
			AP_Settings.setApID(init.getApId());
			AP_Settings.setCmsAdr(init.getCmsAddress());
			AP_Settings.setComchans(init.getCCArray());
		}else{
			AP_Settings.getLogg().writeToLogg("AP_Init failed to load settings from conf.xml. Quitting.",
					"AP_Main", "ap_Init()");
			System.exit(1);
		}
	}
	
	/**
	 * This method will load all needed settings and then start two
	 * new threads; one for the AP_CMS handling communication to the CMS
	 * and one for the AP_ComHandler handling communication between cc
	 * and the CMS.
	 * @param args no arguments needed.
	 */
	public static void main(String[] args) {
		AP_Main m = new AP_Main();
		Thread t = new Thread(m);
		t.start();
	}
}