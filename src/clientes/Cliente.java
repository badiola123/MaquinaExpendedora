/**
 * @file Cliente.java
 * @author Imanol Badiola
 * @brief This file contains client's variables and methods to have access to them
 */

package clientes;

public class Cliente {
	private static final String[] nombreColumnas = {"usuario_id", "u_nombre", "u_apellido", "u_dni",
												"u_edad", "u_telefono", "u_correo", "u_numerocuenta", "u_provincia",
												"u_pueblo", "u_cp", "u_calle", "u_numero"};
	private static final String[] opcionesCliente = {"ID", "Nombre", "Apellido", "DNI", "Fecha de nacimiento", "Teléfono", "Correo",
			"Número de cuenta", "Provincia", "Pueblo", "Código postal", "Calle", "Número vivienda"};
	private static final boolean[] formatoColumnas = {true, true, true, true, true, false, true, true, true,
												true, false, true, false}; //true = comillas ; false = sin comillas
	
	String id;
	String nombre;
	String apellido;
	String dni;
	String fechaNacimiento;
	int telefono;
	String correo;
	String numCuenta;
	String provincia;
	String pueblo;
	int codigoPostal;
	String calle;
	int numVivienda;
	/**
	 * Constructor of the class Cliente, which creates the object of the client with all the needed information
	 * @param id ID of client
	 * @param nombre Name of client
	 * @param apellido Surname of client
	 * @param dni Identity card number
	 * @param fechaNacimiento Birth date
	 * @param telefono Telephone number
	 * @param correo Email account
	 * @param numCuenta Bank account number
	 * @param provincia Province
	 * @param pueblo City 
	 * @param codigoPostal Postal code 
	 * @param calle Street
	 * @param numVivienda House number
	 */
	public Cliente(String[] datos){
		
		this.id = datos[0];
		this.nombre = datos[1];
		this.apellido = datos[2];
		this.dni = datos[3];
		this.fechaNacimiento = datos[4];
		this.telefono = Integer.valueOf(datos[5]);
		this.correo = datos[6];
		this.numCuenta = datos[7];
		this.provincia = datos[8];
		this.pueblo = datos[9];
		this.codigoPostal = Integer.valueOf(datos[10]);
		this.calle = datos[11];
		this.numVivienda = Integer.valueOf(datos[12]);
	}
	
	/**
	 * Returns the type of the variables of the object in reference to an index
	 *
	 * @param indice Index to identify variable
	 * @return Class of the variable
	 */
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 5: return Integer.class;
		case 10: return Integer.class;
		case 12: return Integer.class;
		default: return String.class; 
		}
		
	}
	
	/**
	 * Method to get object of the variable of a client in reference to an index
	 *
	 * @param columna Index of column to identify variable
	 * @return Object of the variable
	 */
	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return id;
		case 1: return nombre;
		case 2: return apellido;
		case 3: return dni;
		case 4: return fechaNacimiento;
		case 5: return Integer.valueOf(telefono);
		case 6: return correo;
		case 7: return numCuenta;
		case 8: return provincia;
		case 9: return pueblo;
		case 10: return Integer.valueOf(codigoPostal);
		case 11: return calle;
		case 12: return Integer.valueOf(numVivienda);
		default: return null; 
		}	
	}
	
	/**
	 * Getter of the name of the client
	 *
	 * @return Name as a string
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Getter of the surname of the client
	 *
	 * @return Surname as a string
	 */
	public String getApellido() {
		return apellido;
	}
	
	/**
	 * Getter of the postal code of the client
	 *
	 * @return Postal code as an integer
	 */
	public int getCodigoPostal() {
		return codigoPostal;
	}
	
	/**
	 * Getter of the birth date of the client
	 *
	 * @return Birthdate as a string
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	/**
	 * Getter of the ID of the client
	 *
	 * @return ID as a string
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Getter of all the information of the client
	 *
	 * @return Array of strings with all the information
	 */
	public String[] getDatos(){
		return new String[] {this.id, this.nombre, this.apellido, this.dni,
				this.fechaNacimiento, String.valueOf(this.telefono), this.correo, this.numCuenta,
				this.provincia, this.pueblo, String.valueOf(this.codigoPostal), this.calle, String.valueOf(this.numVivienda)};
	}
	
	/**
	 * Getter of the constant of all the client information fields for database queries
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
	 * Getter of the constant of client options for database queries
	 *
	 * @return Array of strings of client options
	 */
	public static String[] getOpcionescliente() {
		return opcionesCliente;
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
	 * @return String of client information
	 */
	@Override
	public String toString() {
		return "ID: " + this.id + ", nombre: " + this.nombre + ", apellido: " + this.apellido + ", DNI: " + this.dni;
	}
	
	
}
