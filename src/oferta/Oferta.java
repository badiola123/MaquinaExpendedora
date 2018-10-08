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
	
	int id_maquina;
	int id_producto;
	String fecha;
	int cantidad;
	
	/**
	 * Constructor of the class Oferta, which creates the object of the offer with all the needed information
	 * @param id ID of machine
	 * @param id_producto ID of product
	 * @param fecha Date of offer
	 * @param cantidad Quantity  
	 */
	public Oferta(int id_maquina, int id_producto, String fecha, int cantidad){
		
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
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
		switch (indice){
		case 2: return String.class;
		default: return Integer.class; 
		}
		
	}

	/**
	 * Method to get object of the variable of a offer in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id_maquina);
		case 1: return new Integer(id_producto);
		case 2: return fecha;
		case 3: return new Integer(cantidad);
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
		String[] datos = {String.valueOf(this.id_maquina), String.valueOf(this.id_producto), fecha, String.valueOf(this.cantidad)};
		
		return datos;
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
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_producto + "'" : this.id_producto) + " and " 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.fecha + "'" : this.fecha);
	}

	/**
	 * Override of the "toString" method
	 * @return String of offer information
	 */
	@Override
	public String toString() {
		return "ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", fecha reposición: " + this.fecha + ", cantidad: " + this.cantidad;
	}
}
