import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class logs all errors to a logg.txt file. It also has 
 * the possibility to print all errors to system.out.
 * @author Tommy
 * @version 2009-04-28
 */
public class AP_Logger {
	private File logFile; 
	private FileWriter logFileWriter;
	private DateFormat dateFormat; 
	private Date date; 
	private boolean showLog; 
	private final String D_STR1 = "---------------AP_LOGGER-----------------\n";
	private final String D_STR2 = "--------------/AP_LOGGER-----------------\n";
	private String message;

	/**
	 * This class logs all errors to a logg.txt file. It also has 
	 * the possibility to print all errors to system.out.
	 */
	public AP_Logger(){
		message = "<No log entries this session>";
		showLog=true;
		try{
			logFile = new File("logg.txt");
			logFileWriter = new FileWriter(logFile, true);
			
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		catch(Exception e){
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Write an entry in the log file. If showLog is set it will also print
	 * this message to System.out.
	 * @param errorMessage The error message.
	 * @param className The name of the class where the error occurred.
	 * @param method The method of the class where the error occurred.
	 */
	public void writeToLogg(String errorMessage, String className, String method){
		date = new Date();
		message = dateFormat.format(date) + "\t" + className + " : " + method + "\n" + errorMessage +"\n";
		try{
			if(showLog){ System.out.print(D_STR1+message+"\n"+D_STR2);}
			logFileWriter.write(message);
			logFileWriter.flush();
		}
		catch(Exception e){
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * @return The latest entry in the log if there was any during this run.
	 */
	public String getLatestEntry(){
		return message;
	}
	
	/**
	 * @return True if the logger should print out all errors. False otherwise.
	 */
	public boolean getLogStatus(){
		return showLog;
	}
	
	/**
	 * Toggle the status of whether or not the Logger should print out all logs. 
	 */
	public void toggleLog(){
		showLog=!showLog;
	}
}
