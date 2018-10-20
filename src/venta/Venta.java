/**
 * @file Venta.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains sales' variables and methods to have access to them
 */
package venta;

public class Venta {
	private static final String[] nombreColumnas = {"usuario_id", "maquina_id", "producto_id", "precio_venta", "fecha_venta"};
	private static final String[] opcionesVenta = {"ID usuario", "ID maquina", "ID producto", "precio", "fecha venta"};
	private static final boolean[] formatoColumnas = {true, false, false, false, true}; //true = comillas ; false = sin comillas
	private static final String DB_AND = " and ";
	
	String idUsuario;
	int idMaquina;
	int idProducto;
	double precio;
	String fecha;
	/**
	 * Constructor of the class Venta, which creates the object of the sale with all the needed information
	 * @param idUsuario ID of user
	 * @param idMaquina ID of machine
	 * @param idProducto ID of prodcut
	 * @param precio price of the product
	 * @param fecha date
	 */
	 public Venta(String idUsuario, int idMaquina, int idProducto, double precio, String fecha){
		
		this.idUsuario = idUsuario;
		this.idMaquina = idMaquina;
		this.idProducto = idProducto;
		this.precio = precio;
		this.fecha = fecha;
		
	}
	/**
	 * Returns the type of the variables of the object in reference to an index
	 *
	 * @param indice Index to identify variable
	 * @return Class of the variable
	 */
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 0: return String.class;
		case 3: return Double.class;
		case 4: return String.class;
		default: return Integer.class; 
		}
		
	}
	/**
	 * Method to get object of the variable of a sale in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return idUsuario;
		case 1: return Integer.valueOf(idMaquina);
		case 2: return Integer.valueOf(idProducto);
		case 4: return Double.valueOf(precio);
		case 3: return fecha;
		default: return null; 
		}	
	}
	/**
	 * Getter of the ID of the user
	 *
	 * @return ID as a string
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	/**
	 * Getter of the ID of the machine
	 *
	 * @return ID as a string
	 */
	public int getIdMaquina() {
		return idMaquina;
	}
	/**
	 * Getter of the ID of the product
	 *
	 * @return ID as a string
	 */
	public int getIdProducto() {
		return idProducto;
	}
	/**
	 * Getter of the price of the product
	 *
	 * @return price as a double
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * Getter of all the information of the sale
	 *
	 * @return Array of strings with all the information
	 */

	public String[] getDatos(){
		return new String[] {this.idUsuario, String.valueOf(this.idMaquina), String.valueOf(this.idProducto), String.valueOf(this.precio), fecha};
	}
	/**
	 * Getter of the constant of all the sale information fields for database queries
	 *
	 * @return Fields of information as array of strings
	 */
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}
	/**
	 * Getter of the constant of the format of columns for database queries
	 *
	 * @return Columns as array of strings
	 */
	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}
	/**
	 * Getter of the constant of sale options for database queries
	 *
	 * @return Array of strings of sale options
	 */
	public static String[] getOpcionesVenta() {
		return opcionesVenta;
	}
	/**
	 * Method to get primary key in the database
	 *
	 * @return String of primary key
	 */
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.idUsuario + "'" : this.idUsuario) + DB_AND 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.idMaquina + "'" : this.idMaquina) + DB_AND 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.idProducto + "'" : this.idProducto) + DB_AND 
				+ nombreColumnas[4] + " = " + (formatoColumnas[4] ? "'" + this.fecha + "'" : this.fecha);
	}
	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID usuario: " + this.idUsuario + ", ID maquina: " + this.idMaquina + ", ID producto: " + this.idProducto + ", precio: " + this.precio + ", fecha reposición: " + this.fecha;
	}
	
	
}
