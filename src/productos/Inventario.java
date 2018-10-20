/**
 * @file Inventario.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the table model for product visualization
 */

package productos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import maquinas.Maquina;
import vistas.Principal;

public class Inventario extends AbstractTableModel{
	
	List<Producto> listaEntera;
	List<Producto> lista;
	ModeloColumnasTablaProducto columnas;
	MyDataAccess conexion;
	private final static Logger LOGGER = Logger.getLogger(Inventario.class.getName());
	
	private static final String IM_ERROR = "img/error.png";
	/**
	 * Constructor of Inventario, which establishes the connection to the database
     * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public Inventario (ModeloColumnasTablaProducto columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion, -1);
		listaEntera = lista;
		this.columnas = columnas;
	}
	/**
	 * Loads data of products to create a machine list
	 * @param conexion The instance of connection to the database
   * @param maquinaID Machine from which data has to be loaded
	 * @return List of products
	 */
	static public List<Producto> cargarDatos(MyDataAccess conexion, int maquinaID) {
		Producto producto;
		List<Producto> lista = new ArrayList<>();
		String[] datos = new String[Producto.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		if(maquinaID == -1) resultado = comandos.select(null, Principal.getTablaproducto(), null, null, null, false, 0); // Seleccionar todos los productos
		else{ // Seleccionar productos de una maquina
			//String join = "producto p JOIN stock s ON p.producto_id = s.producto_id JOIN maquina m ON s.maquina_id = m.maquina_id";
			String join = Principal.getTablaproducto() + " join " + Principal.getTablastock() + " on " + Principal.getTablaproducto() 
			+ "." + Producto.getNombreColumnas()[0] + " = " + Principal.getTablastock() + "." + Producto.getNombreColumnas()[0] + " join "
			+ Principal.getTablamaquina() + " on " + Principal.getTablastock() + "." + Maquina.getNombreColumnas()[0] + " = "
			+ Principal.getTablamaquina() + "." + Maquina.getNombreColumnas()[0];
			
			String primaryKey = Principal.getTablamaquina() + ".maquina_id = " + maquinaID;
			resultado = comandos.select(null, join, primaryKey, null, null, false, 0);
		}
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (Producto.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        		System.out.println(datos[i-1]);
		        	}
			        System.out.println("\n");
			        
			        producto = new Producto(Integer.valueOf(datos[0]), datos[1], Double.valueOf(datos[2]), Integer.valueOf(datos[3]));
			        
			        if(producto != null){
			        	lista.add(producto);
			        }
			       
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
	 * Method to get an information field of a product from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected product's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Producto producto = lista.get(fila);
		return producto.getFieldAt(columna);
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
	 * Gets a product from the list of products
	 * @param pos Position of the product
	 * @return Product selected
	 */
	 public Producto getProducto(int pos){
		return lista.get(pos);
	}
}
