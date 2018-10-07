package Productos;

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

import clientes.Cliente;
import clientes.Mapeador;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import vistas.Principal;

public class Inventario extends AbstractTableModel{
	
	List<Producto> listaEntera;
	List<Producto> lista;
	ModeloColumnasTablaProducto columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public Inventario (ModeloColumnasTablaProducto columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion, -1);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<Producto> cargarDatos(MyDataAccess conexion, int maquinaID) {
		Producto producto;
		List<Producto> lista = new ArrayList<>();
		String[] datos = new String[Producto.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		try {
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
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar productos",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
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
		Producto producto = lista.get(fila);
		return producto.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Producto getProducto(int pos){
		return lista.get(pos);
	}
}
