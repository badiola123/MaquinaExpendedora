/**
 * @file Stock.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains stock's variables and methods to have access to them
 */

package stock;

public class Stock {
	private static final String[] nombreColumnas = {"maquina_id", "producto_id", "fecha_cambio", "total", "posicion"};
	private static final String[] opcionesStock = {"ID maquina", "ID producto", "fecha cambio", "total", "posicion"};
	private static final boolean[] formatoColumnas = {false, false, true, false, false}; //true = comillas ; false = sin comillas
	
	int id_maquina;
	int id_producto;
	String fecha;
	int total;
	int posicion;
	/**
	 * Constructor of the class Producto, which creates the object of the product with all the needed information
	 * @param id_maquina ID of machine
	 * @param id_producto ID of prodcut
	 * @param fecha date
	 * @param total number of elements in stock
	 * @param posicion position of the stock in a machine
	 */
	public Stock(int id_maquina, int id_producto, String fecha, int total, int posicion){
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
		this.fecha = fecha;
		this.total = total;
		this.posicion = posicion;
	}
	/**
	 * Returns the type of the variables of the object in reference to an index
	 *
	 * @param indice Index to identify variable
	 * @return Class of the variable
	 */
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 2: return String.class;
		default: return Integer.class; 
		}
		
	}
	/**
	 * Method to get object of the variable of a stock in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id_maquina);
		case 1: return new Integer(id_producto);
		case 2: return fecha;
		case 3: return new Integer(total);
		case 4: return new Integer(posicion);
		default: return null; 
		}	
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
	 * Getter of the date
	 *
	 * @return date as a string
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * Getter of the total number of products
	 *
	 * @return number of products
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * Getter of the position of the product in a machine
	 *
	 * @return position of the product
	 */
	public int getPosicion() {
		return posicion;
	}
	/**
	 * Getter of all the information of the stock
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id_maquina), String.valueOf(this.id_producto), fecha, String.valueOf(this.total), String.valueOf(this.posicion)};
		
		return datos;
	}
	/**
	 * Getter of the constant of all the stock information fields for database queries
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
	 * Getter of the constant of stock options for database queries
	 *
	 * @return Array of strings of stock options
	 */
	public static String[] getOpcionesstock() {
		return opcionesStock;
	}
	/**
	 * Method to get primary key in the database
	 *
	 * @return String of primary key
	 */
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " + nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_producto + "'" : this.id_producto);
	}
	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", fecha cambio: " + this.fecha + ", total: " + this.total + ", posicion: " + this.posicion;
	}
}
