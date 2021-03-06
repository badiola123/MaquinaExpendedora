/**
 * @file Clientela.java
 * @author Imanol Badiola
 * @brief This file contains the table model for client visualization
 */

package clientes;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import vistas.Principal;

public class Clientela extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private transient List<Cliente> listaEntera;
	private transient List<Cliente> lista;
	ModeloColumnasTabla columnas;
	private transient MyDataAccess conexion;
	private transient Map<String, List<Cliente>> agrupacion;
	private static final Logger LOGGER = Logger.getLogger(Clientela.class.getName());
	/**
	 * Constructor of Clientela, which establishes the connection to the database
	 * @param columnas Columns of table
	 * @param conexion Database connection instance
	 */
	public Clientela (ModeloColumnasTabla columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	/**
	 * Updates the list of clients
	 */
	public void actualizar(){
		lista = cargarDatos(conexion);
		listaEntera = lista;
	}
	
	/**
	 * Loads data of clients to create a client list
	 * @param conexion The instance of connection to the database
	 * @return List of client
	 */
	public static List<Cliente> cargarDatos(MyDataAccess conexion) {
		Cliente cliente;
		List<Cliente> lista = new ArrayList<>();
		String[] datos = new String[Cliente.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		resultado = comandos.select(null, Principal.getTablacliente(), null, null, null, false, 0);
		
		if(resultado!=null) {
		    try {
		        while(resultado.next()){
		        	
		        	for(int i = 1; i < (Cliente.getNombreColumnas().length + 1); i++){
		        		datos[i-1] = resultado.getString(i);
		        	}
			        cliente = new Cliente(datos);
		        	lista.add(cliente);
		        }
		      }catch (SQLException e) {
		    	  LOGGER.log(Level.ALL, e.getMessage());
		     } 
		}
	    return lista;
	}
	
	/**
	 * Groups client according to a mapper 
	 * @param mapeador Mapper used to group clients
	 */
	public void agrupar(Mapeador<Cliente> mapeador){
		agrupacion = new HashMap<>();
		for (Cliente cliente: lista){
			String key = mapeador.map(cliente);
			List<Cliente> listaClientes = agrupacion.get(key);
			if (listaClientes==null){
				listaClientes = new ArrayList<>();
			}
			listaClientes.add(cliente);
			agrupacion.put(key, listaClientes);
		}
	}
	
	/**
	 * Gets option list for client selection 
	 * @return Array of strings of option
	 */
	public String[] getListaOpciones() {
		Set<String> clientela = agrupacion.keySet();
		return clientela.toArray(new String [0]);
	}
	
	/**
	 * Gets list of clients according to an option
	 * @param opcion Option to get clients
	 * @return Array of clients
	 */
	public void getListaClientesOpcion(String opcion) {
		List<Cliente> seleccionClientes;
		seleccionClientes = agrupacion.get(opcion);
		lista = new ArrayList<>();
		lista = seleccionClientes;
	}

	/**
	 * Resets selected client Arraylist with all clients list as default
	 */
	public void volverACargar(){
		lista = listaEntera;
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
	 * Method to get an information field of a client from table position 
	 * @param fila row coordinate
	 * @param columna columns coordinate
	 * @return Selected client's information field
	 */
	@Override
	public Object getValueAt(int fila, int columna) {
		Cliente cliente = lista.get(fila);
		return cliente.getFieldAt(columna);
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
	 * Gets a client from the list of clients
	 * @param pos Position of the client
	 * @return Client selected
	 */
	public Cliente getCliente(int pos){
		return lista.get(pos);
	}
}
