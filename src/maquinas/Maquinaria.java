/**
 * @file Maquinaria.java
 * @author Imanol Badiola
 * @brief This file contains the table model for machine visualization
 */

package maquinas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import vistas.Principal;

public class Maquinaria extends AbstractTableModel{
	
	transient List<Maquina> listaEntera;
	transient List<Maquina> lista;
	ModeloColumnasTablaMaquina columnas;
	transient MyDataAccess conexion;
	private static final Logger LOGGER = Logger.getLogger(Maquinaria.class.getName());
	
	/**
	 * Constructor of Maquinaria, which establishes the connection to the database
     * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public Maquinaria (ModeloColumnasTablaMaquina columnas, MyDataAccess conexion){
		super();
		this.conexion = conexion;
		lista = new ArrayList<>();
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	/**
	 * Loads data of machines to create a machine list
	 * @param conexion The instance of connection to the database
	 * @return List of machines
	 */
	 public static List<Maquina> cargarDatos(MyDataAccess conexion) {
		Maquina maquina;
		List<Maquina> lista = new ArrayList<>();
		String[] datos = new String[Maquina.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		resultado = comandos.select(null, Principal.getTablamaquina(), null, null, null, false, 0);
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (Maquina.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        	}
			        
			        maquina = new Maquina(Integer.valueOf(datos[0]), datos[1], datos[2], Integer.valueOf(datos[3]), datos[4]);
			        
		        	lista.add(maquina);
			       
		        }
		      }catch (SQLException e) {
		    	  LOGGER.log(Level.ALL, e.getMessage());
		     } 
		}
	    
	    return lista;
	}

	/**
	 * Column number getter
	 * @return Number of columns
	 */
	@Override
	public int getColumnCount() {
		return columnas.getColumnCount();
	}

	/**
	 * Row number getter
	 * @return Number of rows
	 */
	@Override
	public int getRowCount() {
		return lista.size();
	}

	/**
	 * Method to get an information field of a machine from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected machine's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Maquina maquina = lista.get(fila);
		return maquina.getFieldAt(columna);
	}

	/**
	 * Gets the class of the field of a column
	 * @param columnIndex number of the column
	 * @return Class of the column
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	/**
	 * Gets a machine from the list of machines
	 * @param pos Position of the machine
	 * @return Machine selected
	 */
	public Maquina getMaquina(int pos){
		return lista.get(pos);
	}
}
