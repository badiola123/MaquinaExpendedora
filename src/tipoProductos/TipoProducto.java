package tipoProductos;

public class TipoProducto {
	private static final String[] nombreColumnas = {"tipo_producto_id", "t_descripcion"};
	private static final String[] opcionesTipoP = {"ID", "Descripción"};
	private static final boolean[] formatoColumnas = {false, true}; //true = comillas ; false = sin comillas
	int id;
	String descripcion;
	
	public TipoProducto(int id, String descripcion){
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 1: return String.class;
		default: return Integer.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id);
		case 1: return descripcion;
		default: return null; 
		}	
	}

	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id), this.descripcion};
		
		return datos;
	}
	
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public int getId() {
		return id;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionestipop() {
		return opcionesTipoP;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id + "'" : this.id);
	}

	@Override
	public String toString() {
		return opcionesTipoP[0] + " = " + this.id + ", " + opcionesTipoP[1] + " = " + this.descripcion;
	}
}
