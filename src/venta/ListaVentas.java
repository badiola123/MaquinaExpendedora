/**
 * @file ListaVenta.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the table model for sales' visualization
 */
package venta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class ListaVentas extends AbstractTableModel{

	List<Venta> listaEntera;
	List<Venta> lista;
	ModeloColumnasTablaVenta columnas;
	MyDataAccess conexion;
	private final static Logger LOGGER = Logger.getLogger(ListaVentas.class.getName());
	private static final String IM_ERROR = "img/error.png";
	/**
	 * Constructor of ListaVenta, which establishes the connection to the database
     * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public ListaVentas (ModeloColumnasTablaVenta columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	/**
	 * Loads data of sale to create a machine list
	 * @param conexion The instance of connection to the database
	 * @return List of sale
	 */
	static public List<Venta> cargarDatos(MyDataAccess conexion) {
		Venta venta;
		List<Venta> lista = new ArrayList<>();
		String[] datos = new String[Venta.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);		
		
		try {
			resultado = comandos.select(null, Principal.getTablaventa(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar ventas",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Venta.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
	        	
		        venta = new Venta(datos[0], Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Double.valueOf(datos[3]), datos[4]);
		        
		        if(venta != null){
		        	lista.add(venta);
		        }
		       
	        }
	      }catch (Exception e) {
	    	  LOGGER.log(Level.ALL, e.getMessage());
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
	 * Method to get an information field of a sale from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected sales' information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Venta venta = lista.get(fila);
		return venta.getFieldAt(columna);
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
	 * Gets a sale from the list of sales
	 * @param pos Position of the sale
	 * @return sale selected
	 */
	public Venta getVenta(int pos){
		return lista.get(pos);
	}
}
