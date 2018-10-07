package comunicacion;

import java.util.Enumeration;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

import gnu.io.CommPortIdentifier;

public class LocalizadorXBee {
	
	final static int BAUD_RATE = 9600;
	Enumeration<?> puertos;
	String puertoConectado;
	
	public LocalizadorXBee() {
		puertos = CommPortIdentifier.getPortIdentifiers();  
		puertoConectado = null;
	}
	
	
	public String getPuertoConectado() {
		buscarPuertoConectado();
		return puertoConectado;
	}

	
	// Intenta abrir una xbee por cada puerto conectado. Si, no ha conseguido abrirla salta la xbee exception
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
							e.printStackTrace(); 
					}
				
		        } catch (NullPointerException e) {
		    			e.printStackTrace();
		    	}
		}      
	}  
}