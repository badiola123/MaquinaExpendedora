/**
 * @file ListaOfertas.java
 * @author Imanol Badiola
 * @brief This file contains the table model for offer visualization
 */


package oferta;

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

public class ListaOfertas extends AbstractTableModel{

	transient List<Oferta> listaEntera;
	transient List<Oferta> lista;
	ModeloColumnasTablaOferta columnas;
	transient MyDataAccess conexion;
	private static final  Logger LOGGER = Logger.getLogger(ListaOfertas.class.getName());
	
	/**
	 * Constructor of ListaOfertas, which establishes the connection to the database
     * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public ListaOfertas (ModeloColumnasTablaOferta columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	/**
	 * Loads data of offers to create a offer list
	 * @param conexion The instance of connection to the database
	 * @return List of offers
	 */
	 public static List<Oferta> cargarDatos(MyDataAccess conexion) {
		Oferta oferta;
		List<Oferta> lista = new ArrayList<>();
		String[] datos = new String[Oferta.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		resultado = comandos.select(null, Principal.getTablaoferta(), null, null, null, false, 0);
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (Oferta.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        	}
		        	
			        oferta = new Oferta(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), datos[2], Integer.valueOf(datos[3]));
			        
		        	lista.add(oferta);
			       
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
	 * Method to get an information field of an offer from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected offer's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Oferta oferta = lista.get(fila);
		return oferta.getFieldAt(columna);
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
	 * Gets an offer from the list of offers
	 * @param pos Position of the offer
	 * @return Offer selected
	 */
	public Oferta getOferta(int pos){
		return lista.get(pos);
	}
}
