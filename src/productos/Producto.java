/**
 * @file Producto.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains product's variables and methods to have access to them
 */


package productos;

public class Producto {
	private static final String[] nombreColumnas = {"producto_id", "p_descripcion", "p_precio", "p_tipo_id"};
	private static final String[] opcionesProducto = {"ID", "Descripción", "Precio", "Tipo ID"};
	private static final boolean[] formatoColumnas = {false, true, false, false}; //true = comillas ; false = sin comillas

	int id;
	String descripcion;
	double precio;
	int tipo_id;
		/**
	 * Constructor of the class Producto, which creates the object of the product with all the needed information
	 * @param id ID of product
	 * @param descripcion description of the product
	 * @param precio price of the product 
	 * @param tipo_id id of the product type
	 */
	public Producto(int id, String descripcion, Double precio, int tipo_id){
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
		this.tipo_id = tipo_id;
	}
	
	/**
	 * Returns the type of the variables of the object in reference to an index
	 *
	 * @param indice Index to identify variable
	 * @return Class of the variable
	 */
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 1: return String.class;
		case 2: return Double.class;
		default: return Integer.class; 
		}
		
	}
	/**
	 * Method to get object of the variable of a product in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id);
		case 1: return descripcion;
		case 2: return new Double(precio);
		case 3: return new Integer(tipo_id);
		default: return null; 
		}	
	}

	/**
	 * Getter of all the information of the product
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id), this.descripcion, String.valueOf(this.precio), String.valueOf(this.tipo_id)};
		
		return datos;
	}
	/**
	 * Getter of the constant of all the product information fields for database queries
	 *
	 * @return Fields of information as array of strings
	 */
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}
	/**
	 * Getter of the ID of the product
	 *
	 * @return ID as a string
	 */
	public int getId() {
		return id;
	}
	/**
	 * Getter of the price of the product
	 *
	 * @return price as a string
	 */
	public Double getPrecio() {
		return precio;
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
	 * Getter of the constant of product options for database queries
	 *
	 * @return Array of strings of product options
	 */
	public static String[] getOpcionesproducto() {
		return opcionesProducto;
	}
	
	/**
	 * Method to get primary key in the database
	 *
	 * @return String of primary key
	 */
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id + "'" : this.id);
	}
	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID: " + this.id + ", descripción: " + this.descripcion + ", precio: " + this.precio + ", ID tipo producto: " + this.tipo_id;
	}
}
