package Stock;

public class Stock {
	private static final String[] nombreColumnas = {"maquina_id", "producto_id", "fecha_cambio", "total", "posicion"};
	private static final String[] opcionesStock = {"ID maquina", "ID producto", "fecha cambio", "total", "posicion"};
	private static final boolean[] formatoColumnas = {false, false, true, false, false}; //true = comillas ; false = sin comillas
	
	int id_maquina;
	int id_producto;
	String fecha;
	int total;
	int posicion;
	
	public Stock(int id_maquina, int id_producto, String fecha, int total, int posicion){
		
		this.id_maquina = id_maquina;
		this.id_producto = id_producto;
		this.fecha = fecha;
		this.total = total;
		this.posicion = posicion;
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
		case 3: return new Integer(total);
		case 4: return new Integer(posicion);
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

	public int getTotal() {
		return total;
	}

	public int getPosicion() {
		return posicion;
	}

	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id_maquina), String.valueOf(this.id_producto), fecha, String.valueOf(this.total), String.valueOf(this.posicion)};
		
		return datos;
	}

	public static String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public static boolean[] getFormatoColumnas() {
		return formatoColumnas;
	}

	public static String[] getOpcionesstock() {
		return opcionesStock;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id_maquina + "'" : this.id_maquina) + " and " + nombreColumnas[1] + " = " + (formatoColumnas[1] ? "'" + this.id_producto + "'" : this.id_producto);
	}

	@Override
	public String toString() {
		return "ID maquina: " + this.id_maquina + ", ID producto: " + this.id_producto + ", fecha cambio: " + this.fecha + ", total: " + this.total + ", posicion: " + this.posicion;
	}
}
