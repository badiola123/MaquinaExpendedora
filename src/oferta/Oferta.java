/**
 * @file Oferta.java
 * @author Imanol Badiola
 * @brief This file contains offer's variables and methods to have access to them
 */

package oferta;

public class Oferta {
	private static final String[] nombreColumnas = {"maquina_id", "producto_id", "fecha_reposicion", "cantidad"};
	private static final String[] opcionesOferta = {"ID maquina", "ID producto", "fecha reposición", "cantidad"};
	private static final boolean[] formatoColumnas = {false, false, true, false}; //true = comillas ; false = sin comillas
	
	int idMaquina;
	int idProducto;
	String fecha;
	int cantidad;
	
	/**
	 * Constructor of the class Oferta, which creates the object of the offer with all the needed information
	 * @param id_maquina ID of machine
	 * @param id_producto ID of product
	 * @param fecha Date of offer
	 * @param cantidad Quantity  
	 */
	public Oferta(int idMaquina, int idProducto, String fecha, int cantidad){
		
		this.idMaquina = idMaquina;
		this.idProducto = idProducto;
		this.fecha = fecha;
		this.cantidad = cantidad;
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
	 * Method to get object of the variable of a offer in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return Integer.valueOf(idMaquina);
		case 1: return Integer.valueOf(idProducto);
		case 2: return fecha;
		case 3: return Integer.valueOf(cantidad);
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
	 * Getter of the date of offer
	 *
	 * @return Date as a string
	 */
	public String getFecha() {
		return fecha;
	}
	
	/**
	 * Getter of the quantity of offer
	 *
	 * @return Quantity as integer
	 */
	public int getCantidad() {
		return cantidad;
	}
	
	/**
	 * Getter of all the information of the offer
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		return new String[] {String.valueOf(this.idMaquina), String.valueOf(this.idProducto), fecha, String.valueOf(this.cantidad)};
	}

	/**
	 * Getter of the constant of all the offer information fields for database queries
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
	 * Getter of the constant of offer options for database queries
	 *
	 * @return Array of strings of offer options
	 */
	public static String[] getOpcionesoferta() {
		return opcionesOferta;
	}
	
	/**
	 * Method to get primary key in the database
	 *
	 * @return String of primary key
	 */
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.idMaquina + "'" : this.idMaquina) + " and " 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.idProducto + "'" : this.idProducto) + " and " 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.fecha + "'" : this.fecha);
	}

	/**
	 * Override of the "toString" method
	 * @return String of offer information
	 */
	@Override
	public String toString() {
		return "ID maquina: " + this.idMaquina + ", ID producto: " + this.idProducto + ", fecha reposición: " + this.fecha + ", cantidad: " + this.cantidad;
	}
}
