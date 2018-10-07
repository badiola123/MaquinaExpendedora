package tipoProductos;

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

import Productos.Producto;
import clientes.Cliente;
import clientes.Mapeador;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class TiposLista extends AbstractTableModel{
	
	List<TipoProducto> listaEntera;
	List<TipoProducto> lista;
	ModeloColumnasTablaTipoProductos columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public TiposLista (ModeloColumnasTablaTipoProductos columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<TipoProducto> cargarDatos(MyDataAccess conexion) {
		TipoProducto tipoProducto;
		List<TipoProducto> lista = new ArrayList<>();
		String[] datos = new String[TipoProducto.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);
		
		try {
			resultado = comandos.select(null, Principal.getTablatipop(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar tipos de producto",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (TipoProducto.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
		        
	        	tipoProducto = new TipoProducto(Integer.valueOf(datos[0]), datos[1]);
		        
		        if(tipoProducto != null){
		        	lista.add(tipoProducto);
		        }
		       
	        }
	      }catch (SQLException e) {
	        e.printStackTrace();
	     } 
	    return lista;
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
		TipoProducto tipoProducto = lista.get(fila);
		return tipoProducto.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public TipoProducto getTipoP(int pos){
		return lista.get(pos);
	}
}
