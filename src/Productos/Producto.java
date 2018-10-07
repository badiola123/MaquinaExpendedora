package Productos;

public class Producto {
	private static final String[] nombreColumnas = {"producto_id", "p_descripcion", "p_precio", "p_tipo_id"};
	private static final String[] opcionesProducto = {"ID", "Descripción", "Precio", "Tipo ID"};
	private static final boolean[] formatoColumnas = {false, true, false, false}; //true = comillas ; false = sin comillas

	int id;
	String descripcion;
	double precio;
	int tipo_id;
	
	public Producto(int id, String descripcion, Double precio, int tipo_id){
		this.id = id;
		this.descripcion = descripcion;
		this.precio = precio;
		this.tipo_id = tipo_id;
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 1: return String.class;
		case 2: return Double.class;
		default: return Integer.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id);
		case 1: return descripcion;
		case 2: return new Double(precio);
		case 3: return new Integer(tipo_id);
		default: return null; 
		}	
	}

	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id), this.descripcion, String.valueOf(this.precio), String.valueOf(this.tipo_id)};
		
		return datos;
	}
	
	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public int getId() {
		return id;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionesproducto() {
		return opcionesProducto;
	}

	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id + "'" : this.id);
	}
	
	@Override
	public String toString() {
		return "ID: " + this.id + ", descripción: " + this.descripcion + ", precio: " + this.precio + ", ID tipo producto: " + this.tipo_id;
	}
}
