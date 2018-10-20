/**
 * @file ListaStock.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the table model for stock visualization
 */
package stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import conexion_sql.MyDataAccess;
import vistas.Principal;

public class ListaStock extends AbstractTableModel{

	transient List<Stock> listaEntera;
	transient List<Stock> lista;
	ModeloColumnasTablaStock columnas;
	transient MyDataAccess conexion;
	
	private static final Logger LOGGER = Logger.getLogger(ListaStock.class.getName());
	/**
	 * Constructor of ListaStock, which establishes the connection to the database
     * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public ListaStock (ModeloColumnasTablaStock columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	/**
	 * Loads data of stock to create a machine list
	 * @param conexion The instance of connection to the database
	 * @return List of stock
	 */
	public static List<Stock> cargarDatos(MyDataAccess conexion) {
		Stock stock;
		List<Stock> lista = new ArrayList<>();
		String[] datos = new String[Stock.getNombreColumnas().length];
		ResultSet resultado = null;
		
		resultado = conexion.getQuery("select * from " + Principal.getTablastock());
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (Stock.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        	}
		        	
			        stock = new Stock(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), datos[2], Integer.valueOf(datos[3]), Integer.valueOf(datos[4]));
			        
		        	lista.add(stock);
			       
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
	 * Method to get an information field of a stock from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected stock's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Stock stock = lista.get(fila);
		return stock.getFieldAt(columna);
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
	 * Gets a stock from the list of stocks
	 * @param pos Position of the stock
	 * @return Product selected
	 */
	public Stock getStock(int pos){
		return lista.get(pos);
	}
}
