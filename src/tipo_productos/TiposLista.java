/**
 * @file TiposLista.java
 * @author Edgar Azpiazu
 * @brief This file contains the table model for product type visualization
 */

package tipo_productos;

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

public class TiposLista extends AbstractTableModel{
	
	transient List<TipoProducto> listaEntera;
	transient List<TipoProducto> lista;
	ModeloColumnasTablaTipoProductos columnas;
	transient MyDataAccess conexion;
	private static final Logger LOGGER = Logger.getLogger(TiposLista.class.getName());
	
  /**
	 * Constructor of TiposLista, which establishes the connection to the database
	 * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public TiposLista (ModeloColumnasTablaTipoProductos columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
  /**
	 * Loads data of product types to create a product type list
	 * @param conexion The instance of connection to the database
	 * @return List of product types
	 */
     public static List<TipoProducto> cargarDatos(MyDataAccess conexion) {
		TipoProducto tipoProducto;
		List<TipoProducto> lista = new ArrayList<>();
		String[] datos = new String[TipoProducto.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);
		
		resultado = comandos.select(null, Principal.getTablatipop(), null, null, null, false, 0);
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (TipoProducto.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        	}
			        
		        	tipoProducto = new TipoProducto(Integer.valueOf(datos[0]), datos[1]);
			       
		        	lista.add(tipoProducto);
			       
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
	 * Method to get an information field of a product type from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected product type's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		TipoProducto tipoProducto = lista.get(fila);
		return tipoProducto.getFieldAt(columna);
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
	 * Gets a product type from the list of product types
	 * @param pos Position of the product type
	 * @return Product type selected
	 */
	public TipoProducto getTipoP(int pos){
		return lista.get(pos);
	}
}
