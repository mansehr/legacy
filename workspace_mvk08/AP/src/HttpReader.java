import java.io.IOException;
import java.io.InputStream;

/**
 * The class takes an InputStream from a socket and provides
 * buffered readLine and byte array-reads without applying a character
 * set (but readLine will apply a charset).
 * 
 * NOTE:
 * This is NOT a traditional stream reader in the sense that it does
 * not implement any conventional stream interfaces. The only provided
 * methods are for reading a line or reading into a byte array.
 * 
 * @author ghb 
 */
public class HttpReader {
	private InputStream stream;			// Contains the socket input stream
	private byte[] buffer;				// Buffer containing buffered bytes
	private int buff_size = 1024;		// Size of buffer (must be > 0)
	private int buff_pointer;			// Indicates position of last [unread] buffered bytes
	private int read_pointer;			// Indicates current reading position in buffer
	
	/**
	 * Create instance of HttpInputStream.
	 * 
	 * @param stream Stream to read from
	 */
	public HttpReader(InputStream stream) {
		this.stream = stream;
		buffer = new byte[buff_size];
	}
	
	public String readLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		// If EOF has been reached previously, try to buffer again
		if (buff_pointer == -1) {
			if(!lookAhead()) {		// Buffer anew; if no bytes were read
				return null;
			}
		}
		
		byte[] read_byte = new byte[1];
		char chr;
		boolean read_r = false;
		while (true) {		// Read until "\r\n", '\n', '\r' or EOF has been reached
			if (read_pointer == buff_pointer) {		// If all buffered bytes have been read
				if(!lookAhead()) {		// Buffer anew; if no bytes were read
					break;
				}
			}
			
			read_byte[0] = buffer[read_pointer];		// Read byte
			chr = (new String(read_byte)).toCharArray()[0];			// Convert byte to char will default charset
			read_pointer++;			// Increment read pointer
			if (chr == '\r') {
				read_r = true;
			} else if (chr == '\n') {
				// Do nothing
			} else {		// Append to string
				if (read_r) {		// If a '\r' was previously read and not followed by a '\n'
					sb.append('\r');		// Append '\r' to string before read char
					read_r = false;
				}
				
				sb.append(chr);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Read bytes into byte array. The method will try to fill the
	 * entire array; if there's no more data to read the remaining
	 * bytes in the array will be left intact.
	 * 
	 * @param b Byte array to read to
	 * @return Number of read bytes, or 0 if the destination
	 * 		   array is zero length, or -1 if EOF was hit
	 * @throws IOException See InputStream.read(byte[])
	 */
	public int read(byte[] b) throws IOException {
		int read_bytes = 0;
		
		// If the array to read to is of zero length, then there's nothing to read
		if (b.length == 0) {
			return 0;
		}
		
		// If EOF has been reached previously, try to buffer again
		if (buff_pointer == -1) {
			if(!lookAhead()) {		// Buffer anew; if no bytes were read
				return -1;
			}
		}
		
		for (int i = 0; i < b.length; i++) {
			if (read_pointer == buff_pointer) {		// If all buffered bytes have been read
				if(!lookAhead()) {		// Buffer anew; if no bytes were read
					break;
				}
			}
			
			b[i] = buffer[read_pointer];		// Read byte
			read_pointer++;			// Increment read pointer
			read_bytes++;			// Increment read bytes
		}
		
		return read_bytes;
	}
	
	/**
	 * Reads bytes from stream into the buffer.
	 * 
	 * WARNING:
	 * This method will reset the buffer pointer and will thus
	 * render any previously read bytes in the buffer invalid.
	 * Be sure to have exhausted the buffer completely before
	 * calling this method.
	 * 
	 * @return true if bytes have been read, otherwise false (when EOF has been reached)
	 * @throws IOException See InputStream.read(byte[])
	 */
	private boolean lookAhead() throws IOException {
		buff_pointer = stream.read(buffer) - 1;			// Read bytes
		read_pointer = 0;			// Reset read pointer
		if (buff_pointer >= 0) {	// Bytes have been read
			return true;
		} else {					// End of file must have been reached
			return false;
		}
	}
}
