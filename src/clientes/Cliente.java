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
	
	public Cliente(String id, String nombre, String apellido, String dni, String fechaNacimiento,
			int telefono, String correo, String numCuenta, String provincia, 
			String pueblo,	int codigoPostal, String calle, int numVivienda){
		
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.correo = correo;
		this.numCuenta = numCuenta;
		this.provincia = provincia;
		this.pueblo = pueblo;
		this.codigoPostal = codigoPostal;
		this.calle = calle;
		this.numVivienda = numVivienda;
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 5: return Integer.class;
		case 10: return Integer.class;
		case 12: return Integer.class;
		default: return String.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return id;
		case 1: return nombre;
		case 2: return apellido;
		case 3: return dni;
		case 4: return fechaNacimiento;
		case 5: return new Integer(telefono);
		case 6: return correo;
		case 7: return numCuenta;
		case 8: return provincia;
		case 9: return pueblo;
		case 10: return new Integer(codigoPostal);
		case 11: return calle;
		case 12: return new Integer(numVivienda);
		default: return null; 
		}	
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getId() {
		return id;
	}
	
	public String[] getDatos(){
		String[] datos = {this.id, this.nombre, this.apellido, this.dni,
				this.fechaNacimiento, String.valueOf(this.telefono), this.correo, this.numCuenta,
				this.provincia, this.pueblo, String.valueOf(this.codigoPostal), this.calle, String.valueOf(this.numVivienda)};
		
		return datos;
	}

	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionescliente() {
		return opcionesCliente;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id + "'" : this.id);
	}

	@Override
	public String toString() {
		return "ID: " + this.id + ", nombre: " + this.nombre + ", apellido: " + this.apellido + ", DNI: " + this.dni;
	}
	
	
}
