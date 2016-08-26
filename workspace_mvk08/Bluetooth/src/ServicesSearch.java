import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.bluetooth.*;

/**
 *
 * Minimal Services Search example.
 */
public class ServicesSearch implements DiscoveryListener{
	public final static UUID OBEX_OBJECT_PUSH = new UUID(0x1105);
    static final UUID OBEX_FILE_TRANSFER = new UUID(0x1106);
    final Object serviceSearchCompletedEvent = new Object();
    //public static final Vector/*<String>*/ serviceFound = new Vector();
    public static final HashMap<String, String> serviceFound = new HashMap<String, String>();
    private String btdeviceName;
	static String mobileName;
    
    public ServicesSearch(String[] args) throws IOException, InterruptedException {
    	// First run RemoteDeviceDiscovery and use discoved device
        RemoteDeviceDiscovery.main(null);
        serviceFound.clear();
        UUID serviceUUID = OBEX_OBJECT_PUSH;
        if ((args != null) && (args.length > 0)) {
            serviceUUID = new UUID(args[0], false);
        }
        
        UUID[] searchUuidSet = new UUID[] { serviceUUID };
        int[] attrIDs =  new int[] {
                0x0100 // Service name
        };

        for(Enumeration en = RemoteDeviceDiscovery.devicesDiscovered.elements(); en.hasMoreElements(); ) {
            RemoteDevice btDevice = (RemoteDevice)en.nextElement();
            synchronized(serviceSearchCompletedEvent) {
                if(btDevice.getFriendlyName(false).toLowerCase().equals(mobileName.toLowerCase())){
                	System.out.println("search services on " + btDevice.getBluetoothAddress() + " " + btDevice.getFriendlyName(false));
                    btdeviceName = btDevice.getFriendlyName(false);
                	LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, btDevice, this);
                    serviceSearchCompletedEvent.wait();
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
    	mobileName = args[0];
    	ServicesSearch SS = new ServicesSearch(null);
    }
    
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
    }

    public void inquiryCompleted(int discType) {
    }

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        for (int i = 0; i < servRecord.length; i++) {
            String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            if (url == null) {
                continue;
            }
            serviceFound.put(btdeviceName.toLowerCase(), url);
            DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
            if (serviceName != null) {
                System.out.println("service " + serviceName.getValue() + " found " + url);
            } else {
                System.out.println("service found " + url);
            }
        }
    }

    public void serviceSearchCompleted(int transID, int respCode) {
        System.out.println("service search completed!");
        synchronized(serviceSearchCompletedEvent){
            serviceSearchCompletedEvent.notifyAll();
        }
    }

}
