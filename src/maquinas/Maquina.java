package maquinas;

public class Maquina {
	private static final String[] nombreColumnas = {"maquina_id", "m_provincia", "m_pueblo", "m_cp", "m_calle"};
	private static final String[] opcionesMaquina = {"ID", "Provincia", "Pueblo", "Código postal", "Calle"};
	private static final boolean[] formatoColumnas = {false, true, true, false, true}; //true = comillas ; false = sin comillas
	private static final int numHusillosMaquina = 3;
	
	int id;
	String provincia;
	String pueblo;
	int cp;
	String calle;
	
	public Maquina(int id, String provincia, String pueblo, int cp, String calle){
		this.id = id;
		this.provincia = provincia;
		this.pueblo = pueblo;
		this.cp = cp;
		this.calle = calle;
	}
	
	public Class<?> getFieldClass(int indice){
		switch (indice){
		case 0: return Integer.class;
		case 3: return Integer.class;
		default: return String.class; 
		}
		
	}

	public Object getFieldAt(int columna) {
		switch (columna){
		case 0: return new Integer(id);
		case 1: return provincia;
		case 2: return pueblo;
		case 3: return new Integer(cp);
		case 4: return calle;
		default: return null; 
		}	
	}

	public String[] getDatos(){
		String[] datos = {String.valueOf(this.id), this.provincia, this.pueblo, String.valueOf(this.cp), this.calle};
		
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

	public static String[] getOpcionesmaquina() {
		return opcionesMaquina;
	}
	
	public String getPrimaryKey(){
		return nombreColumnas[0] + " = " + (formatoColumnas[0] ? "'" + this.id + "'" : this.id);
	}

	public static int getNumhusillosmaquina() {
		return numHusillosMaquina;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + ", provincia: " + this.provincia + ", pueblo: " + this.pueblo + ", CP: " + this.cp + ", calle: " + this.calle;
	}
	
	
}
