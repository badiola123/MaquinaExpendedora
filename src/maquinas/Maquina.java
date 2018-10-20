/**
 * @file Maquina.java
 * @author Imanol Badiola
 * @brief This file contains machine's variables and methods to have access to them
 */

package maquinas;


public class Maquina {
	private static final String[] nombreColumnas = {"maquina_id", "m_provincia", "m_pueblo", "m_cp", "m_calle"};
	private static final String[] opcionesMaquina = {"ID", "Provincia", "Pueblo", "Código postal", "Calle"};
	private static final boolean[] formatoColumnas = {false, true, true, false, true}; //true = comillas ; false = sin comillas
	private static final int NUMHUSILLOSMAQUINA = 3;
	
	int id;
	String provincia;
	String pueblo;
	int cp;
	String calle;
	
	/**
	 * Constructor of the class Maquina, which creates the object of the machine with all the needed information
	 * @param id ID of machine
	 * @param provincia Province
	 * @param pueblo City 
	 * @param cp Postal code 
	 * @param calle Street
	 */
	public Maquina(int id, String provincia, String pueblo, int cp, String calle){
		this.id = id;
		this.provincia = provincia;
		this.pueblo = pueblo;
		this.cp = cp;
		this.calle = calle;
	}
	
	/**
	 * Returns the type of the variables of the object in reference to an index
	 *
	 * @param indice Index to identify variable
	 * @return Class of the variable
	 */
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 0: return Integer.class;
		case 3: return Integer.class;
		default: return String.class; 
		}
		
	}

	/**
	 * Method to get object of the variable of a machine in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return Integer.valueOf(id);
		case 1: return provincia;
		case 2: return pueblo;
		case 3: return Integer.valueOf(cp);
		case 4: return calle;
		default: return null; 
		}	
	}

	/**
	 * Getter of all the information of the machine
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		return new String[] {String.valueOf(this.id), this.provincia, this.pueblo, String.valueOf(this.cp), this.calle};
	}
	
	/**
	 * Getter of the constant of all the machine information fields for database queries
	 *
	 * @return Fields of information as array of strings
	 */
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	/**
	 * Getter of the ID of the machine
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
	 * Getter of the constant of machine options for database queries
	 *
	 * @return Array of strings of machine options
	 */
	public static String[] getOpcionesmaquina() {
		return opcionesMaquina;
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
	 * Getter of the number of spindle of the machine
	 *
	 * @return Number of spindles as integer
	 */
	public static int getNumhusillosmaquina() {
		return NUMHUSILLOSMAQUINA;
	}

	/**
	 * Override of the "toString" method
	 * @return String of machine information
	 */
	@Override
	public String toString() {
		return "ID: " + this.id + ", provincia: " + this.provincia + ", pueblo: " + this.pueblo + ", CP: " + this.cp + ", calle: " + this.calle;
	}
	
	
}
