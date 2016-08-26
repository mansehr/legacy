import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Based on XML-tutorial:
 * http://labe.felk.cvut.cz/~xfaigl/mep/xml/java-xml.htm
 * This class will load the contents of conf.xml and make the variables
 * available from get-methods. Before using these the methods sucessfulLoad()
 * should be used. If this is false there was an error while loading the
 * settings (error message will be available in the Error log). If it is true
 * the variables are OK. 
 * @author Mattias Hägglund
 */
public class BT_init {
	private static final String FILENAME = "conf.xml";
	private static boolean btPort;
	
	public static void main(String[] args) throws IOException {
		System.err.println(BT_init.getPort());
	}
	 
	public static int getPort(){
		File file = new File(FILENAME);
	   try {
		   DocumentBuilder builder =
			   DocumentBuilderFactory.newInstance().newDocumentBuilder();
		   Document doc = builder.parse(file);
	
		   NodeList nodes = doc.getElementsByTagName("AP_ListCC");
		   for (int i = 0; i < nodes.getLength(); i++) {
			   Element element = (Element) nodes.item(i);
	
		       NodeList child = element.getElementsByTagName("CC");
		       for (int j = 0; j < child.getLength(); j++) {
		    	   Element element2 = (Element) child.item(j);
		    	   NodeList type = element2.getElementsByTagName("Type");
		    	   for (int k = 0; k < type.getLength(); k++) {
		    		   Element line = (Element) type.item(k);
		    		   if(getCharacterDataFromElement(line).equals("bt")){
		    			   System.out.println("Type: " + getCharacterDataFromElement(line));
		    			   NodeList port = element2.getElementsByTagName("Port");
		    			   line = (Element) port.item(k);
		    			   System.out.println("Port: " + getCharacterDataFromElement(line));
		    			   return Integer.parseInt(getCharacterDataFromElement(line));
		    		   }
		    	   }
		       }
		    }
	   	}
	   	catch (Exception e) {
	      e.printStackTrace();
	   	}
	   	return -1;
	}
	
	public static String getCharacterDataFromElement(Element e) {
	   Node child = e.getFirstChild();
	   if (child instanceof CharacterData) {
	     CharacterData cd = (CharacterData) child;
	       return cd.getData();
	     }
	   return "?";
	}

	
}
