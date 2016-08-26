import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

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
public class AP_Init {
	private static final String FILENAME = "../conf.xml";
	private static String ap_ID;
	private static InetAddress cmsAdr;
	private static ArrayList<ComChannel> ccArray;
	private static boolean sucessfulLoad;
	
	/**
	 * Upon creation of an AP_Init the config-file will be read.
	 * (readConfFile()-method will run).
	 * @throws ConfigFileException 
	 * @throws DOMException 
	 */
	public AP_Init(){
		ap_ID="";
		cmsAdr=null;
		ccArray=new ArrayList<ComChannel>();
		sucessfulLoad = true;
		
		readConfFile();
	}
	
	/**
	 * This method reads the config file and stores it in memory.
	 * For specifications of the config file see ADD chapter 5.
	 * @throws ConfigFileException 
	 * @throws DOMException 
	 */
	public static void readConfFile(){
		Document doc = parseFile(FILENAME);
		if(doc == null){ 
			System.exit(1);
		}
		Node root = doc.getDocumentElement();
		//Innitiate reading of file...
        getSettings(root);
        //... ID and address read...
		findCC(doc);
        //... all cc read.
	}
	
	/*
	 * This method will extract all cc from the config file.
	 */
    private static void findCC(Document doc){
		NodeList nodeLst = doc.getElementsByTagName("CC");
		for (int s = 0; s < nodeLst.getLength(); s++){
			Node currNode = nodeLst.item(s);
			if (currNode.getNodeType() == Node.ELEMENT_NODE ) {
				try{
					Element typeElmnt = (Element) currNode;
					// Read type
					NodeList typeNmElmntLst = typeElmnt.getElementsByTagName("Type");
					Element typeNmElmnt = (Element) typeNmElmntLst.item(0);
				    NodeList typeNm = typeNmElmnt.getChildNodes();
				    String type = ((Node) typeNm.item(0)).getNodeValue();
				    if (AP_Settings.DEBUG_AP_INIT) { System.err.println("\t          TYPE: "+type); }
				    // Read ID
				    NodeList idNmElmntLst = typeElmnt.getElementsByTagName("ID");
				    Element idNmElmnt = (Element) idNmElmntLst.item(0);
				    NodeList idNm = idNmElmnt.getChildNodes();
				    String id = ((Node) idNm.item(0)).getNodeValue();
				    if (AP_Settings.DEBUG_AP_INIT) { System.err.println("\t          ID: "+id); }
				    // Read status
				    NodeList statusNmElmntLst = typeElmnt.getElementsByTagName("Status");
				    Element statusNmElmnt = (Element) statusNmElmntLst.item(0);
				    NodeList statusNm = statusNmElmnt.getChildNodes();
				    int status = convertStatusToInt((((Node) statusNm.item(0)).getNodeValue()));
				    if (AP_Settings.DEBUG_AP_INIT) { System.err.println("\t          STATUS: "+status); }
				    // Read port
				    NodeList portNmElmntLst = typeElmnt.getElementsByTagName("Port");
				    Element portNmElmnt = (Element) portNmElmntLst.item(0);
				    NodeList portNm = portNmElmnt.getChildNodes();
				    int port = Integer.valueOf(((Node) portNm.item(0)).getNodeValue());
				    if (AP_Settings.DEBUG_AP_INIT) { System.err.println("\t          port: "+port); }
				    
				    if (AP_Settings.DEBUG_AP_INIT) { System.err.println("\tNoden '"+id+"' av typen '"+type+"', port '"+port+"' tillagd. Status: '"+status+"'."); }
				    ccArray.add(new ComChannel(type, id, port, status));
				}catch(NullPointerException npe){
					AP_Settings.getLogg().writeToLogg("Configurationfile contains invalid communicationchannel(s). A cc must have Type, ID, Status and Port.",
							"AP_Init", "findCC()");
					sucessfulLoad=false;
					return;
				}catch(NumberFormatException nfe){
					AP_Settings.getLogg().writeToLogg("Configurationfile contains invalid communicationchannel(s). 'Port' must contain a number.",
							"AP_Init", "findCC()");
					sucessfulLoad=false;
					return;
				}
			    
			}
		}
	}

    /*
     * This method converts the possible statuses of a cc
     * to the right integer according to this list:
     * 0 - inactive
     * 1 - online
     * 2 - not_responding
     * 3 - config_error
     */
	private static int convertStatusToInt(String status){
		if(status.toLowerCase().equals("online")){
			return 1;
		}else if(status.toLowerCase().equals("inactive")){
			return 0;
		}else if(status.toLowerCase().equals("not_responding")){
			return 2;
		}else if(status.toLowerCase().equals("config_error")){
			return 3;
		}
		AP_Settings.getLogg().writeToLogg("Invalid status in configfile: "+ status+"Should be (online || inactive || not_responding || config_error).",
				"AP_Init", "convertStatusToInt()");
		sucessfulLoad=false;
		return -1;
	}

	/*
	 * This method will extract the AP ID and the CMS address. 
	 */
	private static void getSettings(Node node){
        String nodeName = node.getNodeName();
        String nodeValue = getElementValue(node);
        if(AP_Settings.DEBUG_AP_INIT) { System.err.println("\t##getSettings | NodeName: " + nodeName + ", NodeValue: " + nodeValue); }
        if(nodeName.equals("AP_ID")){
        	if(AP_Settings.DEBUG_AP_INIT) { System.err.println("\t              | Saving 'AP_ID' as "  + nodeValue); }
			ap_ID=nodeValue;
			if(AP_Settings.DEBUG_AP_INIT) { System.err.println("\t              | Validating 'AP_ID' as "  + ap_ID); }
		}else if(nodeName.equals("AP_CMSadr")){
			if(AP_Settings.DEBUG_AP_INIT) { System.err.println("\t              | Saving 'AP_CMSadr' as "  + nodeValue); }
		    cmsAdr = makeInetAddress(nodeValue);
		    if(AP_Settings.DEBUG_AP_INIT) { System.err.println("\t              | Saving 'AP_CMSadr' as "  + cmsAdr); }
		}
        
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                getSettings(child);
            }
        }
    }
    
	/*
	 * Convert a string to an InetAddress
	 */
	private static InetAddress makeInetAddress(String nodeValue){
		InetAddress address = null;
		try {
			address = InetAddress.getByName(nodeValue);
		} catch (UnknownHostException e) {
			AP_Settings.getLogg().writeToLogg("Invalid InetAddress in configfile: "+nodeValue+
					" (Should be X.X.X.X where X is any number between 0 and 255).",
					"AP_Init", "makeInetAddress()");
			sucessfulLoad=false;
		}
		return address;
	}

	/*
	 * Get the element value as a String of a node. 
	 * 
	 */
	private static String getElementValue(Node elem){
		Node kid;
		if ( elem != null){
			if(elem.hasChildNodes()){
				for(kid = elem.getFirstChild(); kid != null; 
								kid = kid.getNextSibling() ) {
					if(kid.getNodeType() == Node.TEXT_NODE){
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
	/*
	 * Make a Document from the configuration file.
	 */
	private static Document parseFile(String file){
		DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
        	AP_Settings.getLogg().writeToLogg("Wrong parser configuration: " + e.getMessage(),
        			"AP_Init", "parseFile()");
        	sucessfulLoad=false;
            return null;
        }
        try {
            doc = docBuilder.parse(new File(file));
        }
        catch (SAXException e) {
        	AP_Settings.getLogg().writeToLogg("Wrong XML file structure: " + e.getMessage(),
        			"AP_Init", "parseFile()");
        	sucessfulLoad=false;
            return null;
        }
        catch (IOException e) {
        	AP_Settings.getLogg().writeToLogg("Could not read source file: " + e.getMessage(),
        			"AP_Init", "parseFile()");
        	sucessfulLoad=false;
        	return null;
        }
        return doc;
	}
	
	
	/**
	 * Get the AP_ID from the configuration.
	 * @return the AP_ID.
	 */
	public String getApId(){
		return ap_ID;
	}
	
	/**
	 * Get the InetAddress from the configuration.
	 * @return the CMS_Address
	 */
	public InetAddress getCmsAddress(){
		return cmsAdr;
	}
	
	/**
	 * Get the list of cc from the configuration.
	 * @return
	 */
	public ArrayList<ComChannel> getCCArray(){
		return ccArray;
	}
	
	/**
	 * This method will indicate whether the initiation of all variables
	 * was sucessful. 
	 * @return true if the load of  OK. False otherwise.
	 */
	public boolean sucessfulLoad(){
		return sucessfulLoad;
	}
}
