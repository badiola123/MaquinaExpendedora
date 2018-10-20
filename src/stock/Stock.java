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
	
	int idMaquina;
	int idProducto;
	String fecha;
	int total;
	int posicion;
	/**
	 * Constructor of the class Producto, which creates the object of the product with all the needed information
	 * @param idMaquina ID of machine
	 * @param idProducto ID of prodcut
	 * @param fecha date
	 * @param total number of elements in stock
	 * @param posicion position of the stock in a machine
	 */
	public Stock(int idMaquina, int idProducto, String fecha, int total, int posicion){
		this.idMaquina = idMaquina;
		this.idProducto = idProducto;
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
		if(indice == 2) return String.class;
		return Integer.class;
	}
	/**
	 * Method to get object of the variable of a stock in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return Integer.valueOf(idMaquina);
		case 1: return Integer.valueOf(idProducto);
		case 2: return fecha;
		case 3: return Integer.valueOf(total);
		case 4: return Integer.valueOf(posicion);
		default: return null; 
		}	
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
		return new String[] {String.valueOf(this.idMaquina), String.valueOf(this.idProducto), fecha, String.valueOf(this.total), String.valueOf(this.posicion)};
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
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.idMaquina + "'" : this.idMaquina) + " and " + nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.idProducto + "'" : this.idProducto);
	}
	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID maquina: " + this.idMaquina + ", ID producto: " + this.idProducto + ", fecha cambio: " + this.fecha + ", total: " + this.total + ", posicion: " + this.posicion;
	}
}
