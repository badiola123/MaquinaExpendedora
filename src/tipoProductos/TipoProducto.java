/**
 * @file TipoProducto.java
 * @author Edgar Azpiazu
 * @brief This file contains product type's variables and methods to have access to them
 */

package tipoProductos;

public class TipoProducto {
	private static final String[] nombreColumnas = {"tipo_producto_id", "t_descripcion"};
	private static final String[] opcionesTipoP = {"ID", "Descripción"};
	private static final boolean[] formatoColumnas = {false, true}; //true = comillas ; false = sin comillas
	int id;
	String descripcion;
	
  /**
	 * Constructor of the class TipoProducto, which creates the object of the product type with all the needed information
	 * @param id ID of the product type
	 * @param descripcion description of the product type
	 */
	public TipoProducto(int id, String descripcion){
		this.id = id;
		this.descripcion = descripcion;
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
		default: return Integer.class; 
		}
		
	}

  /**
	 * Method to get object of the variable of a product type in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id);
		case 1: return descripcion;
		default: return null; 
		}	
	}

  /**
	 * Getter of all the information of the product type
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id), this.descripcion};
		
		return datos;
	}
	
  /**
	 * Getter of the constant of all the product type information fields for database queries
	 *
	 * @return Fields of information as array of strings
	 */
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

  /**
	 * Getter of the ID of the product type
	 *
	 * @return ID as a string
	 */
	public int getId() {
		return id;
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
	 * Getter of the constant of product type options for database queries
	 *
	 * @return Array of strings of product type options
	 */
	public static String[] getOpcionestipop() {
		return opcionesTipoP;
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
	 * @return String of product type information
	 */
	@Override
	public String toString() {
		return opcionesTipoP[0] + " = " + this.id + ", " + opcionesTipoP[1] + " = " + this.descripcion;
	}
}
