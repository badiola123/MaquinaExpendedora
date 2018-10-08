package clientes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class Clientela extends AbstractTableModel{

	List<Cliente> listaEntera;
	List<Cliente> lista;
	ModeloColumnasTabla columnas;
	MyDataAccess conexion;
	Map<String, List<Cliente>> agrupacion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public Clientela (ModeloColumnasTabla columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	public void actualizar(){
		lista = cargarDatos(conexion);
		listaEntera = lista;
	}
	
	static public List<Cliente> cargarDatos(MyDataAccess conexion) {
		Cliente cliente;
		List<Cliente> lista = new ArrayList<>();
		String[] datos = new String[Cliente.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		try {
			resultado = comandos.select(null, Principal.getTablacliente(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar clientes",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Cliente.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
	        	
		        cliente = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4],
		        		Integer.valueOf(datos[5]), datos[6], datos[7], datos[8], datos[9], Integer.valueOf(datos[10]), datos[11], Integer.valueOf(datos[12]));
		        
		        if(cliente != null){
		        	lista.add(cliente);
		        }
		       
	        }
	      }catch (SQLException e) {
	        e.printStackTrace();
	     } 
	    return lista;
	}

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
	
	public String[] getListaOpciones() {
		Set<String> clientela = agrupacion.keySet();
		return clientela.toArray(new String [0]);
	}

	public void getListaClientesOpcion(String opcion) {
		List<Cliente> seleccionClientes;
		seleccionClientes = agrupacion.get(opcion);
		lista = new ArrayList<>();
		lista = seleccionClientes;
	}

	public void volverACargar(){
		lista = listaEntera;
	}

	@Override
	public int getColumnCount() {
		return columnas.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Cliente cliente = lista.get(fila);
		return cliente.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Cliente getCliente(int pos){
		return lista.get(pos);
	}
}