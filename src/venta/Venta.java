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
	
	String id_usuario;
	int id_maquina;
	int id_producto;
	double precio;
	String fecha;
	/**
	 * Constructor of the class Venta, which creates the object of the sale with all the needed information
	 * @param id_usuario ID of user
	 * @param id_maquina ID of machine
	 * @param id_producto ID of prodcut
	 * @param precio price of the product
	 * @param fecha date
	 */
	 public Venta(String id_usuario, int id_maquina, int id_producto, double precio, String fecha){
		
		this.id_usuario = id_usuario;
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
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
		case 0: return id_usuario;
		case 1: return new Integer(id_maquina);
		case 2: return new Integer(id_producto);
		case 4: return new Double(precio);
		case 3: return fecha;
		default: return null; 
		}	
	}
	/**
	 * Getter of the ID of the user
	 *
	 * @return ID as a string
	 */
	public String getId_usuario() {
		return id_usuario;
	}
	/**
	 * Getter of the ID of the machine
	 *
	 * @return ID as a string
	 */
	public int getId_maquina() {
		return id_maquina;
	}
	/**
	 * Getter of the ID of the product
	 *
	 * @return ID as a string
	 */
	public int getId_producto() {
		return id_producto;
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
		String[] datos = {this.id_usuario, String.valueOf(this.id_maquina), String.valueOf(this.id_producto), String.valueOf(this.precio), fecha};
		
		return datos;
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
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_usuario + "'" : this.id_usuario) + " and " 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.id_producto + "'" : this.id_producto) + " and " 
				+ nombreColumnas[4] + " = " + (formatoColumnas[4] ? "'" + this.fecha + "'" : this.fecha);
	}
	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID usuario: " + this.id_usuario + ", ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", precio: " + this.precio + ", fecha reposición: " + this.fecha;
	}
	
	
}
