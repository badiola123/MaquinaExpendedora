/**
 * @file LocalizadorXBee.java
 * @author Edgar Azpiazu
 * @brief File that locates the port at which the XBee module is connected
 */

package comunicacion;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

import gnu.io.CommPortIdentifier;

public class LocalizadorXBee {
	
	private final static Logger LOGGER = Logger.getLogger(LocalizadorXBee.class.getName());
	final static int BAUD_RATE = 9600;
	Enumeration<?> puertos;
	String puertoConectado;
	
	/**
	 * Contructor of the class
	 */
	public LocalizadorXBee() {
		puertos = CommPortIdentifier.getPortIdentifiers();  
		puertoConectado = null;
	}
	
	/**
	 * Searches for the por where the XBee is connected
	 * @return The name of the port
	 */
	public String getPuertoConectado() {
		buscarPuertoConectado();
		return puertoConectado;
	}

	/**
	 * Tries to open a XBee for each available port and stores the port it opens in a global variable
	 */
	private void buscarPuertoConectado() { 
		XBeeDevice xBee;
	    
		while(puertos.hasMoreElements()) {
				String puerto = ((CommPortIdentifier)puertos.nextElement()).getName();
		        
		        try {
		        	xBee = new XBeeDevice(puerto, BAUD_RATE);
		            	
		        	try {
		        		xBee.open();
		        		xBee.close();
		        		puertoConectado = puerto;
					} catch (XBeeException e) {
						LOGGER.log(Level.ALL, e.getMessage()); 
					}
				
		        } catch (NullPointerException e) {
		        	LOGGER.log(Level.ALL, e.getMessage());
		    	}
		}      
	}  
}