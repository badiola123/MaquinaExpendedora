/**
 * @file Recepcion.java
 * @author Edgar Azpiazu
 * @brief File that receives the data sent from one XBee to another
 */

package comunicacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;

public class Recepcion extends Observable implements IDataReceiveListener, ActionListener {
	final static int PAQUETE_HELLO = 64;
	final static int ERROR_PRODUCTO = 101;
	final static int PRODUCTO_DETECTADO = 102;
	final static int MAQUINA_ID = 104;
	final static int PRODUCTO_ID = 105;
	final static int USUARIO_ID = 106;
	final static int BYTES_USUARIO = 10;
	private static final String IM_ERROR = "img/error.png";
	ModuloXBee moduloXBee;
	String maquinaID, husilloID, usuarioID;
	int numActualizacion = 0;
	int hello = 0;
	Timer timer= null;
	
	/**
	 * Constructor of the class which initializes the timer and sets the CBee module
	 * @param moduloXBee Used XBee module
	 */
	public Recepcion(ModuloXBee moduloXBee) {
		this.moduloXBee = moduloXBee;
		timer = new Timer (2000,this);
		timer.setActionCommand("timer");
		timer.start();
	}

	/**
	 * Overridden method to receive data
	 * @param data The received message
	 */
	@Override
	public void dataReceived(XBeeMessage data) {
		String tipoDato;
		int dato = (int) data.getData()[0];
		
		if (dato < 0) {
			dato += 256;
		}
		System.out.println(dato);
		
		switch (dato) {
		case PAQUETE_HELLO:
			tipoDato = "PAQUETE_HELLO";
			hello = 1;
			break;
		case ERROR_PRODUCTO:
			tipoDato = "ERROR_PRODUCTO";
			moduloXBee.mandarTramaVentaAceptada();
			hello = 2;
			JOptionPane.showMessageDialog(null, "El producto no ha sido entregado correctamente",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
			break;
		case PRODUCTO_DETECTADO:
			tipoDato = "PRODUCTO DETECTADO";
			moduloXBee.mandarTramaVentaAceptada();
			numActualizacion = 2;
			moduloXBee.confirmarVenta(maquinaID, husilloID, usuarioID);
			this.setChanged();
			this.notifyObservers(this);
			break;
		case MAQUINA_ID:
			tipoDato = "MAQUINA ID";
			leerDatos(data);
			break;
		case USUARIO_ID:
			tipoDato = "USUARIO ID";
			leerNuevoUsuario(data);
			break;
		default:
			tipoDato = "ningun dato";
			break;
			}
		System.out.println(tipoDato);
		}

	/**
	 * Reads the received data information and checks it to give an answer
	 * @param data The received message
	 */
	private void leerDatos(XBeeMessage data) {
		maquinaID = Character.toString((char) data.getData()[1]);
		if(data.getData()[2] == PRODUCTO_ID){
			husilloID = Character.toString((char) data.getData()[3]);
			}
		if(data.getData()[4] == USUARIO_ID){
			leerUsuario(5, data);
			}
		moduloXBee.comprobarDatos(maquinaID, husilloID, usuarioID);
		}

	/**
	 * Gets the received new user's ID and notifies the observers
	 * @param data The received message
	 */
	private void leerNuevoUsuario(XBeeMessage data) {
		leerUsuario(1, data);
		numActualizacion = 1;
		this.setChanged();
		this.notifyObservers(this);
	}
	
	/**
	 * Reads the received new user's ID from the received data
	 * @param offset The offset from which the function starts reading
	 * @param data The received message
	 */
	private void leerUsuario(int offset, XBeeMessage data) {
		usuarioID = "";
		
		for(int numDato = offset; numDato < BYTES_USUARIO + offset; numDato++){
			usuarioID += Character.toString((char) data.getData()[numDato]);
		}
	}

	/**
	 * Getter of the machine ID
	 * @return The ID of the machine involved
	 */
	public String getMaquinaID() {
		return maquinaID;
	}

	/**
	 * Getter of the spindle ID
	 * @return The ID of the spindle involved
	 */
	public String getHusilloID() {
		return husilloID;
	}

	/**
	 * Getter of the user ID
	 * @return The ID of the user involved
	 */
	public String getUsuarioID() {
		return usuarioID;
	}

	/**
	 * Getter of the actualization number which specifies the executed change
	 * @return The value of numActualizacion
	 */
	public int getNumActualizacion() {
		return numActualizacion;
	}

	/**
	 * Overridden method of the ActionListener
	 * @param e the identifier of the performed action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("timer")){
			if(hello == 1) hello = 0;
			else if(hello == 0){
				JOptionPane.showMessageDialog(null, "Hay un problema con la conexión",
						"Aviso",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
				hello = 2;
				}
			}
		}
}