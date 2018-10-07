package oferta;

public class Oferta {
	private static final String[] nombreColumnas = {"maquina_id", "producto_id", "fecha_reposicion", "cantidad"};
	private static final String[] opcionesOferta = {"ID maquina", "ID producto", "fecha reposición", "cantidad"};
	private static final boolean[] formatoColumnas = {false, false, true, false}; //true = comillas ; false = sin comillas
	
	int id_maquina;
	int id_producto;
	String fecha;
	int cantidad;
	
	public Oferta(int id_maquina, int id_producto, String fecha, int cantidad){
		
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
		this.fecha = fecha;
		this.cantidad = cantidad;
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 2: return String.class;
		default: return Integer.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id_maquina);
		case 1: return new Integer(id_producto);
		case 2: return fecha;
		case 3: return new Integer(cantidad);
		default: return null; 
		}	
	}
	
	public int getId_maquina() {
		return id_maquina;
	}

	public int getId_producto() {
		return id_producto;
	}

	public String getFecha() {
		return fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id_maquina), String.valueOf(this.id_producto), fecha, String.valueOf(this.cantidad)};
		
		return datos;
	}

	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionesoferta() {
		return opcionesOferta;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_producto + "'" : this.id_producto) + " and " 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.fecha + "'" : this.fecha);
	}

	@Override
	public String toString() {
		return "ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", fecha reposición: " + this.fecha + ", cantidad: " + this.cantidad;
	}
}
