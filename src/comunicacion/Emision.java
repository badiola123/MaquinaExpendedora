/**
 * @file Emision.java
 * @author Edgar Azpiazu
 * @brief File that creates the diferent frames which are sent
 */

package comunicacion;

public class Emision {
	static final int DATOS_ACEPTADOS = 97; // 1 trama
	static final int DATOS_RECHAZADOS = 98; // 1 trama
	static final int NUEVO_USUARIO = 99; // 1 tramas
	static final int NUEVO_USUARIO_CANCELADO = 100; // 1 tramas
	static final int VENTA_ACEPTADA = 103; // 1 trama
	
	/**
	 * Create the data accepted frame
	 * @return The int number asigned to data accepted
	 */
	public int crearTramaDatosAceptados(){
		return DATOS_ACEPTADOS;
	}
	
	/**
	 * Create the data rejected frame
	 * @return The int number asigned to data rejected
	 */
	public int crearTramaDatosRechazados(){
		return DATOS_RECHAZADOS;
	}
	
	/**
	 * Create the new user frame
	 * @return The int number asigned to new user
	 */
	public int crearTramaNuevoUsuario(){
		return NUEVO_USUARIO;
	}
	
	/**
	 * Create the new user cancelled frame
	 * @return The int number asigned to new user cancelled
	 */
	public int crearTramaNuevoUsuarioCancelado(){
		return NUEVO_USUARIO_CANCELADO;
	}
	
	/**
	 * Create the sale accepted frame
	 * @return The int number asigned to sale accepted
	 */
	public int crearTramaVentaAceptada(){
		return VENTA_ACEPTADA;
	}
}
