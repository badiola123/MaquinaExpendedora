package venta;

public class Venta {
	private static final String[] nombreColumnas = {"usuario_id", "maquina_id", "producto_id", "precio_venta", "fecha_venta"};
	private static final String[] opcionesVenta = {"ID usuario", "ID maquina", "ID producto", "precio", "fecha venta"};
	private static final boolean[] formatoColumnas = {true, false, false, false, true}; //true = comillas ; false = sin comillas
	
	String id_usuario;
	int id_maquina;
	int id_producto;
	double precio;
	String fecha;
	
	public Venta(String id_usuario, int id_maquina, int id_producto, double precio, String fecha){
		
		this.id_usuario = id_usuario;
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
		this.precio = precio;
		this.fecha = fecha;
		
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 0: return String.class;
		case 3: return Double.class;
		case 4: return String.class;
		default: return Integer.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return id_usuario;
		case 1: return new Integer(id_maquina);
		case 2: return new Integer(id_producto);
		case 4: return new Double(precio);
		case 3: return fecha;
		default: return null; 
		}	
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public int getId_maquina() {
		return id_maquina;
	}

	public int getId_producto() {
		return id_producto;
	}

	public double getPrecio() {
		return precio;
	}

	public String[] getDatos(){
		String[] datos = {this.id_usuario, String.valueOf(this.id_maquina), String.valueOf(this.id_producto), String.valueOf(this.precio), fecha};
		
		return datos;
	}

	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionesVenta() {
		return opcionesVenta;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_usuario + "'" : this.id_usuario) + " and " 
				+ nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " 
				+ nombreColumnas[2] + " = " + (formatoColumnas[2] ? "'" + this.id_producto + "'" : this.id_producto) + " and " 
				+ nombreColumnas[4] + " = " + (formatoColumnas[4] ? "'" + this.fecha + "'" : this.fecha);
	}

	@Override
	public String toString() {
		return "ID usuario: " + this.id_usuario + ", ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", precio: " + this.precio + ", fecha reposición: " + this.fecha;
	}
	
	
}
